/**
 *
 */
package com.arkami.myidkey.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;
import com.arkami.myidkey.activity.customuicomponents.predifined.Note;
import com.arkami.myidkey.activity.customuicomponents.predifined.Site;
import com.arkami.myidkey.database.datasources.FieldDataSource;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.datasources.TagDataSource;
import com.arkami.myidkey.database.datasources.ValuesDataSource;
import com.arkami.myidkey.database.tables.Field;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.database.tables.Tag;
import com.arkami.myidkey.database.tables.Value;
import com.arkami.myidkey.model.KeyCardTypeEnum;

/**
 * @author sbahdikyan
 */
public class CSVImporter {
    private Context context;
    private FieldDataSource fieldDataSource;
    private ValuesDataSource valuesDataSource;
    private KeyCardDataSource keyCardDataSource;
    private TagDataSource tagDataSource;
    private int all;
    private int imported;

    /**
     *
     */
    public CSVImporter(Context context) {
        this.context = context;
        this.fieldDataSource = new FieldDataSource(context);
        this.valuesDataSource = new ValuesDataSource(context);
        this.keyCardDataSource = new KeyCardDataSource(context);
        this.tagDataSource = new TagDataSource(context);
    }

    /**
     * Imports keycards from scv file.
     *
     * @param csvLocation where the csv is. "lastpass.csv" in assets is default file.
     */
    public void importFromCSV(String csvLocation) {
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(csvLocation)));
//                    new InputStreamReader(context
//                    .getAssets().open(csvLocation)));
            String next[] = {};
            List<String[]> list = new ArrayList<String[]>();

            while (true) {
                next = csvReader.readNext();

                if (next != null) {

                    list.add(next);

                    String[] split = next[3].split(CSVReader.ITEM_SEPARATOR, 0);
                    String[] nameValuePair = split[0].split(":", 0);

                    long date = new Date().getTime();
                    {

                        KeyCard keyCard = new KeyCard();
                        keyCard.setCreateDate(date);
                        keyCard.setModifyDate(date);
                        int favorite;
                        try {
                            favorite = Integer.valueOf(next[6]);
                            all++;
                        } catch (NumberFormatException e) {
                            //it is the initial line of the csv
                            continue;
                        }
                        keyCard.setFavourite(favorite == 1);
                        keyCard.setName(next[4]);
                        long keyCardId = keyCardDataSource.insert(keyCard);
                        keyCard.setId(keyCardId);
                        long[] valueIds = null;

                        String[] tags = next[5].split(CSVReader.ITEM_SEPARATOR, 0);
                        int tagslength = tags.length;
                        for (int i = 0; i < tags.length; i++) {
                            if (tags[i].equals("Secure Notes")) {
                                tagslength--;
                            }
                        }
                        if (tagslength > 0) {
                            long[] tagIds = new long[tagslength];
                            int tagIdsIndex = 0;
                            for (int i = 0; i < tags.length; i++) {

                                if (tags[i].equals(String.valueOf("Secure Notes")) == false) {
                                    tagIds[tagIdsIndex++] = getTagId(tags[i]);
                                }
                            }
                            keyCard.setTagIds(tagIds);
                        }


                        if (next[3].contains("NoteType")) {

                            // if ((nameValuePair.length > 1)
                            // && ((nameValuePair[1].equals(KeyCardTypeEnum.visa
                            // .getTitle()))
                            // || (nameValuePair[1]
                            // .equals(KeyCardTypeEnum.drivers_license
                            // .getTitle())) || (nameValuePair[1]
                            // .equals(KeyCardTypeEnum.database
                            // .getTitle())))){


                            long keyCardTypeID = Cache
                                    .getInstance(context)
                                    .getKeyCardTypeId(
                                            KeyCardTypeEnum
                                                    .getValueByTitle(nameValuePair[1]));
                            // id = Cache.getInstance(context)
                            // .getKeyCardTypeId(KeyCardTypeEnum.visa)
                            keyCard.setKeyCardTypeId(keyCardTypeID);


                            valueIds = new long[split.length - 1];

                            for (int i = 1; i < split.length; i++) {

                                // XXX

                                String nameString = split[i].split(":")[0];

                                FieldNamesEnum name = FieldNamesEnum
                                        .getByName(nameString);
                                Field field = fieldDataSource.get(name.getName());
                                long fiedId = field.getId();

                                if (name.equals(FieldNamesEnum.DEFAULT)) {
                                    field = new Field();
                                    field.setCreateDate(new Date().getTime());
                                    field.setModifyDate(new Date().getTime());
                                    field.setFieldTypeId(2);
                                    field.setName(nameString);
                                    fiedId = fieldDataSource.insert(field);

                                }
                                try {
                                    String data = split[i].split(":")[1];

                                    Value value = createValue(field, data,
                                            keyCardId, date);

                                    valueIds[i - 1] = value.getId();

                                } catch (Exception e) {
                                    continue;
                                }

                            }


                        } else {
                            //it is a note or a site

                            if (next[0].equals(String.valueOf("http://sn"))) {
                                // it is a note
                                Long keyCardTypeID = Cache
                                        .getInstance(context)
                                        .getKeyCardTypeId(
                                                KeyCardTypeEnum.note);
                                keyCard.setKeyCardTypeId(keyCardTypeID);
                                Note note = new Note();
                                Value[] values = note.createValues(context);
                                valueIds = new long[1];
                                values[0].setData(next[3]);
                                values[0].setKeyCardId(keyCardId);
                                valueIds[0] = valuesDataSource.insert(values[0]);

                            }  //it is a site
                            else {
                                Long keyCardTypeID = Cache
                                        .getInstance(context)
                                        .getKeyCardTypeId(
                                                KeyCardTypeEnum.site);
                                keyCard.setKeyCardTypeId(keyCardTypeID);

                                Site site = new Site();
                                Value[] values = site.createValues(context);
                                values[0].setData(next[0]);
                                values[0].setKeyCardId(keyCardId);
                                values[1].setData(next[1]);
                                values[1].setKeyCardId(keyCardId);
                                values[2].setData(next[2]);
                                values[2].setKeyCardId(keyCardId);
                                values[3].setData(next[3]);
                                values[3].setKeyCardId(keyCardId);

                                valueIds = new long[values.length];
                                for (int i = 0; i < valueIds.length; i++) {
                                    //inserting values and getting the ids
                                    valueIds[i] = valuesDataSource.insert(values[i]);
                                }
//                                keyCard.setValueIds(valueIds);

                            }
                        }
                        keyCard.setValueIds(valueIds);
                        keyCardDataSource.update(keyCard);
                        imported++;
                    }
                    Log.w("csv import value", "------------");
                    Log.w("csv import value number of elements", next.length
                            + "");
                    Log.w("csv import value elements", Arrays.toString(next));
                    // }

                } else {
                    break;
                }

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {

        }

    }

    /**
     * @param field
     * @param data
     * @param date
     * @param keyCardId
     * @return
     * @throws IllegalAccessException
     */
    private Value createValue(Field field, String data, long keyCardId,
                              long date) throws IllegalAccessException {
        if(field.getFieldTypeId()==3){
            //it is a date string, parse it to our date format!!!
            String formattedDate = Common.format(data);
            if(formattedDate != null){
                data = formattedDate;
            }
        }

        Value value = new Value();
        value.setCreateDate(date);
        value.setModifyDate(date);
        value.setData(data);
        value.setFieldId(field.getId());
        value.setKeyCardId(keyCardId);
        value.setId(valuesDataSource.insert(value));

        return value;
    }

    /**
     * Checks if the file has consistent data. All elements has to have the same
     * number of values like the first one - the defying one.
     *
     * @param csvData
     * @return
     */
    @SuppressWarnings("unused")
	private boolean checkFileStructure(List<String[]> csvData) {
        if ((csvData == null) || (csvData.size() < 2)) {
            return false;
        }
        int numberOfValuesDefined = csvData.get(0).length;
        for (String[] strings : csvData) {
            if (strings.length != numberOfValuesDefined) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the number of all elements in the csv.
     */
    public int getAll() {
        return this.all;
    }

    /**
     * @return the number of imported elements from csv.
     */
    public int getImported() {
        return this.imported;
    }

    /**
     * Checks if the tag exists in the data base and returns its' id,
     * if not existing inserts it and returns the id
     *
     * @param tagName name of the tag
     * @return id of the tag
     */
    private long getTagId(String tagName) throws Exception {
        Tag tag = new Tag();
        tag.setName(tagName);
        return tagDataSource.insertIfNotExisting(tag);
    }
}

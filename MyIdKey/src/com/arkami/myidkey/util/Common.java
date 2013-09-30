/**
 * 
 */
package com.arkami.myidkey.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.tables.GenericDataObject;
import com.arkami.myidkey.database.tables.Tag;

/**
 * @author sbahdikyan
 * 
 */
public class Common {
	// compares strings alphabetically
	public static final Comparator<String> STRING_COMPARATOR = new StringComparator();
    public static final String[] dateFormats = {"M,d,yyyy", "MM,d,yyyy", "MMM,d,yyyy", "MMMM,d,yyyy",
            "M,yyyy", "MM,yyyy", "MMM,yyyy", "MMMM,yyyy"};
    public static final SimpleDateFormat applicationDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    /**
     * Fromats date string to M/dd/Y
     * @param date
     * @return
     */
    public static String format(String date){
        for(int i = 0; i < dateFormats.length; i++){
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormats[i], Locale.ENGLISH);
            try {
                Date dateObject = dateFormat.parse(date);
                Log.w("the date",applicationDateFormat.format(dateObject));
                return applicationDateFormat.format(dateObject);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

	/**
	 * Creates an image from android system intent data - selected or taken
	 * image.
	 * 
	 * @param filePath
	 *            intent from witch we will get the image
	 * @return bitmap
	 */
	public static Bitmap getImageFromIntent(String filePath, boolean isThumb) {

		if ((filePath == null) || (filePath.length() < 1)) {
			return null;
		}
		Bitmap image = BitmapFactory.decodeFile(filePath);
        Log.w("File Path:", filePath);
        if ((isThumb)&&(image != null)) {
			image = Bitmap.createScaledBitmap(image, 64, 48, false);
		}
		return image;
	}

	/**
	 * Gets the bytes from a file, needed for writing in database
	 * 
	 * @param file
	 *            file to get bytes from
	 * @return bytes from file if exists
	 * @throws Exception
	 */
	public static byte[] read(File file) throws Exception {
		if ((file != null) && (file.exists()) && (file.canRead())) {
			ByteArrayOutputStream outputStream = null;
			InputStream inputStream = null;
			try {
				byte[] buffer = new byte[4096];
				outputStream = new ByteArrayOutputStream();
				inputStream = new FileInputStream(file);
				int read = 0;
				while ((read = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, read);
				}
			} finally {

				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
			return outputStream.toByteArray();
		}
		return null;
	}

	/**
	 * @return All key cards' tags, from resources
	 */
	public static List<Tag> getPredifinedTags(Context context) {

		List<Tag> tags = new ArrayList<Tag>();
		String[] tagNames = context.getResources().getStringArray(
				R.array.tags_list);
		for (String tagName : tagNames) {
			Tag tag = new Tag();
			tag.setName(tagName);
			tags.add(tag);
		}
		return tags;
	}

	/**
	 * Copies file to internal device storage.
	 * 
	 * @param file
	 *            to be copied to internal storage
	 * @return
	 * @return true if succeeds
	 * @throws IOException
	 */
	public static void copyFileToAppFolder(File file, Context context)
			throws IOException {
		CopyFile copyFile = new CopyFile(context);
		copyFile.execute(file);
	}

	/**
	 * @param data
	 * @return
	 */
	public static List<String> getListItems(String data) {
		List<String> items = null;
		if ((data != null) && (data.length() > 0)) {
			String[] split = data.split(GenericDataObject.SEPARATOR, 0);
			items = new ArrayList<String>(split.length);
			for (int i = 0; i < split.length; i++) {
				String item = split[i];
				if (item.length() > 0) {
					items.add(item);
				}
			}
		}
		return items;
	}

    /**
     * @param id type of the item
     * @return image of that type
     */
    public static int getImageResourseId(Long id) {
        switch (id.intValue()) {

            case 1:
                return R.drawable.globe;
            case 2:
                return R.drawable.pin;
            case 3:
                return R.drawable.bank_account;
            case 4:
                return R.drawable.clothing;
            case 5:
                return R.drawable.visa;
            case 6:
                return R.drawable.database;
            case 7:
                return R.drawable.drivers_license;
            case 8:
                return R.drawable.letter;
            case 9:
                return R.drawable.form_fill;
            case 10:
                return R.drawable.instant_messenger;
            case 11:
                return R.drawable.insurance;
            case 12:
                return R.drawable.membership;
            case 13:
                return R.drawable.passport;
            case 14:
                return R.drawable.prescriptio;
            case 15:
                return R.drawable.reward;
            case 16:
                return R.drawable.server;
            case 17:
                return R.drawable.social_security;
            case 18:
                return R.drawable.software_license;
            case 19:
                return R.drawable.vehiclee;
            case 20:
                return R.drawable.wifi;
            case 21:
                return R.drawable.folder;
            default:
                return R.drawable.key_cards;
        }

    }

}

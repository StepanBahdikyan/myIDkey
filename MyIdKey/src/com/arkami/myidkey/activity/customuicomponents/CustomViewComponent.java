package com.arkami.myidkey.activity.customuicomponents;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.KeyCardEditActivity;
import com.arkami.myidkey.common.DatePickerFragment;
import com.arkami.myidkey.database.datasources.FieldDataSource;
import com.arkami.myidkey.database.datasources.FieldTypeDataSourse;
import com.arkami.myidkey.database.datasources.PasswordSettingsDataSource;
import com.arkami.myidkey.database.tables.Field;
import com.arkami.myidkey.database.tables.FieldType;
import com.arkami.myidkey.database.tables.PasswordSettings;
import com.arkami.myidkey.database.tables.Value;
import com.arkami.myidkey.listeners.DatePickerListener;
import com.arkami.myidkey.util.Common;
import com.arkami.myidkey.util.Generator;

/**
 * @author sbahdikyan
 */
public class CustomViewComponent extends LinearLayout implements
        DatePickerListener, Parcelable {

    private TextView title;
    private View data;
    private Value value;
    private FieldTypeDataSourse fieldTypeDataSourse;
    private FieldDataSource fieldDataSource;
    private Field field;
    private Context context;
    private ArrayAdapter<String> spinnerAdapter;
    private FieldTypeEnum fieldType;
    private Button generate;
    private static int id = 0;

    /**
     * @param context
     */
    public CustomViewComponent(Context context) {
        super(context);
        fieldDataSource = new FieldDataSource(context);
        fieldTypeDataSourse = new FieldTypeDataSourse(context);
        this.context = context;
    }

    /**
     * @param context
     * @param value
     * @throws IllegalAccessException
     */
    public CustomViewComponent(Context context, Value value)
            throws IllegalAccessException {
        super(context);

        this.context = context;
        this.value = value;
        fieldDataSource = new FieldDataSource(context);
        fieldTypeDataSourse = new FieldTypeDataSourse(context);
        field = fieldDataSource.get(value.getFieldId());
        this.setValue(value);
        initializeComponents(this.value);

    }

    /**
     * @throws IllegalAccessException
     */
    private void initializeComponents(Value value)
            throws IllegalAccessException {

        FieldType fieldTypeObject = fieldTypeDataSourse.get(field
                .getFieldTypeId());
        if (fieldTypeObject != null) {

            this.fieldType = FieldTypeEnum.valueOf(fieldTypeObject.getName());

            switch (this.fieldType) {
                case TEXT:
                    createTextField();
                    break;
                case DATE:
                    createDateField();
                    break;
                case NUMERICAL:
                    createNumericField();
                    break;
                case SPINNER:
                    createSpinnerField();
                    break;
                case PASSWORD:
                    createPasswordField();
                    break;
                default:
                    createTextField();
                    break;
            }
        } else {
            Toast.makeText(context, "fieldType is null", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     *
     */
    private void createSpinnerField() {
        this.fieldType = FieldTypeEnum.SPINNER;
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_component_spinner, this, true);

        this.title = (TextView) super
                .findViewById(R.id.custom_component_spinner_name);
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        this.title.setText(field.getName());
        this.data = findViewById(R.id.custom_component_spinner_spinner);
//        ((Spinner)this.data).setImeOptions(EditorInfo.IME_ACTION_NEXT);
        // spinner items in field special value
        // value - only the selection
        List<String> spinnerValues = Common.getListItems(field
                .getPossibleValues());
        String spinnerSelection = value.getData();
        this.spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, spinnerValues);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) this.data).setAdapter(spinnerAdapter);

        int itemsCount = spinnerValues.size();
        for (int i = 0; i < itemsCount; i++) {
            if (spinnerAdapter.getItem(i).toLowerCase(Locale.ENGLISH)
                    .equals(spinnerSelection.toLowerCase(Locale.ENGLISH))) {
                ((Spinner) this.data).setSelection(i);
            }
        }

        ((Spinner) this.data)
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        value.setData(spinnerAdapter.getItem(arg0
                                .getSelectedItemPosition()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                });

    }

    /**
     *
     */
    private void createNumericField() {
        this.fieldType = FieldTypeEnum.NUMERICAL;
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_component_numeric_text, this, true);

        this.title = (TextView) super
                .findViewById(R.id.custom_component_numeric_text_title);
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        this.title.setText(field.getName());
        this.data = findViewById(R.id.custom_component_numeric_text_edit_text);

        this.data.setId(id++);
        ((TextView) this.data).setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ((TextView) this.data).setText(String.valueOf(value.getData()));
        ((TextView) this.data).addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                value.setData(((TextView) data).getText().toString());
            }
        });

    }

    private void createPasswordField() {
        this.fieldType = FieldTypeEnum.PASSWORD;
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_component_password, this, true);

        this.title = (TextView) super
                .findViewById(R.id.custom_component_password_name);
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        this.title.setText(field.getName());
        this.data = findViewById(R.id.custom_component_password_edit_text);

        this.data.setId(id++);
        ((TextView) this.data).setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ((TextView) this.data).setText(String.valueOf(value.getData()));
        ((TextView) this.data).addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                value.setData(((TextView) data).getText().toString());
            }
        });
        generate = (Button) findViewById(R.id.custom_component_password_generate);
        generate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordSettingsDataSource passwordSettingsDataSource = new PasswordSettingsDataSource(context);
                PasswordSettings passwordSettings = passwordSettingsDataSource.getPasswordSettings();
                String password = Generator.generate(
                        passwordSettings.hasSpecialCharacters(),
                        passwordSettings.hasNumbers(),
                        passwordSettings.hasSmallLetters(),
                        passwordSettings.hasUppercaseLetters(),
                        passwordSettings.isHexadecimal(),
                        passwordSettings.getPasswordLength());
                ((TextView) CustomViewComponent.this.data).setText(password);

            }
        });
    }

    private void createDateField() {
        this.fieldType = FieldTypeEnum.DATE;
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_component_free_text, this, true);
        this.title = (TextView) super
                .findViewById(R.id.custom_component_free_text_name);
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        this.title.setText(field.getName());
        this.data = findViewById(R.id.custom_component_free_text_edit_text);
//        this.data.setBackground(getResources().getDrawable(R.drawable.keycard_selector));
        this.data.setId(id++);
        ((TextView) this.data).setText(String.valueOf(value.getData()));
        ((TextView) this.data).setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ((TextView) this.data).setInputType(InputType.TYPE_NULL);
//		((TextView) this.data)
//				.setBackgroundResource(android.R.drawable.editbox_background_normal);
        ((TextView) this.data).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();
                Bundle datePickerArguments = new Bundle(3);
                datePickerArguments.putParcelable(DatePickerListener.CONTEXT,
                        ((KeyCardEditActivity) context));
                dateFragment.setArguments(datePickerArguments);
                datePickerArguments.putInt(DatePickerListener.ID,
                        R.id.custom_component_free_text_edit_text);
                datePickerArguments.putParcelable(DatePickerListener.LISTENER,
                        CustomViewComponent.this);
                dateFragment.show(((SherlockFragmentActivity) context)
                        .getSupportFragmentManager(), "datePicker");
                return;
            }
        });
    }

    /**
     * creates a text field;
     */
    private void createTextField() {
        boolean iSsingleLine = true;
        this.fieldType = FieldTypeEnum.TEXT;
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_component_free_text, this, true);
        this.title = (TextView) super
                .findViewById(R.id.custom_component_free_text_name);

        this.setLayoutParams(new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        if ((String.valueOf(field.getName()).equals(String.valueOf("Note")))
                || (String.valueOf(field.getName()).equals(String.valueOf("Notes")))) {
            iSsingleLine = false;
        }
        this.title.setText(field.getName());
        this.data = findViewById(R.id.custom_component_free_text_edit_text);

        this.data.setId(id++);
        ((TextView) this.data).setText(String.valueOf(value.getData()));
        ((TextView) this.data).setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ((TextView) this.data).setSingleLine(iSsingleLine);
        ((TextView) this.data).addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                value.setData(((TextView) data).getText().toString());
            }
        });
    }

    /**
     * @return the value
     */
    public Value getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    /**
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.data.setEnabled(editable);
        if ((generate != null) && (this.fieldType == FieldTypeEnum.PASSWORD)) {
            if (editable) {
                generate.setVisibility(VISIBLE);
            } else {
                generate.setVisibility(GONE);
            }
        }
    }

    /**
     * @return
     */
    public FieldTypeEnum getFieldType() {
        return fieldType;
    }

    @Override
    public void onDateSet(int id, String date) {
    	if (id == R.id.custom_component_free_text_edit_text) {
			((TextView) this.data).setText(date);
			this.value.setData(date);
		}
    }
}

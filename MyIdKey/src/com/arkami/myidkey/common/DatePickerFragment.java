/**
 * 
 */
package com.arkami.myidkey.common;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.arkami.myidkey.listeners.DatePickerListener;

/**
 * @author sbahdikyan
 * 
 */
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private Context context;
	private int id;
	private DatePickerListener listener;

	public DatePickerFragment() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// this.id = this.getArguments().getInt(DatePickerListener.ID);
		this.context = (Context) this.getArguments().get(
				DatePickerListener.CONTEXT);
		this.listener = (DatePickerListener) this.getArguments().get(
				DatePickerListener.LISTENER);
		this.id = this.getArguments().getInt(DatePickerListener.ID);

		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(this.context, this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker picker, int year, int month, int day) {
		listener.onDateSet(id, month + "/" + day + "/" + year);

	}
}

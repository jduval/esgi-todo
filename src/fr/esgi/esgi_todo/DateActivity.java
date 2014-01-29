package fr.esgi.esgi_todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DateActivity extends Activity {
	
	private String isUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);
		
		EditText editText = (EditText) findViewById( R.id.editTextInitialDate );
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
		editText.setText( sdf.format( new Date() ));
		
		EditText editTextHour = (EditText) findViewById( R.id.editTextInitialHour );
		SimpleDateFormat sdfhour = new SimpleDateFormat( "kk:mm" );
		editTextHour.setText(sdfhour.format(new Date()));
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("UPDATE_TASK_DATE") != null) {
					isUpdate = "yes";
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
					
					EditText ini_d_button = (EditText)findViewById(R.id.editTextInitialDate);
					ini_d_button.setText(prefs.getString("initial_date", null));
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date, menu);
		return true;
	}
	
	public void openDatePickerDialog(View v)
	{ 
	    Calendar c = Calendar.getInstance();
	    int mYear = c.get(Calendar.YEAR);
	    int mMonth = c.get(Calendar.MONTH);
	    int mDay = c.get(Calendar.DAY_OF_MONTH);
	    
	    //datepicker callback
	    OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

	        @Override
	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            EditText editText = (EditText) findViewById( R.id.editTextInitialDate );
	            
	            int monthCustom;
	            monthCustom = monthOfYear + 1;
	            String finalMonth = null;

				if (monthCustom < 10) {
	            	finalMonth = "0" + String.valueOf(monthCustom);
				} else {
					finalMonth = String.valueOf(monthCustom);
				}

	            editText.setText(dayOfMonth + "/" + finalMonth + "/" + year);
	        }
        
		};
		
		//display datepicker
	    DatePickerDialog dp = new DatePickerDialog(this,
	            dateListener,
	            mYear, mMonth, mDay);
	    dp.show();
	}
	
	public void openDatePickerDialogHour(View v)
	{ 
	    Calendar c = Calendar.getInstance();
	    int mHour = c.get(Calendar.HOUR_OF_DAY);
	    int mMinute = c.get(Calendar.MINUTE);
	    
	    //timepicker callback
	    OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				EditText editText = (EditText) findViewById( R.id.editTextInitialHour );
				editText.setText(hourOfDay + ":" + minute);
			}
        
		};
		
		//display timepicker
	    TimePickerDialog tp = new TimePickerDialog(this,
	            timeListener,
	            mHour, mMinute, true);
	    tp.show();
	}
	
	public void openDatePickerDialogRecall(View v)
	{ 
	    Calendar c = Calendar.getInstance();
	    int mYear = c.get(Calendar.YEAR);
	    int mMonth = c.get(Calendar.MONTH);
	    int mDay = c.get(Calendar.DAY_OF_MONTH);
	    
	    //datepicker callback
	    OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

	        @Override
	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            EditText editText = (EditText) findViewById( R.id.editTextRecallDate );
	            
	            int monthCustom;
	            monthCustom = monthOfYear + 1;
	            String finalMonth = null;

				if (monthCustom < 10) {
	            	finalMonth = "0" + String.valueOf(monthCustom);
				} else {
					finalMonth = String.valueOf(monthCustom);
				}

	            editText.setText(dayOfMonth + "/" + finalMonth + "/" + year);
	        }

		};
		
		//display datepicker
	    DatePickerDialog dp = new DatePickerDialog(this,
	            dateListener,
	            mYear, mMonth, mDay);
	    dp.show();
	}

	public void openDatePickerDialogRecallHour(View v)
	{ 
	    Calendar c = Calendar.getInstance();
	    int mHour = c.get(Calendar.HOUR);
	    int mMinute = c.get(Calendar.MINUTE);

	    //timepicker callback
	    OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				EditText editText = (EditText) findViewById( R.id.editTextRecallHour );
				editText.setText(hourOfDay + ":" + minute);
			}
        
		};

		//display timepicker
	    TimePickerDialog tp = new TimePickerDialog(this,
	            timeListener,
	            mHour, mMinute, true);
	    tp.show();
	}

	public void validateDate(View v) {
		EditText initialDate = (EditText) findViewById( R.id.editTextInitialDate );
		EditText initialHour = (EditText) findViewById( R.id.editTextInitialHour );
		EditText recallDate = (EditText) findViewById( R.id.editTextRecallDate );
		EditText recallHour = (EditText) findViewById( R.id.editTextRecallHour );

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();

		String initialDateKey = "initial_date";
		String initialHourKey = "initial_hour";
		String recallDateKey = "recall_date";
		String recallHourKey = "recall_hour";
		
		editor.putString(initialDateKey, initialDate.getText().toString());
		editor.putString(initialHourKey, initialHour.getText().toString());

		if (recallDate.getText().toString() != "") {
			editor.putString(recallDateKey, recallDate.getText().toString());

			if (recallHour.getText().toString() != "") {
				editor.putString(recallHourKey, recallHour.getText().toString());
			}
		}

		editor.commit();

		if (isUpdate == "yes") {
			Intent intent3 = new Intent(this, CurrentTaskActivity.class);
			intent3.putExtra("updated_initial_date", initialDate.getText().toString());
			intent3.putExtra("updated_initial_hour", initialHour.getText().toString());
			if (recallDate.getText().toString() != "") {
				intent3.putExtra("updated_recall_date", recallDate.getText().toString());
				if (recallHour.getText().toString() != "") {
					intent3.putExtra("updated_recall_hour", recallHour.getText().toString());
				}
			}
			setResult(RESULT_OK, intent3);
			finish();
		} else {
			Intent intent = new Intent(this, NewTaskActivity.class);
			intent.putExtra("EXTRA_DATE", "set!");
			startActivity(intent);
		}

	}
	
}

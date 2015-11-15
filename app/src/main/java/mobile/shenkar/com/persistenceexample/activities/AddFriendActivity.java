package mobile.shenkar.com.persistenceexample.activities;

import mobile.shenkar.com.persistenceexample.R;
import mobile.shenkar.com.persistenceexample.common.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddFriendActivity extends Activity
{
	
	private EditText nameEt;
	private EditText phoneEt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		nameEt = (EditText) findViewById(R.id.editTextName);
		phoneEt = (EditText) findViewById(R.id.editTextPhone);
	}
	
    public void okClicked(View v)
    {
    	if(nameEt == null || phoneEt == null) return;
    	String name = nameEt.getText().toString();
    	String phone = phoneEt.getText().toString();
    	// Prepare data intent 
    	  Intent data = new Intent();
    	  //Put the extras.
    	  data.putExtra(AppConst.ExtrasFriendName, name);
    	  data.putExtra(AppConst.ExtrasFriendPhone,phone);
    	  // Activity finished ok, return the data
    	  setResult(RESULT_OK, data);
    	  finish(); 
    }
	

}

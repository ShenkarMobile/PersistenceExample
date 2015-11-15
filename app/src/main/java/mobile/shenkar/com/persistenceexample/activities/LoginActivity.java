package mobile.shenkar.com.persistenceexample.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mobile.shenkar.com.persistenceexample.R;
import mobile.shenkar.com.persistenceexample.bl.LoginController;
import mobile.shenkar.com.persistenceexample.common.User;


public class LoginActivity extends Activity {

	private EditText userNameEditText;
	private EditText passwordEditText;
	private LoginController controller;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = new LoginController(this);
        //ask the controller if the user is logged in.
        if(controller.isLoggedIn())
        {
        	//In case the user is logged in start the main activity.
        	startMainActivity();
        	return;
        }
        //get the useName and password edit text view 
        userNameEditText = (EditText) findViewById(R.id.editTextUserName);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
    }
    
    public void logInClicked(View v)
    {
    	//get the password and the user name from the edit text.
    	if(userNameEditText!=null && passwordEditText!=null)
    	{
    		String userName  = userNameEditText.getText().toString();
    		String pass = passwordEditText.getText().toString();
    		User u = controller.getUser(userName,pass);
    		//the user is exists, set the IsLogin flag to true.
    		if(u!=null)
    		{
    			controller.setLogedIn(u);
    			startMainActivity();
    			return;
    		}
    		//log in was failed.
    		Toast.makeText(this, "User name or password is incorrect", Toast.LENGTH_LONG).show();
    	}
		
	}
    
    public void startMainActivity()
    {
		//Explicit intent.
		Intent  i = new Intent(this,MainActivity.class);
		//Start the activity
		startActivity(i);
		finish();
    }
}

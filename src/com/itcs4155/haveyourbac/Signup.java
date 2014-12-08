//DONE. DON'T TOUCH!!

package com.itcs4155.haveyourbac;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Signup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		final Button signup = (Button)findViewById(R.id.signupSignup);
		signup.setEnabled(false);
		final Button cancel = (Button)findViewById(R.id.signupCancel);
		final EditText username = (EditText)findViewById(R.id.signupUsername);
		final EditText password = (EditText)findViewById(R.id.signupPassword);
		final EditText email = (EditText)findViewById(R.id.signupEmail);
		final EditText weight = (EditText)findViewById(R.id.signupWeight);
		final RadioGroup gender = (RadioGroup)findViewById(R.id.radioGroup1);

		username.addTextChangedListener(new TextWatcher() {


			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//Check if 's' is empty 

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});

		password.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
					int before, int count) {

				if(!username.getText().toString().isEmpty()){
					signup.setEnabled(true);
				}
			}
		});

		signup.setOnClickListener(new View.OnClickListener(){

			public void onClick(View view){

				//getting value from Radio Button
				int id= gender.getCheckedRadioButtonId();
				View radioButton = gender.findViewById(id);
				int radioId = gender.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) gender.getChildAt(radioId);
				String selection = (String) btn.getText();

				//Changing Weight into a number
				String weightNum = weight.getText().toString();

				ParseUser user = new ParseUser();
				user.setUsername(username.getText().toString());
				user.setPassword(password.getText().toString());
				user.setEmail(email.getText().toString());
				user.put("weight", weightNum);
				user.put("gender", selection);

				user.signUpInBackground(new SignUpCallback() {

					public void done(ParseException e) {
						if (e == null) {
							// Hooray! Let them use the app now.
							Toast.makeText(getBaseContext(), "User Created!", Toast.LENGTH_LONG).show();
							finish();
						} else {
							// Sign up didn't succeed. Look at the ParseException
							// to figure out what went wrong
							Toast.makeText(getBaseContext(), "Something went wrong. Please try again!", Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		});

		cancel.setOnClickListener(new View.OnClickListener(){

			public void onClick(View view){
				// Finish activity
				finish();
			}

		});

	}
}
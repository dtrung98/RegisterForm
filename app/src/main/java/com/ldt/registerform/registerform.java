package com.ldt.registerform;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerform extends AppCompatActivity  implements View.OnClickListener, CheckBox.OnCheckedChangeListener {
    EditText edit_username;
    EditText edit_password;
    EditText edit_retypePassword;
    EditText edit_birthDate;
    AppCompatButton button_select, button_reset, button_signUp;
    RadioButton radio_male, radio_female;
    CheckBox check_tennis, check_futbal, check_others;
    ImageButton exitImageButton;
    AppCompatButton exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);
        attachViewID();
        setEventListeners();
    }
    private void attachViewID() {
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        edit_retypePassword = findViewById(R.id.edit_retype);
        edit_birthDate = findViewById(R.id.edit_birthdate);

        button_select = findViewById(R.id.btn_select);
        button_reset = findViewById(R.id.btn_reset);
        button_signUp = findViewById(R.id.btn_sign_up);

        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);

        check_tennis = findViewById(R.id.check_tennis);
        check_futbal = findViewById(R.id.check_futbal);
        check_others = findViewById(R.id.check_others);

        exitImageButton = findViewById(R.id.exitImageButton);
        exitButton = findViewById(R.id.exitButton);
    }
    private void setEventListeners() {
        button_select.setOnClickListener(this);
        button_reset.setOnClickListener(this);
        button_signUp.setOnClickListener(this);

        exitButton.setOnClickListener(this);
        exitImageButton.setOnClickListener(this);

        radio_male.setOnCheckedChangeListener(this);
        radio_female.setOnCheckedChangeListener(this);

        check_tennis.setOnCheckedChangeListener(this);
        check_futbal.setOnCheckedChangeListener(this);
        check_others.setOnCheckedChangeListener(this);
    }
    enum GENDER {
        BLANK,MALE, FEMALE
    }
    private GENDER gender = GENDER.BLANK;
    private boolean[] hobbies = new boolean[] {false,false,false};
    private void select_click() {
        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
      DatePickerDialog datePickerDialog =   new DatePickerDialog(registerform.this, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //DO SOMETHING
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date(year-1900,monthOfYear, dayOfMonth);
                String formatedDate = sdf.format(date);
                edit_birthDate.setText(formatedDate);
            }
        }, mYear, mMonth, mDay);
      datePickerDialog.show();
    };

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private void signUp_click() {
        String username = edit_username.getText().toString();
        String pass = edit_password.getText().toString();
        String retype = edit_retypePassword.getText().toString();
        String birthDate = edit_birthDate.getText().toString();
        if(username.equals(""))  {
            Toast.makeText(this,"Username can not be empty",Toast.LENGTH_SHORT).show();
            edit_username.requestFocus();
            return;
        }

        if(pass.equals("")) {
            Toast.makeText(this,"Password can not be empty",Toast.LENGTH_SHORT).show();
            edit_password.requestFocus();
            return;
        }

        if(retype.equals("")) {
            Toast.makeText(this,"Please enter the confirm password",Toast.LENGTH_SHORT).show();
            edit_retypePassword.requestFocus();
            return;
        }

        if(!pass.equals(retype)) {
            Toast.makeText(this,"Password does not match the confirm password",Toast.LENGTH_SHORT).show();
            edit_retypePassword.requestFocus();
            return;
        }

        if(birthDate.equals("")) {
            Toast.makeText(this,"Birth date can not be empty",Toast.LENGTH_SHORT).show();
            edit_birthDate.requestFocus();
            return;
        }
        if(!isValidDate(birthDate)) {
            Toast.makeText(this,"Birth date is not valid",Toast.LENGTH_SHORT).show();
            edit_birthDate.requestFocus();
            return;
        }

        if(gender==GENDER.BLANK) {
            Toast.makeText(this,"Gender must to be checked",Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(this,"Welcome! you have signed up successfully",Toast.LENGTH_SHORT).show();

        String result="Your profile\n";
        result+="Username : "+ username+"\n";
        result+="Password  : "+pass+"\n";
        result+="Birth date : "+birthDate+"\n";
        result +="Gender    :  " + ((gender==GENDER.MALE) ? "Male" : "Female") +"\n";
        result +="Hobbies  :"+((hobbies[0]) ? " Tennis" :"") +((hobbies[1]) ? " Futbal" : "") +((hobbies[0]) ? " Others" :"");
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }
    private void asking_reset_click() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Reset All");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                reset_click();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void reset_click() {
        edit_username.setText(null);
        edit_password.setText(null);
        edit_retypePassword.setText(null);
        edit_birthDate.setText(null);

        radio_male.setChecked(false);
        radio_female.setChecked(false);

        check_tennis.setChecked(false);
        check_futbal.setChecked(false);
        check_others.setChecked(false);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                select_click();
                break;
            case R.id.btn_reset:
                asking_reset_click();
                break;
            case R.id.btn_sign_up:
                signUp_click();
                break;
            case R.id.exitButton:
            case R.id.exitImageButton:
                Toast.makeText(this,"You are about to quit this app",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"You are about to quit this app",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.radio_male:
                if(b) gender = GENDER.MALE;
                break;
            case R.id.radio_female:
                if(b) gender = GENDER.FEMALE;
                break;
            case R.id.check_tennis:
               hobbies[0] = b;
                break;
            case R.id.check_futbal:
                hobbies[1] = b;
                break;
            case R.id.check_others:
                hobbies[2] = b;
                break;
        }
    }
}

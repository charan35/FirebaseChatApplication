package com.jayaraj.firebasechatapplication.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jayaraj.firebasechatapplication.R;
import com.jayaraj.firebasechatapplication.data.StaticConfig;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewEmployee extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    private Button save;
    TextView login;
    EditText username,fullname,password,cnfpassword,dob;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private LovelyProgressDialog waitingDialog;

    DatePickerDialog datePickerDialog;

    private static String TAG = "NewRegister";
    public static String STR_EXTRA_ACTION_REGISTER = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        //  toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        cnfpassword = findViewById(R.id.cnfpassword);

        dob = findViewById(R.id.dob);

        save = findViewById(R.id.register);
        login = findViewById(R.id.login);

        waitingDialog = new LovelyProgressDialog(this).setCancelable(false);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewEmployee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(NewEmployee.this,MainActivity.class);
                startActivity(intent);*/
               //onBackPressed();
                registerClosed();
            }
        });

    }

    private void registerClosed() {
        NewEmployee.super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        registerClosed();
    }

    public void clickedRegister(View view) {
        String fullnam= fullname .getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String repeatPassword = cnfpassword.getText().toString();
        String dateofbirth=dob.getText().toString();
        if(validate(user, pass, repeatPassword)){
            Intent data = new Intent();
            data.putExtra(StaticConfig.STR_EXTRA_NAME,fullnam);
            data.putExtra(StaticConfig.STR_EXTRA_USERNAME, user);
            data.putExtra(StaticConfig.STR_EXTRA_PASSWORD, pass);
            data.putExtra(StaticConfig.STR_EXTRA_DOB,dateofbirth);
            data.putExtra(StaticConfig.STR_EXTRA_ACTION, STR_EXTRA_ACTION_REGISTER);
            setResult(RESULT_OK, data);
            finish();
        }else {
            Toast.makeText(this, "Invalid email or not match password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate(String emailStr, String password1, String repeatPassword) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return password.length() > 0 && repeatPassword.equals(password1) && matcher.find();
    }


}

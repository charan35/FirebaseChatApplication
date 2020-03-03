package com.jayaraj.firebasechatapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.spark.submitbutton.SubmitButton;

public class Feedback extends AppCompatActivity {

    EditText name,email,mobile,feedback;
    SmileRating smileRating;
    SubmitButton submit;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.mobile);
        feedback = (EditText)findViewById(R.id.feedback);
        smileRating = (SmileRating) findViewById(R.id.smile_rating);
        submit = (SubmitButton)findViewById(R.id.submit);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        smileRating.setSelectedSmile(BaseRating.BAD);
                        Toast.makeText(Feedback.this, "BAD", Toast.LENGTH_LONG).show();
                        break;
                    case SmileRating.GOOD:
                        smileRating.setSelectedSmile(BaseRating.GOOD);
                        Toast.makeText(getApplicationContext(), "GOOD", Toast.LENGTH_LONG).show();
                        break;
                    case SmileRating.GREAT:
                        smileRating.setSelectedSmile(BaseRating.GREAT);
                        Toast.makeText(getApplicationContext(), "GREAT", Toast.LENGTH_LONG).show();
                        break;
                    case SmileRating.OKAY:
                        smileRating.setSelectedSmile(BaseRating.OKAY);
                        Toast.makeText(getApplicationContext(),"OKAY", Toast.LENGTH_LONG).show();
                        break;
                    case SmileRating.TERRIBLE:
                        smileRating.setSelectedSmile(BaseRating.TERRIBLE);
                        Toast.makeText(getApplicationContext(), "TERRIBLE", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(getApplicationContext(), "Selected Rating"+level, Toast.LENGTH_LONG).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                sendEmail();
            }
        });
    }

    private void addData(){
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Mobile = mobile.getText().toString();
        String Feedback = feedback.getText().toString();
        String Rating = smileRating.getSmileName(smileRating.getRating()-1).toString();

        if(!TextUtils.isEmpty(Email)){
            String id = databaseReference.push().getKey();
            Feedbackdata feedbackdata = new Feedbackdata(id,Name,Email,Mobile,Feedback,Rating);
            databaseReference.child(id).setValue(feedbackdata);
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data  not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        //Getting content for email
        String Email = email.getText().toString().trim();
        String Subject = name.getText().toString().trim();
        String Message = feedback.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();
        String Rating = smileRating.getSmileName(smileRating.getRating()-1).toString();

        //Creating SendMail object
        SendMail sm = new SendMail(this, Email, Subject, Message, Mobile, Rating);

        //Executing sendmail to send email
        sm.execute();
    }

}

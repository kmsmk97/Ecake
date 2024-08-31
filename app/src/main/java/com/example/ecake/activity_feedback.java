package com.example.ecake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class activity_feedback extends AppCompatActivity {

    //Define variables
    private EditText name,message;
    private FirebaseFirestore db;
    private String ufId;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.feeName);
        message = findViewById(R.id.feeMessage);
        Button submitBtn = findViewById(R.id.feeInBtn);
        Button showBtn = findViewById(R.id.feeShowBtn);

        db = FirebaseFirestore.getInstance();

        //Check button visible word update or save
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            submitBtn.setText("Update");
            //collect data for update
            //variables for update method
            String uname = bundle.getString("uname");
            ufId = bundle.getString("ufId");
            String umessage = bundle.getString("umessage");
            //Set update
            name.setText(uname);
            message.setText(umessage);


        }else{
            submitBtn.setText("Save");
        }

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_feedback.this, activity_show_feedback.class));
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collect all the input text fields
                String fname = name.getText().toString();
                String fmessage = message.getText().toString();

                Bundle bundle1= getIntent().getExtras();
                //check user wants to update or don't update data
                if(bundle1!=null){
                    String fid = ufId;
                    updateToFirestore (fid,fname,fmessage );
                }else{
                    String fid = UUID.randomUUID().toString();
                    saveToFirestore(fid,fname,fmessage);
                }

            }
        });
    }

    //Update method
    private void updateToFirestore(String fid,String fname,String fmessage){

                                                            //call the update method
        db.collection("Feedbacks").document(fid).update("fname",fname, "fmessage",fmessage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_feedback.this,"Data Updated",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity_feedback.this,"Error :"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_feedback.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Insert method
    private void saveToFirestore(String fid,String fname,String fmessage){

        if(!fname.isEmpty() && !fmessage.isEmpty()){
            HashMap<String,Object> map = new HashMap<>();
            map.put("fid",fid);
            map.put("fname",fname);
            map.put("fmessage",fmessage);
            //Database instance
            db.collection("Feedbacks").document(fid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(activity_feedback.this,"Feedback Saved",Toast.LENGTH_SHORT).show();
                           }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity_feedback.this,"Save Error",Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(this,"Please fill those all fields",Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.ecake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_show_feedback extends AppCompatActivity {

    private RecyclerView recycleFeedbackView;
    private FirebaseFirestore db;
    private feedAdapter adapter;
    private List<feedModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feedback);

        recycleFeedbackView=findViewById(R.id.recycleFeedbackView);
        recycleFeedbackView.setHasFixedSize(true);
        recycleFeedbackView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        adapter = new feedAdapter(this,list);
        recycleFeedbackView.setAdapter(adapter);

        ItemTouchHelper touchHelper =new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recycleFeedbackView);

        showData();

    }
    public void showData(){
        db.collection("Feedbacks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();

                        for(DocumentSnapshot snapshot: task.getResult()){
                            feedModel model = new feedModel(snapshot.getString("fid"), snapshot.getString("fname"), snapshot.getString("fmessage"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_show_feedback.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}












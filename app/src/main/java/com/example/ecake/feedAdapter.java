package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class feedAdapter extends RecyclerView.Adapter<feedAdapter.FeedView> {
    private  activity_show_feedback activity;
    private  List<feedModel> feedList;
    private  FirebaseFirestore db =FirebaseFirestore.getInstance();

    //contructor for class
    public feedAdapter (activity_show_feedback activity,List<feedModel> feedList){
        this. activity = activity;
        this.feedList=feedList;
    }

    //Send data to insert form for update function
    public void updateFeed(int position){
        feedModel item = feedList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("ufId",item.getFid());
        bundle.putString("uname",item.getFname());
        bundle.putString("umessage",item.getFmessage());

        //This method will pass data to the activity_feedback(Feedback Main)
        Intent intent = new Intent(activity, activity_feedback.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }

    //delete from the database
    public void deleteFeed(int position){
        feedModel item = feedList.get(position);
        db.collection("Feedbacks").document(item.getFid()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        feedList.remove(position);
        notifyItemRemoved(position);
        activity.showData();

    }
    @NonNull
    @Override
    public FeedView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.feedadapter , parent, false);
        return new FeedView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedView holder, int position) {
       holder.fname.setText(feedList.get(position).getFname());
       holder.fmessage.setText(feedList.get(position).getFmessage());

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class FeedView extends RecyclerView.ViewHolder{

        TextView fname,fmessage;
        public FeedView(@NonNull  View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fNameR);
            fmessage = itemView.findViewById(R.id.fMessageR);

        }
    }

}

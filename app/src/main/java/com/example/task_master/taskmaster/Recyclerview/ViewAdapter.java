package com.example.task_master.taskmaster.Recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.example.task_master.R;

import java.util.List;




public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {

    Context context;
    List<com.amplifyframework.datastore.generated.model.Task> Taskslist;
    MyOnClickListener listener;

    public ViewAdapter(Context context, List<com.amplifyframework.datastore.generated.model.Task> tasklist, MyOnClickListener myOnClickListener) {
        this.context = context;
        this.Taskslist = Taskslist;
        this.listener = listener;
    }

    public interface MyOnClickListener {
        void onClicked(com.amplifyframework.datastore.generated.model.Task task);
    }
    public ViewAdapter(List<com.amplifyframework.datastore.generated.model.Task> Taskslist, MyOnClickListener listener) {
        this.Taskslist = Taskslist;
        this.listener = listener;
    }

    public ViewAdapter(Context context, List<com.amplifyframework.datastore.generated.model.Task> Taskslist) {
        this.context = context;
        this.Taskslist = Taskslist;
    }

    public ViewAdapter(List<com.amplifyframework.datastore.generated.model.Task> tasklist) {
        this.Taskslist = tasklist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v= layoutInflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(v,listener);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("position", "onBindViewHolder: ..."+Taskslist.get(position));
        holder.Title.setText(Taskslist.get(position).getTitle());
        holder.Body.setText(Taskslist.get(position).getDescription());
        holder.State.setText(Taskslist.get(position).getStatus());
        holder.itemView.setOnClickListener(view ->
        {
            listener.onClicked(Taskslist.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return Taskslist.size() ;
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Title, Body, State;
        MyOnClickListener listener ;
        public MyViewHolder(@NonNull View itemView,MyOnClickListener listener) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Body = itemView.findViewById(R.id.Body);
            State = itemView.findViewById(R.id.State);

        }
    }

}


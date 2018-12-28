package com.scnu.learningboundless.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.activity.CreateTaskActivity;
import com.scnu.learningboundless.activity.TaskDetailActivity;
import com.scnu.learningboundless.bean.task.Task;
import com.scnu.learningboundless.db.TaskDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;
    private List<Task> mTaskList;
    private TaskDao mTaskTableDao;

    public TaskAdapter(Context context, List<Task> taskList, TaskDao taskTableDao){
        mContext = context;
        mTaskList = taskList;
        mTaskTableDao = taskTableDao;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Task task = mTaskList.get(position);

        holder.mTvDate.setText(task.getStartTime());

        holder.mTvTask.setText(task.getContent());

        holder.mTvTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDetailActivity.actionStart(mContext, task.getStartTime());
            }
        });

        holder.mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTaskActivity.actionEditStart(mContext, task.getStartTime());
            }
        });

        holder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTaskActivity.actionEditStart(mContext, task.getStartTime());
            }
        });

        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTaskList.size() < position+1){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.delete_fail)+position, Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyItemRemoved(position);
                notifyDataSetChanged();
                mTaskList.remove(position);
                notifyItemRemoved(position);
                mTaskTableDao.deleteTask(task);
                Toast.makeText(mContext, mContext.getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public void replaceTask(Task task){
        for(Task t: mTaskList){
            if(t.getStartTime().equals(task.getStartTime())){
                t.setEndTime(task.getEndTime());
                t.setContent(task.getContent());
                t.setPriority(task.getPriority());
                t.setIs_finish(task.getIs_finish());
                break;
            }
        }

        notifyDataSetChanged();
    }


    public void updateTask(String date, Task task){
        for(Task t: mTaskList){
            if(t.getStartTime().equals(date)){
                t.setEndTime(task.getEndTime());
                t.setContent(task.getContent());
                t.setPriority(task.getPriority());
                t.setIs_finish(task.getIs_finish());
                break;
            }
        }

        notifyDataSetChanged();
    }

    public void addTask(Task task){
        mTaskList.add(task);
        notifyItemInserted(mTaskList.size() - 1);
        notifyDataSetChanged();
    }

    public void removeAllTasks(){
        mTaskList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_date)
        TextView mTvDate;

        @BindView(R.id.tv_task)
        TextView mTvTask;

        @BindView(R.id.btn_start)
        Button mBtnStart;

        @BindView(R.id.btn_edit)
        Button mBtnEdit;

        @BindView(R.id.btn_delete)
        Button mBtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
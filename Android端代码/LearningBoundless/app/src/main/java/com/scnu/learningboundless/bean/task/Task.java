package com.scnu.learningboundless.bean.task;

public class Task {

    private String content;
    private String startTime;
    private String endTime;
    private int priority = 0;
    private int is_finish = 0;

    public Task() {
    }

    public Task(String content, String startTime, String endTime, int priority, int is_finish) {
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.is_finish = is_finish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    @Override
    public String toString() {
        return "Task{" +
                "content='" + content + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", priority=" + priority +
                ", is_finish=" + is_finish +
                '}';
    }
}
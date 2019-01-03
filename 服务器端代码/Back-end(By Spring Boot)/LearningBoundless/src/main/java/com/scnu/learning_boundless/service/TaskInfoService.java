package com.scnu.learning_boundless.service;

import com.scnu.learning_boundless.entity.TaskInfo;

import java.util.List;

public interface TaskInfoService {

    List<TaskInfo> listTaskInfos();
    List<TaskInfo> listTaskInfosByUsername(String username);
    TaskInfo addTaskInfo(TaskInfo taskInfo);
    List<TaskInfo> addTasks(List<TaskInfo> taskInfoList);

}

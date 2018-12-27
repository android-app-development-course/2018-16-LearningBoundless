package com.scnu.learning_boundless.service;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-11-37
 **/

import com.scnu.learning_boundless.entity.TaskInfo;
import com.scnu.learning_boundless.repository.TaskInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoRepository taskInfoRepository;

    @Override
    public List<TaskInfo> listTaskInfos() {
        return taskInfoRepository.findAll();
    }

    @Override
    public List<TaskInfo> listTaskInfosByUsername(String username) {
        return taskInfoRepository.findByUsername(username);
    }

    @Override
    public TaskInfo addTaskInfo(TaskInfo taskInfo) {
        return taskInfoRepository.save(taskInfo);
    }

    public List<TaskInfo> addTasks(List<TaskInfo> taskInfoList){
        return taskInfoRepository.saveAll(taskInfoList);
    }
}

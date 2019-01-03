package com.scnu.learning_boundless.controller;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-11-51
 **/

import com.scnu.learning_boundless.entity.Result;
import com.scnu.learning_boundless.entity.TaskInfo;
import com.scnu.learning_boundless.entity.UserInfo;
import com.scnu.learning_boundless.service.TaskInfoServiceImpl;
import com.scnu.learning_boundless.service.UserInfoServiceImpl;
import com.scnu.learning_boundless.util.CreateResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private TaskInfoServiceImpl taskInfoService;


    /**
     * 获取用户个人信息
     * @param username 用户名
     * @return
     */
    @GetMapping(value = "/user/{username}")
    public UserInfo getUserInfoByUsername(@PathVariable("username") String username){
       return userInfoService.getUserInfoByUsername(username);
    }


    /**
     * 获取某一用户的任务集
     * @param username
     * @return
     */
    @GetMapping(value = "/task/{username}")
    public List<TaskInfo> getTasksOfUser(@PathVariable("username") String username){
        return taskInfoService.listTaskInfosByUsername(username);
    }


    /**
     * 获取数据库中所有任务
     * @return
     */
    @GetMapping(value = "/task/all")
    public List<TaskInfo> getAllTasks(){
        return taskInfoService.listTaskInfos();
    }


    /**
     * 添加用户信息（实现了表单验证）
     * @param userInfo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/user/add")
    public Result<UserInfo> addUserInfo(@Valid UserInfo userInfo, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return CreateResultUtil.createErrorResult(1,bindingResult.getFieldError().getDefaultMessage() );
        }

        return CreateResultUtil.createSuccessResult(userInfoService.addUserInfo(userInfo));
    }


    /**
     * 更新用户信息（实现了表单验证）
     * @param userInfo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/user/update")
    public Result<UserInfo> updateUserInfo(@Valid UserInfo userInfo, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return CreateResultUtil.createErrorResult(3,bindingResult.getFieldError().getDefaultMessage() );
        }

        return CreateResultUtil.createSuccessResult(userInfoService.updateUserInfo(userInfo));
    }


    /**
     * 添加任务集
     * @param taskInfoList
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/task/add")
    public Result<List<TaskInfo>> addTasks(@Valid @RequestBody List<TaskInfo> taskInfoList, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return CreateResultUtil.createErrorResult(2, bindingResult.getFieldError().getDefaultMessage());
        }

        return CreateResultUtil.createSuccessResult(taskInfoService.addTasks(taskInfoList));
    }
}

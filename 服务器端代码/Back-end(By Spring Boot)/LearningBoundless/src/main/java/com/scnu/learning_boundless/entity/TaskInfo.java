package com.scnu.learning_boundless.entity;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-26-22-57
 **/

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 任务信息实体类
 */
@Table(name = "t_task")
@Entity
public class TaskInfo {

    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 任务开始时间
     */
    @NotBlank(message = "任务开始时间不能为空")
    private String startTime;


    /**
     * 任务结束时间
     */
    @NotBlank(message = "任务结束时间不能为空")
    private String endTime;


    /**
     * 任务优先级
     */
    private Integer priority;


    /**
     * 任务内容
     */
    @NotBlank(message = "任务内容不能为空")
    private String content;


    /**
     * 任务所属用户的用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

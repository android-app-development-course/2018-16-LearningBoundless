# 2018-16-LearningBoundless
[![state](https://img.shields.io/badge/state-in%20development%20-brightgreen.svg)](https://github.com/android-app-development-course/2018-16-LearningBoundless)
[![license](https://img.shields.io/packagist/l/doctrine/orm.svg)](https://github.com/android-app-development-course/2018-16-LearningBoundless/blob/master/LICENSE)


<br/>



## *Group members*


> [@WuchangI](https://github.com/Yuziquan), [@陈铭海](https://github.com/chenminghai), [@SeanLiaoy](https://github.com/SeanLiaoy), [@ticktockKK](https://github.com/ticktockKK)

<br/>

***


## 1. App Name

> **学迹无涯(LearningBoundless)**

<br/>

***

## 2. App Intro

> 一款基于学习轨迹的好友（社交）监督App。

<br/>

***

## 3. Project Specification

*为了便于后期的维护和迭代，对代码进行解耦和减少代码冗余，对常用的类进行了封装，后期的开发将基于这些封装类。*



> * 项目中的所有的活动类（Activity）均要继承自**BaseActivity**，所有的碎片类（Fragment）均要继承自**BaseFragment**。如需在特定Activity或Fragment子类中实现特定逻辑，可以 Override 基类的 getContentLayoutId()、initWidget() 、initData()等方法；
>
> * 获取控件实例尽量避免使用**findViewById**，借助**ButterKnife**，改用注解方式 **@BindView** 进行依赖注入获取；
>
> * 项目中但凡使用到网络部分，即向后台发送请求以获取响应，均需借助网络封装类**SendMessageManager**的**getXXX**接口，响应内容可以随时随地在任何Activity和Fragment通过注册**EventBus**实例来订阅获取，从而渲染界面；
>
> * 所有网络响应数据实体类均以**xxxResponseInfo**命名；
>
> * 项目中的变量命名、方法命名、控件命名、xml布局文件命名等规范参照：
>
>   [Android开发规范](https://github.com/Blankj/AndroidStandardDevelop)
>
>   （参照即可，不必严格照搬）
>
> * 项目中使用到的所有常量均放置到**Constants**常量类中；
>
> * 项目中使用到的所有字符串均放置到**strings.xml**文件中，java文件中使用字符串参照：
>
>   > 在Activity中：String str = getResources().getString(R.string.str_name);
>   >
>   > 在Fragment中：String str = getActivity().getResources().getString(R.string.str_name);
>   >
>   > 通用：String str = MyApplication.getGlobalApplication().getResources().getString(R.string.str_name);​
>



<br/>

***


## 4. Promotion Channel
![promotion](/Screenshots/Project/promotion.jpg)

<br/>

***

## 5. RESTful API

### 5.1 获取用户个人信息 

* 请求方式：get

* url：http://119.23.227.96:8080/LB/user/{username}

* 请求参数：username

* 响应格式示例：

  ```json
  {
      "username": "wuchangi",
      "password": "132152315",
      "avatar": "wqeqwgrewhtrehrtejrej",
      "level": 2
  }
  ```
  <br/>


### 5.2 获取某一用户的任务集

* 请求方式：get

* url：http://119.23.227.96:8080/LB/task/{username}

* 请求参数：username

* 响应格式示例：

  ```json
  [{
  	"id": 1,
  	"startTime": "2018/12/26 18:00",
  	"endTime": "2018/12/26 21:00",
  	"priority": 1,
  	"content": "刷完算法导论第四章第二节",
  	"username": "chenminghai"
  }, {
  	"id": 2,
  	"startTime": "2018/12/26 21:10",
  	"endTime": "2018/12/26 22:10",
  	"priority": 2,
  	"content": "写移动开发实验报告",
  	"username": "chenminghai"
  }, {
  	"id": 3,
  	"startTime": "2018/12/26 22:18",
  	"endTime": "2018/12/26 23:20",
  	"priority": 3,
  	"content": "复习程序设计艺术PPT",
  	"username": "chenminghai"
  }]
  ```



<br/>


### 5.3 获取数据库中所有任务

* 请求方式：get

* url：http://119.23.227.96:8080/LB/task/all

* 响应格式示例：

  ```json
  [{
  	"id": 1,
  	"startTime": "2018/12/26 18:00",
  	"endTime": "2018/12/26 21:00",
  	"priority": 1,
  	"content": "刷完算法导论第四章第二节",
  	"username": "chenminghai"
  }, {
  	"id": 2,
  	"startTime": "2018/12/26 21:10",
  	"endTime": "2018/12/26 22:10",
  	"priority": 2,
  	"content": "写移动开发实验报告",
  	"username": "chenminghai"
  }, {
  	"id": 3,
  	"startTime": "2018/12/26 22:18",
  	"endTime": "2018/12/26 23:20",
  	"priority": 3,
  	"content": "复习程序设计艺术PPT",
  	"username": "chenminghai"
  }, {
  	"id": 4,
  	"startTime": "2018/12/27 17:30",
  	"endTime": "2018/12/27 18:00",
  	"priority": 1,
  	"content": "去西田跑步",
  	"username": "wuchangi"
  }, {
  	"id": 5,
  	"startTime": "2018/12/27 19:30",
  	"endTime": "2018/12/27 20:30",
  	"priority": 2,
  	"content": "整理复习资料",
  	"username": "wuchangi"
  }, {
  	"id": 6,
  	"startTime": "2018/12/26 17:30",
  	"endTime": "2018/12/26 21:30",
  	"priority": 1,
  	"content": "移动开发实验作业",
  	"username": "ticktock"
  }, {
  	"id": 7,
  	"startTime": "2018/12/26 20:00",
  	"endTime": "2018/12/26 22:00",
  	"priority": 2,
  	"content": "科技文献PPT准备",
  	"username": "SeanLiaoy"
  }]
  ```



<br/>

### 5.4 添加用户个人信息 

* 请求方式：post

* url：http://119.23.227.96:8080/LB/user/add

* 请求体格式示例：

  ```json
  {
       "username": "newone",
       "password": "223634",
       "avatar": "ghuweghueiwrhgurhghghhghghhg",
       "level": 1
  }
  ```

* 响应格式示例：

  ```json
  {
      "code": 0,
      "msg": "成功！",
      "data": {
          "username": "newone",
          "password": "223634",
          "avatar": "ghuweghueiwrhgurhghghhghghhg",
          "level": 1
      }
  }
  ```

<br/>

### 5.5 更新用户信息

* url：http://119.23.227.96:8080/LB/user/update


* 请求方式、请求体格式、响应格式同**5.4**

<br/>



### 5.6 添加任务集

* 请求方式：post


* url：http://119.23.227.96:8080/LB/task/add

* 请求体格式示例：

  ```json
  [{
  	"id": 1,
  	"startTime": "2018/12/26 18:00",
  	"endTime": "2018/12/26 21:00",
  	"priority": 1,
  	"content": "刷完算法导论第四章第二节",
  	"username": "chenminghai"
  }, {
  	"id": 2,
  	"startTime": "2018/12/26 21:10",
  	"endTime": "2018/12/26 22:10",
  	"priority": 2,
  	"content": "写移动开发实验报告",
  	"username": "chenminghai"
  }, {
  	"id": 3,
  	"startTime": "2018/12/26 22:18",
  	"endTime": "2018/12/26 23:20",
  	"priority": 3,
  	"content": "复习程序设计艺术PPT",
  	"username": "chenminghai"
  }]
  ```

* 响应格式示例：

  ```json
  {
      "code": 0,
      "msg": "成功！",
      "data": [
          {
              "id": 1,
              "startTime": "2018/12/26 18:00",
              "endTime": "2018/12/26 21:00",
              "priority": 1,
              "content": "刷完算法导论第四章第二节",
              "username": "chenminghai"
          },
          {
              "id": 2,
              "startTime": "2018/12/26 21:10",
              "endTime": "2018/12/26 22:10",
              "priority": 2,
              "content": "写移动开发实验报告",
              "username": "chenminghai"
          },
          {
              "id": 3,
              "startTime": "2018/12/26 22:18",
              "endTime": "2018/12/26 23:20",
              "priority": 3,
              "content": "复习程序设计艺术PPT",
              "username": "chenminghai"
          }
      ]
  }
  ```

  ​

<br/>

***


## 6. Tasks
### 6.1 Task1

![task1](/Screenshots/Tasks/task1.png)



<br/>

![task1_1](/Screenshots/Tasks/task1_1.png)


<br/>

### 6.2 Task2



![task2](/Screenshots/Tasks/task2.png)

<br/>

![task2_1](/Screenshots/Tasks/task2_1.png)

<br/>



### 6.3 Task3

![task3](/Screenshots/Tasks/task3.png)

<br/>

![task3_1](/Screenshots/Tasks/task3_1.png)






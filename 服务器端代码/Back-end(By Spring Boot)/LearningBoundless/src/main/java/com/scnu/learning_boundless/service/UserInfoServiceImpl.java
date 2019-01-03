package com.scnu.learning_boundless.service;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-11-38
 **/

import com.scnu.learning_boundless.entity.UserInfo;
import com.scnu.learning_boundless.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {

        UserInfo user = new UserInfo();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setAvatar(userInfo.getAvatar());
        user.setLevel(userInfo.getLevel());

        return userInfoRepository.save(user);
    }


    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {

        UserInfo user = new UserInfo();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setAvatar(userInfo.getAvatar());
        user.setLevel(userInfo.getLevel());

        return userInfoRepository.save(user);
    }

}

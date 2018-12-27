package com.scnu.learning_boundless.service;

import com.scnu.learning_boundless.entity.UserInfo;

public interface UserInfoService {

    UserInfo getUserInfoByUsername(String username);
    UserInfo addUserInfo(UserInfo userInfo);
    UserInfo updateUserInfo(UserInfo userInfo);
}

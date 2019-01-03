package com.scnu.learning_boundless.repository;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-11-05
 **/

import com.scnu.learning_boundless.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

    UserInfo findByUsername(String username);
}

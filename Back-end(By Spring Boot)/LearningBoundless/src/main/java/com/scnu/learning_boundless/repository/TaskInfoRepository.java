package com.scnu.learning_boundless.repository;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-11-05
 **/


import com.scnu.learning_boundless.entity.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskInfoRepository extends JpaRepository<TaskInfo, Long> {

    List<TaskInfo> findByUsername(String username);

}

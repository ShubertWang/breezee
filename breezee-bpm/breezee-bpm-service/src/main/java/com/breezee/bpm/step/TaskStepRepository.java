/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.step;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Silence on 2016/2/14.
 */
@Repository
public interface TaskStepRepository extends PagingAndSortingRepository<TaskStepEntity,Long> {

    List<TaskStepEntity> findByProcInstIdAndWorkItemId(String procsId,String workItemId);
}

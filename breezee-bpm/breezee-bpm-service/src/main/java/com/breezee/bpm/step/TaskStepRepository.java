/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.step;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Silence on 2016/2/14.
 */
public interface TaskStepRepository extends PagingAndSortingRepository<TaskStepEntity,Long> {
}

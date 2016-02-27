package com.breezee.prj.sodexo.repository;

import com.breezee.prj.sodexo.entity.ModelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * fafa
 * Created by Zhong, An-Jing on 2015/12/20.
 */
@Repository("modelRepository")
public interface ModelRepository extends CrudRepository<ModelEntity, Long> {

    List<ModelEntity> findByParent(ModelEntity category);

    @Query("select c from ModelEntity c where c.parent is null")
    List<ModelEntity> findTop();

    ModelEntity findByCode(String code);
}

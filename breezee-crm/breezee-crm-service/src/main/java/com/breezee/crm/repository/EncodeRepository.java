package com.breezee.crm.repository;

import com.breezee.crm.entity.EncodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Silence on 2016/2/28.
 */
@Repository("encodeRepository")
public interface EncodeRepository extends CrudRepository<EncodeEntity,Long> {

    List<EncodeEntity> findBySite(String site);

    EncodeEntity findBySiteAndCode(String site,String code);
}

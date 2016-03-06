package com.breezee.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.sodexo.api.domain.CommentInfo;
import com.breezee.sodexo.api.service.ICommentService;
import com.breezee.sodexo.entity.CommentEntity;
import com.breezee.sodexo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/3/3.
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentInfo saveInfo(CommentInfo info) {
        //evaluate, read, order
        String time = formatter.format(new Date());
        info.setCommentTime(time);
        info.setCode(info.getUserId() + info.getOperType() + info.getObjectId() + time);
        CommentEntity entity = commentRepository.findByCode(info.getCode());
        System.out.println(entity + "-------------");
        if (entity != null) {
            return ErrorInfo.build(info, ContextUtil.getMessage("comment.once.error"));
        } else {
            entity = new CommentEntity();
        }
        commentRepository.save(entity.parse(info));
        return SuccessInfo.build(CommentInfo.class);
    }

    @Override
    public CommentInfo deleteById(Long id) {
        commentRepository.delete(id);
        return null;
    }

    @Override
    public CommentInfo findInfoById(Long id) {
        CommentEntity entity = commentRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(CommentInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<CommentInfo> listAll(Map<String, Object> m) {
        List<CommentEntity> l = commentRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<CommentEntity, CommentInfo>) (commentEntity, info) -> commentEntity.toInfo());
    }

    @Override
    public PageResult<CommentInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
        Page<CommentEntity> page = commentRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, CommentInfo.class, (commentEntity, info) -> commentEntity.toInfo());
    }

    @Override
    public long countObject(String objectId, Integer value) {
        return commentRepository.countObject(objectId, value);
    }
}

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
        if (info.getObjectType().equals("evaluate")) {
            CommentEntity entity = commentRepository.findByUserIdAndObjectIdAndCommentTime(info.getUserId(), info.getObjectId(), formatter.format(new Date()));
            if (entity != null) {
                return ErrorInfo.build(info, ContextUtil.getMessage("comment.once.error"));
            }
        }
        commentRepository.save(new CommentEntity().parse(info));
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
}

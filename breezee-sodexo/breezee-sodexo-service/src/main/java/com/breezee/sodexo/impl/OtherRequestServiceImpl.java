package com.breezee.sodexo.impl;

import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.sodexo.api.domain.OtherRequestInfo;
import com.breezee.sodexo.api.service.IOtherRequestService;
import com.breezee.sodexo.entity.OtherRequestEntity;
import com.breezee.sodexo.repository.OtherRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/3/5.
 */
@Service("otherRequestService")
public class OtherRequestServiceImpl implements IOtherRequestService {

    @Autowired
    private Environment environment;

    @Autowired
    private OtherRequestRepository otherRequestRepository;

    @Resource
    private IWorkflowService workflowServiceImpl;

    @Override
    public OtherRequestInfo saveInfo(OtherRequestInfo orderInfo) {
        if (orderInfo.getId() == null)
            orderInfo.setCode(String.format("%05d", orderInfo.getUserId()) + Long.valueOf(System.currentTimeMillis()-1457000000000L).toString());
        OtherRequestEntity entity = new OtherRequestEntity().parse(orderInfo);
        entity.setIssueDate(new Date());
        otherRequestRepository.save(entity);
        //启动流程
        if (orderInfo.getTaskId() == null || orderInfo.getTaskId() < 0) {
            Map<String, Object> vars = new HashMap<>();
            //注意第一次保存启动流程orderInfo的ProcDefId和code一定要有值
            vars.put("requestServiceRole", environment.getProperty("request.service.line","requestServiceLine"));
            vars.put("startUser", entity.getUserId());
            vars.put("orderId", entity.getId());
            workflowServiceImpl.startProcessInstanceById(orderInfo.getProcDefId(), entity.getId().toString(), vars);
        }
        orderInfo.setId(entity.getId());
        return SuccessInfo.build(orderInfo);
    }

    @Override
    public OtherRequestInfo deleteById(Long id) {
        return null;
    }

    @Override
    public OtherRequestInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<OtherRequestInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<OtherRequestInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }
}

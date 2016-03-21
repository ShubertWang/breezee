/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.crm.api.domain.ShippingAddressInfo;
import com.breezee.crm.api.domain.UserInfo;
import com.breezee.crm.api.service.IUserService;
import com.breezee.crm.entity.EncodeEntity;
import com.breezee.crm.entity.ShippingAddressEntity;
import com.breezee.crm.entity.UserEntity;
import com.breezee.crm.repository.EncodeRepository;
import com.breezee.crm.repository.ShippingAddressRepository;
import com.breezee.crm.repository.UserRepository;
import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.domain.OrganizationInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import com.breezee.sysmgr.api.service.IOrganizationService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/11.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private EncodeRepository encodeRepository;

    @Resource
    private IOrganizationService organizationService;

    @Resource
    private IAccountService accountService;

    @Override
    public Map<String, Object> saveShippingAddress(ShippingAddressInfo addressInfo) {
        UserEntity u = userRepository.findOne(addressInfo.getUserId());
        //TODO:throw user not found exception
        ShippingAddressEntity se = new ShippingAddressEntity().parse(addressInfo);
        se.setUser(u);
        shippingAddressRepository.save(se);

        //供前端使用
        Map<String, Object> m = new HashMap<>();
        m.put("callback", true);
        m.put("value", se.toInfo());
        return m;
    }

    @Override
    public UserInfo findByCode(String code) {
        UserEntity ue = userRepository.findByCode(code);
        if (ue == null)
            return ErrorInfo.build(UserInfo.class);
        UserInfo userInfo = ue.toInfo();
        if(StringUtils.hasText(ue.getAccountId())){
            AccountInfo info = new AccountInfo();
            info.setCode(ue.getAccountId());
            info.setPassword("rootroot");
            info = accountService.checkPassword(info);
            userInfo.setUserJob(info.getJob());
        }
        return userInfo;
    }

    @Override
    public List<ShippingAddressInfo> findShippingAddress(Long userId) {
        UserEntity u = userRepository.findOne(userId);
        if (u != null) {
            UserInfo ui = u.toInfo();
            return ui.getShippingAddressInfos();
        }
        return new ArrayList<>(0);
    }

    @Override
    public UserInfo employeeValidate(UserInfo info) {
        String key = Md5Crypt.md5Crypt((info.getDn() + info.getName() + info.getIdCard()).getBytes());
        EncodeEntity en = encodeRepository.findBySiteAndCode(info.getCompany(), key);
        if (en == null)
            return ErrorInfo.build(UserInfo.class);
        return SuccessInfo.build(info);
    }

    @Override
    public UserInfo registerSite(UserInfo info) {
        UserEntity entity = userRepository.findOne(info.getId());
        if (entity != null) {
            entity.setCompany(info.getCompany());
            entity.setEmail(info.getEmail());
            entity.setType("site");
            userRepository.save(entity);
            sendMail(entity);
            return SuccessInfo.build(info);
        }
        return ErrorInfo.build(UserInfo.class);
    }

    @Override
    public ShippingAddressInfo findShippingAddressById(Long id) {
        ShippingAddressEntity entity = shippingAddressRepository.findOne(id);
        if (entity != null)
            return entity.toInfo();
        return ErrorInfo.build(ShippingAddressInfo.class);
    }

    @Override
    public UserInfo saveInfo(UserInfo userInfo) {
        //检查网点编号
        if(StringUtils.hasText(userInfo.getCompany())) {
            OrganizationInfo org = organizationService.findByCode(userInfo.getCompany());
            if (org.getId() < 0)
                return ErrorInfo.build(userInfo, ContextUtil.getMessage("site.exist.error", new Object[]{userInfo.getCompany()}));
        }
        UserEntity entity = userRepository.findByCode(userInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if (userInfo.getId() == null && entity != null) {
            return ErrorInfo.build(userInfo, ContextUtil.getMessage("duplicate.key", new String[]{userInfo.getCode()}));
        }
        if(StringUtils.hasText(userInfo.getAccountId())){
            AccountInfo acct = accountService.findByCode(userInfo.getAccountId());
            if (acct.getId() < 0)
                return ErrorInfo.build(userInfo, ContextUtil.getMessage("account.exist.error", new Object[]{userInfo.getAccountId()}));
        }
        if (entity == null)
            entity = new UserEntity();
        entity.parse(userInfo);
        userRepository.save(entity.parse(userInfo));
        if(userInfo.getId()!=null && userInfo.getId()>0)
            sendMail(entity);
        return SuccessInfo.build(UserInfo.class);
    }

    @Override
    public UserInfo deleteById(Long id) {
        userRepository.delete(id);
        return SuccessInfo.build(UserInfo.class);
    }

    @Override
    public UserInfo findInfoById(Long id) {
        UserEntity ue = userRepository.findOne(id);
        if (ue == null)
            return ErrorInfo.build(UserInfo.class);
        return ue.toInfo();
    }

    @Override
    public List<UserInfo> listAll(Map<String, Object> m) {
        List<UserEntity> l = userRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<UserEntity, UserInfo>) (userEntity, userInfo) -> userEntity.toInfo());
    }

    @Override
    public PageResult<UserInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
        Page<UserEntity> page = userRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, UserInfo.class, (userEntity, userInfo) -> userEntity.toInfo());
    }

    @Override
    public void updateStatus(Long id, int status) {
        UserEntity entity = userRepository.findOne(id);
        if (entity != null) {
            entity.setStatus(status);
            if(status==3)
                entity.setType("employee");
            userRepository.save(entity);
        }
    }

    /**
     * 发送邮件
     * @param entity
     * @throws MessagingException
     */
    private void sendMail(UserEntity entity) {
        if(entity.getEmail()==null)
            return;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(this.templateMessage.getFrom());
            messageHelper.setSubject(this.templateMessage.getSubject());
            messageHelper.setTo(entity.getEmail());
            messageHelper.setText("Dear " + entity.getName()+
                    "<a href='http://weixin.sodexo-cn.com/portal/verify?id=" + entity.getId() + "&status=3'>点此链接</a>，验证您的员工身份！",true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

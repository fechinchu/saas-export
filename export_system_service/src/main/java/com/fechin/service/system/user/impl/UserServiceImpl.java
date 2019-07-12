package com.fechin.service.system.user.impl;

import com.fechin.dao.system.dept.DeptDao;
import com.fechin.dao.system.user.UserDao;
import com.fechin.domain.system.role.Role;
import com.fechin.domain.system.user.User;
import com.fechin.service.system.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DeptDao deptDao;
    @Autowired
    private Destination queue;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public PageInfo<User> findByPage(String companyId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.findAll(companyId);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public User findById(String companyId, String id) {
        return userDao.findById(companyId, id);
    }

    @Override
    public void update(User user) {
        Date date = new Date();

        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:hh:ss");
        String s = simpleDateFormat.format(date);*/
        String email = user.getEmail();
        String password = user.getPassword();
        String md5 = new Md5Hash(password, email, 2).toString();
        user.setPassword(md5);
        user.setUpdateTime(date);
        userDao.update(user);
    }

    @Override
    public void add(User user) {
        String s = user.getPassword();
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));

        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        //算法加密
        String email = user.getEmail();
        String password = user.getPassword();
        String md5 = new Md5Hash(password, email, 2).toString();
        user.setPassword(md5);
        /*String deptId = deptDao.findByName(deptName);
        user.setDeptId(deptId);*/
        userDao.add(user);
        //在添加user的同时使用activeMQ,给员工发送邮件
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setStringProperty("to", user.getEmail());
                mapMessage.setStringProperty("subject", "欢迎加入国庆的沙县大酒店");
                mapMessage.setStringProperty("content", user.getUserName() + "你好,欢迎加入国庆的沙县大酒店," +
                        "愿您跟吃了屎一样难受,您的密码是" + s);
                return mapMessage;
            }
        });

    }

    @Override
    public void delete(String id, String companyId) {
        userDao.delete(id, companyId);
    }

    @Override
    public List<Role> findWithRole(String userId) {
        return userDao.findWithRole(userId);
    }

    @Override
    public void changeRole(String userId, String oldRoleds, String[] roleIds) {
        //删除中间表的包含有userId的用户的所有角色信息
        userDao.deleteUserRole(userId);
        for (String roleId : roleIds) {

            userDao.addUserRole(userId, roleId);
        }
    }

    @Override
    public User login(User user) {
        User user1 = userDao.findUserByEmail(user.getEmail());
        //System.out.println(user1);
        return user1;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll("1");
    }

}

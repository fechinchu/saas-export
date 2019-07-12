package com.fechin.service.system.user;

import com.fechin.domain.system.role.Role;
import com.fechin.domain.system.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    PageInfo<User> findByPage(String companyId, Integer page, Integer size);

    User findById(String companyId, String id);

    void update(User user);

    void add(User user);

    void delete(String id, String companyId);

    List<Role> findWithRole(String userId);

    void changeRole(String userId, String oldRoleds, String[] roleIds);

    User login(User user);

    User findByEmail(String email);

    List<User> findAll();

}

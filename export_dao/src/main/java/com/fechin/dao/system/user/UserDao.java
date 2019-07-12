package com.fechin.dao.system.user;

import com.fechin.domain.system.role.Role;
import com.fechin.domain.system.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    List<User> findAll(String companyId);

    User findById(@Param("companyId") String companyId,@Param("userId") String id);

    void update(User user);

    void add(User user);

    void delete(@Param("userId") String id, @Param("companyId") String companyId);

    List<Role> findWithRole(@Param("userId") String userId);

    void deleteUserRole(String userId);

    void addUserRole(@Param("userId") String userId, @Param("roleId")String roleId);

    User findUserByEmail(String email);
}

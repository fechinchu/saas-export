package com.fechin.dao.system.role;

import com.fechin.domain.module.Module;
import com.fechin.domain.system.role.Role;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RoleDao {
    List<Role> findAll(String companyId);

    Role findByOne(@Param("companyId") String companyId, @Param("id") String id);

    void add(Role role);

    void update(Role role);

    void delete(String id);

    List<BigDecimal> findAllOrderNo(String companyId);

    List<Role> findRoleWithUser(@Param("userId") String userId,@Param("companyId") String companyId);

    List<Module> findModluesByRole(String roleid);

    void deleteOnRoleModule(String roleid);

    void addOnRoleModule(@Param("roleId") String roleid, @Param("moduleId") String string);
}

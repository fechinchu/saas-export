package com.fechin.service.system.role;

import com.fechin.domain.module.Module;
import com.fechin.domain.system.role.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    PageInfo<Role> findAll(String companyId, Integer page, Integer size);

    Role findByOne(String companyId, String id);

    List<Role> findAllWithOutPage(String companyId);

    void add(Role role);

    void update(Role role);

    void delete(String id);

    String findRoleWithUser(String id, String companyId);

    List findModuleByRoleForAjax(String roleid, String companyId);

    void updateRoleModule(String roleid, String moduleIds);

    List<Module> findModuleByRole(String id);
}

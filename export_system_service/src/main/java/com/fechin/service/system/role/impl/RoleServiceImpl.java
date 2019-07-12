package com.fechin.service.system.role.impl;

import com.fechin.dao.module.ModuleDao;
import com.fechin.dao.system.role.RoleDao;
import com.fechin.domain.module.Module;
import com.fechin.domain.system.role.Role;
import com.fechin.service.system.role.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo<Role> findAll(String companyId, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Role> list = roleDao.findAll(companyId);
        PageInfo<Role> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public Role findByOne(String companyId, String id) {
        return roleDao.findByOne(companyId,id);
    }

    @Override
    public List<Role> findAllWithOutPage(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void add(Role role) {
        role.setId(UUID.randomUUID().toString().replace("-",""));
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleDao.add(role);
    }

    @Override
    public void update(Role role) {
        role.setUpdateTime(new Date());
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public String findRoleWithUser(String id, String companyId) {
        List<Role> roleListWithUser = roleDao.findRoleWithUser(id, companyId);
        //如果数据库中没有数据,name返回null;
        if(roleListWithUser.size()==0){
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Role role : roleListWithUser) {
            String roleId = role.getId();
            builder.append(roleId+",");
        }
        String string = builder.toString();
        return string.substring(0, string.length() - 1);
    }

    @Override
    public LinkedList findModuleByRoleForAjax(String roleid, String companyId) {
        List<Module> modulesByRole = roleDao.findModluesByRole(roleid);
        List<Module> modules = moduleDao.findAll();
        LinkedList<Map<String,Object>> list = new LinkedList<>();
        for (Module module : modules) {
            HashMap<String, Object> map = new HashMap<>();
            // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
            map.put("id",module.getId());
            map.put("pId",module.getParentId());
            map.put("name",module.getName());
            if(modulesByRole.contains(module)){
                map.put("checked",true);
            }
            list.add(map);
        }
        /*//TEST
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }*/
        return list;
    }

    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        //根据roleid去数据库中删除该roleid的数据
        roleDao.deleteOnRoleModule(roleid);
        String[] strings = moduleIds.split(",");
        for (String string : strings) {
            roleDao.addOnRoleModule(roleid,string);
        }
    }

    @Override
    public List<Module> findModuleByRole(String id) {
        return  roleDao.findModluesByRole(id);
    }

}

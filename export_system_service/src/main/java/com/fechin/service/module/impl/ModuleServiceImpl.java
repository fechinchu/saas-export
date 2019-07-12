package com.fechin.service.module.impl;

import com.fechin.dao.module.ModuleDao;
import com.fechin.domain.module.Module;
import com.fechin.domain.system.user.User;
import com.fechin.service.module.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo<Module> findByPage(int page, int size) {
        PageHelper.startPage(page,size);
        List<Module> list = moduleDao.findAll();
        PageInfo<Module> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Module findByOne(String id) {
        return moduleDao.findByOne(id);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public void add(Module module) {
        module.setId(UUID.randomUUID().toString().replace("-",""));
        moduleDao.add(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    //根据用户信息确定能够使用的模块
    @Override
    public List<Module> findModuleByUid(User user1) {
        Integer degree = user1.getDegree();
        //当degree为0的时候,表示是系统管理员
        if(degree==0){
            //系统管理员的话可以操作Saas管理,企业管理,和模块管理
            return moduleDao.findByBelong(0);
        }else if(degree==1){
            //表明是企业管理员,可以操作企业的所有内容
            return moduleDao.findByBelong(1);
        }else{
            return moduleDao.findModuleByUid(user1.getUserId());
        }
    }
}

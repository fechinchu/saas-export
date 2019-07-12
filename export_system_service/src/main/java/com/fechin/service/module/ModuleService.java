package com.fechin.service.module;

import com.fechin.domain.module.Module;
import com.fechin.domain.system.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    PageInfo<Module> findByPage(int page, int size);

    Module findByOne(String id);

    void delete(String id);

    void add(Module module);

    void update(Module module);

    List<Module> findAll();

    List<Module> findModuleByUid(User user1);
}

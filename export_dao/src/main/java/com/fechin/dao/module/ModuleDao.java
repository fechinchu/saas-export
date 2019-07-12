package com.fechin.dao.module;

import com.fechin.domain.module.Module;

import java.util.List;

public interface ModuleDao {

    List<Module> findAll();

    Module findByOne(String id);

    void delete(String id);

    void add(Module module);

    void update(Module module);

    List<Module> findByBelong(int i);

    List<Module> findModuleByUid(String userId);
}

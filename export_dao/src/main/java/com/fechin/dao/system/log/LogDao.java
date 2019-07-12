package com.fechin.dao.system.log;

import com.fechin.domain.system.log.Log;

import java.util.List;

public interface LogDao {

    List<Log> findAll(String companyId);

    void save(Log log);
}

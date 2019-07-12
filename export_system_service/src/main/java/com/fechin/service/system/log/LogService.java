package com.fechin.service.system.log;

import com.fechin.domain.system.log.Log;
import com.github.pagehelper.PageInfo;

public interface LogService {

    PageInfo<Log> findByPage(Integer page, Integer size, String companyId);

    void save(Log log);
}

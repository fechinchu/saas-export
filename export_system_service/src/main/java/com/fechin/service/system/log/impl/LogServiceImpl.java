package com.fechin.service.system.log.impl;

import com.fechin.dao.system.log.LogDao;
import com.fechin.domain.system.log.Log;
import com.fechin.service.system.log.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public PageInfo<Log> findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page,size);
        List<Log> logs = logDao.findAll(companyId);
        PageInfo<Log> logPageInfo = new PageInfo<>(logs);
        return logPageInfo;

    }

    @Override
    public void save(Log log) {
        logDao.save(log);
    }
}

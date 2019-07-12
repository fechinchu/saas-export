package com.fechin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.system.feedback.FeedbackDao;
import com.fechin.domain.system.feedback.Feedback;
import com.fechin.domain.system.feedback.FeedbackExample;
import com.fechin.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public PageInfo<Feedback> findByPage(FeedbackExample feedbackExample, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Feedback> feedbacks = feedbackDao.selectByExample(feedbackExample);
        return new PageInfo<Feedback>(feedbacks);
    }

    @Override
    public void save(Feedback feedback) {
        feedbackDao.insertSelective(feedback);
    }

    @Override
    public Feedback findById(String id) {
        return feedbackDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Feedback feedback) {
        feedbackDao.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public void delete(String id) {
        feedbackDao.deleteByPrimaryKey(id);
    }
}

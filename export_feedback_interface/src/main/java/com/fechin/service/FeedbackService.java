package com.fechin.service;

import com.fechin.domain.system.feedback.Feedback;
import com.fechin.domain.system.feedback.FeedbackExample;
import com.github.pagehelper.PageInfo;

public interface FeedbackService {

    PageInfo<Feedback> findByPage(FeedbackExample feedbackExample, Integer page, Integer size);

    void save(Feedback feedback);

    Feedback findById(String id);

    void update(Feedback feedback);

    void delete(String id);
}

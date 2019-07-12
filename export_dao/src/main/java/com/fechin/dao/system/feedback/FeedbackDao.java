package com.fechin.dao.system.feedback;

import com.fechin.domain.system.feedback.Feedback;
import com.fechin.domain.system.feedback.FeedbackExample;

import java.util.List;

public interface FeedbackDao {
    int deleteByPrimaryKey(String feedbackId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    List<Feedback> selectByExample(FeedbackExample example);

    Feedback selectByPrimaryKey(String feedbackId);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}
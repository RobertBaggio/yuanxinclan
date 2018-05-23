package com.yuanxin.clan.core.mineclass.entity;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/13 0013 16:24
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class QuestionEntity implements Serializable{

    private String questionId;
    private String questionTitle;
    private String questionContent;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}

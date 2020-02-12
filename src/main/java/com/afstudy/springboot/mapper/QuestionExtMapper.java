package com.afstudy.springboot.mapper;

import com.afstudy.springboot.model.Question;
import com.afstudy.springboot.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
}
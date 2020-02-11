package com.afstudy.springboot.service;

import com.afstudy.springboot.dto.PaginationDTO;
import com.afstudy.springboot.dto.QuestionDTO;
import com.afstudy.springboot.mapper.QuestionMapper;
import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.Question;
import com.afstudy.springboot.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size + 1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset=size*(page-1);
        List<Question>questions=questionMapper.list(offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        //偏移量
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount % size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size + 1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset=size*(page-1);
        if(offset<=0){
            offset=5;
        }
        List<Question>questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}

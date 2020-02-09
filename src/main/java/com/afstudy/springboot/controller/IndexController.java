package com.afstudy.springboot.controller;

import com.afstudy.springboot.dto.QuestionDTO;
import com.afstudy.springboot.mapper.QuestionMapper;
import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.Question;
import com.afstudy.springboot.model.User;
import com.afstudy.springboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO>questionList=questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }


}

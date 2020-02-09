package com.afstudy.springboot.controller;

import com.afstudy.springboot.dto.AccessTokenDTO;
import com.afstudy.springboot.dto.GithubUser;
import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.User;
import com.afstudy.springboot.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    //配置文件的键值对映射关系，使其能够读取到
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    
    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokonDTO = new AccessTokenDTO();
        accessTokonDTO.setClient_id(clientId);
        accessTokonDTO.setClient_secret(clientSecret);
        accessTokonDTO.setCode(code);
        accessTokonDTO.setRedirect_uri(redirectUri);
        accessTokonDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokonDTO);
        GithubUser githubUser = githubProvider.githubUser(accessToken);
        if(githubUser !=null){
            //登录成功
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatar_url(githubUser.getAvatar_url());
            userMapper.insert(user);
            //写入session cookie
            response.addCookie(new Cookie("token",token));

            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }
}

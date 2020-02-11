package com.afstudy.springboot.controller;

import com.afstudy.springboot.dto.AccessTokenDTO;
import com.afstudy.springboot.dto.GithubUserDTO;
import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.User;
import com.afstudy.springboot.provider.GithubProvider;
import com.afstudy.springboot.service.UserService;
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
    private UserService userService;
    
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
        GithubUserDTO githubUserDTO = githubProvider.githubUser(accessToken);
        if(githubUserDTO !=null){
            //登录成功
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccount_id(String.valueOf(githubUserDTO.getId()));

            user.setAvatar_url(githubUserDTO.getAvatar_url());
            userService.createOrUpdate(user);
            //写入session cookie
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user", githubUserDTO);
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}

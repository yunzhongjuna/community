package com.afstudy.springboot.controller;

import com.afstudy.springboot.dto.AccessTokenDTO;
import com.afstudy.springboot.dto.GithubUser;
import com.afstudy.springboot.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccessTokenDTO accessTokonDTO = new AccessTokenDTO();
        accessTokonDTO.setClient_id(clientId);
        accessTokonDTO.setClient_secret(clientSecret);
        accessTokonDTO.setCode(code);
        accessTokonDTO.setRedirect_uri(redirectUri);
        accessTokonDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokonDTO);
        GithubUser user = githubProvider.githubUser(accessToken);
        System.out.println(user.getName()+222);
        return "index";
    }
}

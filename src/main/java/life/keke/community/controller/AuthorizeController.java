package life.keke.community.controller;


import life.keke.community.dto.AccessTokenDTO;
import life.keke.community.dto.GithubUser;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.User;
import life.keke.community.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("$(github.client.id)")
    private  String clientId;

    @Value("$(github.client.secret)")
    private String clientSecret;

    @Value("$(github.redirect.uri)")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request

    ) throws IOException {
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri("localhost:8011/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if(githubUser !=null){

            User user=new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.saveUser(user);
            //登录成功，返回cookie和session
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        } else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }



}

package life.keke.community.provider;


import com.alibaba.fastjson.JSON;
import life.keke.community.dto.AccessTokenDTO;
import life.keke.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token?client_id=d9578dd5d74eea4e62c3&client_secret=044c149a49cbf294788523c55a1b9633bcaea7f1&code="+accessTokenDTO.getCode()+"&redirect_uri=http://localhost:8011/callback&state=1")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                System.out.println(string);
                return string;
            } catch (IOException e) {
            }
            return null;
    }


    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        GithubUser githubUser=JSON.parseObject(string, GithubUser.class);
        return githubUser;


    }
}
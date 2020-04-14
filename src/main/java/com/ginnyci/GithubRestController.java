package com.ginnyci;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class GithubRestController {

    @Value("#{environment.CLIENT_ID}")
    private String clientId;
    @Value("#{environment.CLIENT_SECRET}")
    private String clientSecret;

    @CrossOrigin(origins = {"http://localhost:4200", "https://ginny-ci.com"})
    @PostMapping
    public Token auth(@RequestBody GithubCode githubCode) {
        String url = "https://github.com/login/oauth/access_token?" +
                "client_id=" + clientId + "&" +
                "client_secret=" + clientSecret + "&" +
                "state=" + githubCode.state + "&" +
                "code=" + githubCode.code;
        System.out.println("Requesting:" + url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, null, Token.class);
    }

    static class GithubCode {
        public String code;
        public String state;
    }

    static class Token {
        public String access_token;
        public String state;
    }
}

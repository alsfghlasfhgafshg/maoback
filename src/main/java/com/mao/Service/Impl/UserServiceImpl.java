package com.mao.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mao.Dao.UserDao;
import com.mao.Entity.User;
import com.mao.Service.UserService;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UserServiceImpl implements UserService {

    @Value("${auth.wechat.appId}")
    private String appid;

    @Value("${auth.wechat.secert}")
    private String secert;

    @Autowired
    private UserDao userDao;

    @Autowired
    RestTemplate restTemplate;

    public User getUserByOpenid(String openid) {
        return userDao.findUserByOpenid(openid);
    }

    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public String code2Session(String js_code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("secret", secert);
        params.add("js_code", js_code);
        params.add("grant_type", "authorization_code");

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);
        URI uri = uriComponentsBuilder.build().encode().toUri();

        String code2SessionString = restTemplate.getForObject(uri, String.class);

        System.out.println(code2SessionString);
        JSONObject code2SessionJson = JSONObject.parseObject(code2SessionString);
        if (code2SessionJson.containsKey("errcode")) {
            return null;
        } else {
            return code2SessionJson.getString("openid");
        }
    }
}

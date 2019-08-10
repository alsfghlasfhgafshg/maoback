package com.mao.Service;

import com.mao.Entity.User;

public interface UserService {
    User getUserByOpenid(String openid);
    int insertUser(User user);
    String code2Session(String js_code);
}

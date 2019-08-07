package com.mao.Service.Impl;

import com.mao.Dao.UserDao;
import com.mao.Entity.User;
import com.mao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByOpenid(String openid) {
        return userDao.findUserByOpenid(openid);
    }

    public int insertUser(User user) {
        return userDao.insertUser(user);
    }
}

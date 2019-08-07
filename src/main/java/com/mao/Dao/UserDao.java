package com.mao.Dao;

import com.mao.Entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User findUserByOpenid(@Param("openid") String openid);
    int insertUser(User user);
    User findById(@Param("id")Integer id);
}

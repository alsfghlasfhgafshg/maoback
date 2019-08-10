package com.mao.Dao;

import com.mao.Entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.TreeSet;

public interface NotificationDao {
    int insertByNotification(Notification notification);
    Notification[] findByToopenid(String toopenid);
    Notification findById(int id);
    int delete(@Param("toopenid")String toopenid,@Param("id")int id);

}

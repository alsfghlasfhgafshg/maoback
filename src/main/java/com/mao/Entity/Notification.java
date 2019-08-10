package com.mao.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notification {

    @Id
    int id;
    @Column
    String fromopenid;
    @Column
    String toopenid;
    @Column
    String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromopenid() {
        return fromopenid;
    }

    public void setFromopenid(String fromopenid) {
        this.fromopenid = fromopenid;
    }

    public String getToopenid() {
        return toopenid;
    }

    public void setToopenid(String toopenid) {
        this.toopenid = toopenid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

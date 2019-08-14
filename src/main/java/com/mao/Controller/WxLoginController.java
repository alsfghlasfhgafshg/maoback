package com.mao.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WxLoginController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(){
        return;
    }
}

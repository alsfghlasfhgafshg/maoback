package com.mao.Config;

import com.mao.Dao.UserDao;
import com.mao.Service.JWTService;
import com.mao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtLoginAuthorizationManager implements AuthenticationManager {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String js_code = (String) authentication.getCredentials();
        System.out.println("js_code="+js_code);

        ArrayList<Integer> list = new ArrayList<>();


        String openid = userService.code2Session(js_code);

        if (openid == null) {
            System.out.println("verify fail");
        } else {

            UserDetails user = userDao.findUserByOpenid(openid);
            if (user == null) {
                notificationService.insertSystemNotification(openid,"欢迎来到读书分享");
                wxAccountRepository.insertOneByWxAccount(new WxAccount(openid, ""));
                wxAccount = wxAccountRepository.findByOpenid(openid);
            }

            ArrayList<SimpleGrantedAuthority> grantedAuthoritys = new ArrayList<>();
            grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_WXUSER"));

            String jwt_token = jwtTokenService.generateToken(wxAccount.getUsername());

            WxAuthenticationToken authenticationToken = new WxAuthenticationToken(wxAccount, jwt_token, grantedAuthoritys);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            return authenticationToken;
        }
    }
}

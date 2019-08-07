package com.mao.Config;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mao.Dao.UserDao;
import com.mao.Service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import com.mao.Util.HttpServeletResponseUtil;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=httpServletRequest.getHeader("Authorization");

        if(token!=null && !token.equals("")){
            try {
                Integer id = jwtService.verifyToken(token);
                UserDetails user = userDao.findById(id);
                ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_WXUSER"));
                WxAuthenticationToken authenticationToken = new WxAuthenticationToken(user, id, grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            } catch (TokenExpiredException e) {
                //TODO: token过期
                HttpServeletResponseUtil.returnString(response, "TokenExpiredException");
            } catch (SignatureVerificationException e) {
                //TODO: token验证失败
                HttpServeletResponseUtil.returnString(response, "SignatureVerificationException");
            }
        }

        filterChain.doFilter(httpServletRequest, response);
    }
}

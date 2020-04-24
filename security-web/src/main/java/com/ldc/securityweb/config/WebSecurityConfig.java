package com.ldc.securityweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/4/22 21:11
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(new BCryptPasswordEncoder().encode("1")).roles("user")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("1")).roles("admin")
                ;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//此配置错了导致一直无法post请求login接口
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/user").hasRole("user")
                .antMatchers("/admin").hasRole("admin")
                .anyRequest().authenticated()
                ;
        //postman工具测试是保存sessionid的，使用默认JSESSIONID，springsecurity自动获取后判断
        http.formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String,Object> map = new HashMap<>();
                    map.put("status",200);
                    map.put("msg",authentication.getPrincipal());
                    String s = new ObjectMapper().writeValueAsString(map);
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String,Object> map = new HashMap<>();
                    if (e instanceof LockedException) {
                        map.put("msg","账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        map.put("msg","密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        map.put("msg","账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        map.put("msg","账户被禁用，请联系管理员!");
                    } else if (e instanceof BadCredentialsException) {
                        map.put("msg","用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                ;
        http.logout()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String,Object> map = new HashMap<>();
                    map.put("status",200);
                    map.put("msg","登出成功");
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                ;
        http.exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String,Object> map = new HashMap<>();
                    map.put("status",403);
                    map.put("msg","请求失败，请联系管理员");
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                ;
    }
}

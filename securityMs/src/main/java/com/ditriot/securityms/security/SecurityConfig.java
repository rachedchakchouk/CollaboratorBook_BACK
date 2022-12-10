package com.ditriot.securityms.security;

import com.ditriot.securityms.filter.CustomAuthenticationFilter;
import com.ditriot.securityms.filter.CustomAuthorizationFilter;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/*").permitAll();
        //
//    http.authorizeRequests().antMatchers("/api/login","/token/refresh/**","/api/user/save/**","localhost:8082/business/Companies/newCompany","http://localhost:8082/business/Companies/getAllCompanies","http://localhost:8087/code/**").permitAll();
//       http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.GET), "/**/get-public-key").permitAll();
//        http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.POST), "/**/Login").permitAll();
        //
       // http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**").permitAll();
      //  http.authorizeRequests().antMatchers("/api/login").authenticated();
//  df      http.authorizeRequests().antMatchers("**/localhost:3306/**").permitAll();
       // http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**","/swagger-config","/swagger-resources/**").permitAll();


/*
        http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.GET),"/api/user/**").authenticated();
        http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.GET),"/api/employee/**").authenticated();
        http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.GET),"localhost:8081/RH/**").authenticated();*/

       // http.authorizeRequests().antMatchers(String.valueOf(Request.HttpMethod.GET),"http://localhost:8082/business/Companies/getAllCompanies").authenticated();

        //http.authorizeRequests().antMatchers("api/users/token/refresh").permitAll();
       // http.authorizeRequests().antMatchers("/mission/missions").permitAll();
       // http.authorizeRequests().antMatchers("/mission/**").permitAll();
       // http.authorizeRequests().antMatchers("/cvtheque/**").permitAll();
      //  http.authorizeRequests().antMatchers("/testchat/**").permitAll();


//        http.authorizeRequests().anyRequest().authenticated();
       http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder( bCryptPasswordEncoder);
//
//    }
//     @Override
//    protected void configure(HttpSecurity http) throws Exception {
//       CustomAuthenticationFilter customAuthenticationFilter =new CustomAuthenticationFilter(authenticationManagerBean());
//      customAuthenticationFilter.setFilterProcessesUrl("/api/user/save/**");
//     http.csrf().disable();
//
//     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////     http.authorizeRequests().antMatchers("/api/login","/token/refresh/**","/api/user/save/**").permitAll();
//     http.authorizeRequests().antMatchers("/signup").permitAll();
//     http.authorizeRequests().antMatchers("/home").permitAll();
//    // http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
//
//    // http.authorizeRequests().anyRequest().permitAll();
//         http.authorizeRequests().anyRequest().authenticated();
//     //http.addFilter(customAuthenticationFilter);
//     http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean()throws Exception
//    {
//return super.authenticationManagerBean();
//    }
//
//}

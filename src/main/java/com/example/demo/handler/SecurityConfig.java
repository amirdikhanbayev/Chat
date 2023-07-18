package com.example.demo.handler;

import com.example.demo.service.user.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/users/create").hasRole("ADMIN")
                .antMatchers("/users/delete").hasRole("ADMIN")
                .antMatchers("/users/ListALL").hasRole("ADMIN")
                .antMatchers("/users/addRole").hasRole("ADMIN")
                .antMatchers("/role*").hasRole("ADMIN")
                .antMatchers("/users/changeUsername").permitAll()
                .antMatchers("/users/findById").permitAll()
                .antMatchers("/users/findByUsername").permitAll()
                .antMatchers("/users/allOnline").permitAll()
                .antMatchers("/users/join").permitAll()
                .antMatchers("/chatroom*").permitAll()
                .antMatchers("/message*").hasRole("ADMIN");
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    //
//    private void sharedSecurityConfiguration(HttpSecurity http) throws Exception{
//        http.csrf(AbstractHttpConfigurer::disable);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        sharedSecurityConfiguration(http);
//        http
//                .authorizeRequests()
//                .antMatchers("/users/create").permitAll()
//                .antMatchers("/users/delete*").permitAll()
//                .anyRequest().authenticated().and()
//                .formLogin(from ->
//                        from.loginProcessingUrl("/login")
//                                .usernameParameter("login")
//                                .passwordParameter("password")
//                                .failureHandler(customAuthenticationFailureHandler)
//                                .successHandler(customAuthenticationSuccessHandler))
//                .logout()
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login");
//        return http.build();
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailService);
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
//        return authenticationManagerBuilder.build();
//    }
}

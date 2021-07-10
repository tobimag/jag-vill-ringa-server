package com.example.enddigitsfetcher.resource;

import com.example.enddigitsfetcher.resource.BasicAuthProperties.User;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  final BasicAuthProperties basicAuthProperties;
  final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    basicAuthProperties.getUsers().forEach(user -> addUser(auth, user));
  }

  @SneakyThrows
  private void addUser(AuthenticationManagerBuilder auth, User user) {
    auth.inMemoryAuthentication()
        .withUser(user.getUsername())
        .password(encoder.encode(user.getPassword()))
        .roles(user.getRole());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/api/v1/end-digits/latest").hasRole("USER")
        .antMatchers("/api/v1/end-digits/").hasRole("ADMIN")
        .anyRequest().authenticated().and().httpBasic()
        .and()
        .authorizeRequests()
        .anyRequest().permitAll();
  }
}

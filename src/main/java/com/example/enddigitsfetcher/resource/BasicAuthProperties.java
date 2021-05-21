package com.example.enddigitsfetcher.resource;

import java.util.List;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "basic-auth")
@ConstructorBinding
@Value
public class BasicAuthProperties {

  List<User> users;

  @Value
  @ConstructorBinding
  public static class User {

    String username;
    String password;
    String role;

  }

}

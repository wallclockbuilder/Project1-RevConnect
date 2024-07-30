package com.revature.Project1.Util;


import com.revature.Project1.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return ( args )-> {
            boolean isUserTablePopulated = userService.isUserTablePopulated();
          if(!isUserTablePopulated){
              userService.populateSampleUsers();
          }else{
              System.out.println("Skipping populating users. Database already contains at least 3 users.");
          }
        };
    }


}

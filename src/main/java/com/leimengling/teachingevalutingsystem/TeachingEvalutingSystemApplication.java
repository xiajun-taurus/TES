package com.leimengling.teachingevalutingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeachingEvalutingSystemApplication {


  public static void main(String[] args) {
    SpringApplication.run(TeachingEvalutingSystemApplication.class, args);
  }

}


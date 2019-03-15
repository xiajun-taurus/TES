package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import com.leimengling.teachingevalutingsystem.service.SecurityQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class securityQuestion {
    @Autowired
    private SecurityQuestionService securityQuestionService;
    @Test
    public void addQuestion(){
        String questions = "您母亲的姓名是？,您父亲的姓名是？,您配偶的姓名是？,您的出生地是？,您高中班主任的名字是？,您初中班主任的名字是？,您小学班主任的名字是？,您的小学校名是？,您的学号（或工号）是？,您父亲的生日是？,您母亲的生日是？,您配偶的生日是？";
        String[] split = questions.split(",");
        for(String s :split){
            SecurityQuestion securityQuestion=new SecurityQuestion();
            securityQuestion.setQuestion(s);
            int result=securityQuestionService.addSQ(securityQuestion);
            if(result>0){
                log.info("添加密保问题成功！");
            }
            else {
                log.info("添加失败！");
            }
        }

    }

}

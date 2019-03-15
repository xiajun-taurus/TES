package com.leimengling.teachingevalutingsystem.provider;

import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Question;
import com.leimengling.teachingevalutingsystem.domain.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by Lei MengLing. 更新预处理sql
 */
public class UpdateProvider {

  public String QuestionUpdateProvider(Question question) {
    return new SQL() {
      {
        UPDATE("question_bank");
        if (question.getQuestion() != null) {
          SET("question=#{question}");
        }
        if (question.getAnswerA() != null) {
          SET("answerA=#{answerA}");
        }
        if (question.getAnswerB() != null) {
          SET("answerB=#{answerB}");
        }
        if (question.getAnswerC() != null) {
          SET("answerC=#{answerC}");
        }
        if (question.getAnswerD() != null) {
          SET("answerD=#{answerD}");
        }
        if (question.getScoreA() != null) {
          SET("scoreA=#{scoreA}");
        }
        if (question.getScoreB() != null) {
          SET("scoreB=#{scoreB}");
        }
        if (question.getScoreC() != null) {
          SET("scoreC=#{scoreC}");
        }
        if (question.getScoreD() != null) {
          SET("scoreD=#{scoreD}");
        }
        WHERE("oid=#{oid}");
      }
    }.toString();
  }

  //试卷更新判定
  public String PaperUpdateProvider(Paper paper) {
    return new SQL() {
      {
        UPDATE("papers");
        if (paper.getPaperTitle() != null) {
          SET("paper_title=#{paperTitle}");
        }
        if (paper.getQuestions() != null) {
          SET("questions = #{questions}");
        }
        WHERE("oid=#{oid}");
      }
    }.toString();
  }

  //结果更新
  public String CommentResultProvider(CommentResult result) {
    return new SQL() {
      {
        UPDATE("comment_result");
        if (result.getAverScore() != null) {
          SET("aver_score=#{averScore}");
        }
        if (result.getComment() != null) {
          SET("comment= #{comment}");
        }
        WHERE("oid=#{oid}");
      }
    }.toString();
  }

  public String UserProvider(User user) {
    return new SQL() {
      {
        UPDATE("user");
        if (user.getSchoolNo() != null) {
          SET("school_no = #{schoolNo}");
        }
        if (user.getUserName() != null) {
          SET("username=#{userName}");
        }
        if (user.getTrueName() != null) {
          SET("truename=#{trueName}");
        }
        if (user.getPassword() != null) {
          SET("password=#{password}");
        }
        if (user.getEMail() != null) {
          SET("email=#{eMail}");
        }
        if (user.getSignature() != null) {
          SET("signature = #{signature}");
        }
        if (user.getAvatar() != null) {
          SET("avatar = #{avatar}");
        }
        if (user.getRole() != null) {
          SET("role=#{role}");
        }
        if (user.getSecurityQuestion() != null) {
          SET("security_question = #{securityQuestion}");
        }
        if (user.getSecurityAnswer() != null) {
          SET("security_answer = #{securityAnswer}");
        }
        WHERE("oid = #{oid}");
      }
    }.toString();
  }
}

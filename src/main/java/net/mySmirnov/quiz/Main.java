package net.mySmirnov.quiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import net.mySmirnov.quiz.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        QuizApplication quizApplication = new QuizApplication(questionService);
        quizApplication.run();
    }
}
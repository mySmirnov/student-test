import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        QuizApplication quizApplication = new QuizApplication(questionService);
        quizApplication.run();
    }
}
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        Scanner scanner = new Scanner(System.in);
        try {
            for (int i = 0; i < questionService.length(); i++) {
                // Задать вопрос
                Optional<String> question = questionService.getQuestion(i);
                if (!question.isPresent()) {
                    break;
                }
                System.out.println(question.get());
                // Принять ответ
                String answer = scanner.nextLine();
                System.out.println(questionService.checkAnswer(i, answer));
            }

            // Выдать результат опроса
            System.out.println(questionService.report());
            if (questionService.resultAll()) {
                System.out.println("Тест пройден успешно!");
            } else {
                System.out.println("Тест провален!");
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
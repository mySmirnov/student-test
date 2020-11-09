import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.InMemoryQuestionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        InMemoryQuestionService questionService = context.getBean(InMemoryQuestionService.class);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < questionService.length(); i++) {
            // Задать вопрос
            String question = questionService.askQuestion(i);
            if (question == "break") {
                break;
            }
            System.out.println(question);
            // Принять ответ
            String answer = scanner.nextLine();
            String resultQuestion = questionService.takeAnswer(i, answer);
            System.out.println(resultQuestion);
        }
        // Выдать результат опроса
        System.out.println(questionService.reportResult());
    }
}
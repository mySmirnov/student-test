import service.QuestionServiceImpl;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        QuestionServiceImpl questionService = new QuestionServiceImpl();

        String question = "Как тебя зовут?";
        System.out.println(question);

        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        System.out.println("Здраавствуй " + response + "!");

        questionService.saveResponse(question, response);

        System.out.println(questionService.getQuestion());
        scanner.nextLine();
    }
}

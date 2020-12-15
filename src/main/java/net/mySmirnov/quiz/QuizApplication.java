package net.mySmirnov.quiz;

import net.mySmirnov.quiz.service.QuestionService;
import net.mySmirnov.quiz.ui.InputUIImpl;
import net.mySmirnov.quiz.ui.OutputUI;
import net.mySmirnov.quiz.ui.OutputUIImpl;

import java.util.Optional;
import java.util.Scanner;

public class QuizApplication {
    QuestionService questionService;

    public QuizApplication(QuestionService questionService) {
        this.questionService = questionService;
    }

    public void run() {
            for (int i = 0; i < questionService.length(); i++) {
                // Задать вопрос
                Optional<String> question = questionService.getQuestion(i);
                if (!question.isPresent()) {
                    break;
                }
                new OutputUIImpl().write(question.get());
                // Принять ответ
                String answer = new InputUIImpl().read();
                Object obj = questionService.checkAnswer(i, answer);
                new OutputUIImpl().write(obj);
            }
            // Выдать результат опроса
            System.out.println(questionService.report());
            if (questionService.resultAll()) {
                new OutputUIImpl().write("Тест пройден успешно!");
            } else {
                new OutputUIImpl().write("Тест провален!");
            }
    }
}

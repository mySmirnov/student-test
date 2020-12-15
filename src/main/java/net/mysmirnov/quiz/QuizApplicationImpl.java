package net.mysmirnov.quiz;

import net.mysmirnov.quiz.service.QuestionService;
import net.mysmirnov.quiz.ui.InputUIService;
import net.mysmirnov.quiz.ui.OutputUIService;

import java.util.Optional;

public class QuizApplicationImpl implements QuizApplication {
    private QuestionService questionService;
    private InputUIService inputUIService;
    private OutputUIService outputUIService;

    public QuizApplicationImpl(QuestionService questionService, InputUIService inputUIService, OutputUIService outputUIService) {
        this.questionService = questionService;
        this.inputUIService = inputUIService;
        this.outputUIService = outputUIService;
    }

    public void run() {
        for (int i = 0; i < questionService.length(); i++) {
            // Задать вопрос
            Optional<String> question = questionService.getQuestion(i);
            if (!question.isPresent()) {
                break;
            }
            outputUIService.write(question.get());
            // Принять ответ
            outputUIService.write(questionService.checkAnswer(i, inputUIService.read()));
        }
        // Выдать результат опроса
        outputUIService.write(questionService.report());

        if (questionService.resultAll()) {
            outputUIService.write("Тест пройден успешно!");
        } else {
            outputUIService.write("Тест провален!");
        }
    }
}

package net.mysmirnov.quiz;

import net.mysmirnov.quiz.service.QuestionService;
import net.mysmirnov.quiz.ui.InputUIService;
import net.mysmirnov.quiz.ui.InputUIServiceImpl;
import net.mysmirnov.quiz.ui.OutputUIService;

import java.util.Optional;

public class QuizApplicationImpl implements QuizApplication {
    private QuestionService questionService;
    private InputUIService input;
    private OutputUIService output;
    private boolean exit;
    public QuizApplicationImpl(QuestionService questionService, InputUIService input, OutputUIService output) {
        this.questionService = questionService;
        this.input = input;
        this.output = output;
    }

    public void run() {

        for (int i = 0; i < questionService.length(); i++) {
// Задать вопрос -------------------------------------------------------------------------------------------------------
            Optional<String> question = questionService.getQuestion(i);
            if (!question.isPresent())
                break;
            output.write(question.get());
// Принять ответ пользователя и выдать результат -----------------------------------------------------------------------
//            if (!input.hasNextLine())
//                break;
//            while (input.hasNextLine()) {
                Optional<String> answer = input.read();
                if (!answer.isPresent()) {
                    output.write(String.format("Вы ввели пустое значение, ответьте на вопрос: %s", question.get()));
                    continue;
                }
                output.write(questionService.checkAnswer(i, answer.get()));
//                break;
//            }
        }
// Выдать результат опроса ---------------------------------------------------------------------------------------------
        output.write(questionService.report());
        if (questionService.resultAll()) {
            output.write("Тест пройден успешно!");
        } else {
            output.write("Тест провален!");
        }
    }
}
package net.mysmirnov.quiz;

import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.daoimpl.AnswerDaoImpl;
import net.mysmirnov.quiz.daoimpl.AttemptDaoImpl;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Attempt;
import net.mysmirnov.quiz.service.QuestionService;
import net.mysmirnov.quiz.ui.InputUIService;
import net.mysmirnov.quiz.ui.OutputUIService;
import net.mysmirnov.quiz.utils.JdbcUtils;

import java.sql.*;
import java.util.Calendar;
import java.util.Optional;

public class QuizApplicationImpl implements QuizApplication {
    private QuestionService questionService;
    private InputUIService input;
    private OutputUIService output;
    private AttemptDaoImpl attemptDao;
    private boolean exit;

    public QuizApplicationImpl(QuestionService questionService, InputUIService input, OutputUIService output) {
        this.questionService = questionService;
        this.input = input;
        this.output = output;
    }

    public void run() throws SQLException {
        for (int i = 0; i < questionService.length() && !exit; i++) {
// Задать вопрос -------------------------------------------------------------------------------------------------------
            Optional<String> question = questionService.getQuestion(i);
            if (!question.isPresent()) {
                break;
            }
            output.write(question.get());
// Принять ответ пользователя и выдать результат -----------------------------------------------------------------------
            processAnswer(i);

        }
// Выдать результат опроса ---------------------------------------------------------------------------------------------
        output.write(questionService.report());
        if (questionService.resultAll()) {
            output.write("Тест пройден успешно!");
        } else {
            output.write("Тест провален!");
        }

    }

    private void processAnswer(int i) throws SQLException {
        while (input.hasNextLine()) {
            Optional<String> answerText = input.read();
            if (!answerText.isPresent()) {
                output.write(String.format("Вы ввели пустое значение, ответьте на вопрос: "));
                continue;
            }
            output.write(questionService.checkAnswer(i, answerText.get()));
            return;
        }
        exit = true;
    }
}
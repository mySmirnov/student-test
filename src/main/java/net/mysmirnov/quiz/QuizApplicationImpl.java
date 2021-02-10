package net.mysmirnov.quiz;

import net.mysmirnov.quiz.dao.AnswerDao;
import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Attempt;
import net.mysmirnov.quiz.service.question.QuestionService;
import net.mysmirnov.quiz.service.ui.InputUIService;
import net.mysmirnov.quiz.service.ui.OutputUIService;

import java.sql.*;
import java.util.Optional;

public class QuizApplicationImpl implements QuizApplication {
    private QuestionService questionService;
    private InputUIService inputUIService;
    private OutputUIService outputUIService;
    private AttemptDao attemptDao;
    private AnswerDao answerDao;

    // TODO: 07.02.2021 запилить в контекст?
    private Attempt attempt = new Attempt();
    private boolean exit;
    private static boolean setUpIsDone = false;

    public QuizApplicationImpl(
            QuestionService questionService,
            InputUIService inputUIService,
            OutputUIService outputUIService,
            AttemptDao attemptDao,
            AnswerDao answerDao) {
        this.questionService = questionService;
        this.inputUIService = inputUIService;
        this.outputUIService = outputUIService;
        this.attemptDao = attemptDao;
        this.answerDao = answerDao;
    }

    public void run() throws SQLException {
        attemptDao.insert(attempt);
        for (int i = 0; i < questionService.length() && !exit; i++) {
// Задать вопрос -------------------------------------------------------------------------------------------------------
            Optional<String> question = questionService.getQuestion(i);
            if (!question.isPresent()) {
                break;
            }
            outputUIService.write(question.get());
// Принять ответ пользователя и выдать результат -----------------------------------------------------------------------
            processAnswer(i);
        }
// Выдать результат опроса ---------------------------------------------------------------------------------------------
        outputUIService.write(questionService.report());
        if (questionService.resultAll()) {
            outputUIService.write("Тест пройден успешно!");
        } else {
            outputUIService.write("Тест провален!");
        }

    }

    private void processAnswer(int i) throws SQLException {
        while (inputUIService.hasNextLine()) {
            Optional<String> answerText = inputUIService.read();
            if (!answerText.isPresent()) {
                outputUIService.write(String.format("Вы ввели пустое значение, ответьте на вопрос: "));
                continue;
            }
            outputUIService.write(questionService.checkAnswer(i, answerText.get()));
            Answer answer = new Answer(attempt.getId(), i, answerText.get());
            answerDao.insert(answer);
            return;
        }
        exit = true;
    }
}
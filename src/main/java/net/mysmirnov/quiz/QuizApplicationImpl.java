package net.mysmirnov.quiz;

import net.mysmirnov.quiz.dao.AnswerDao;
import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.daoimpl.AttemptDaoImpl;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Attempt;
import net.mysmirnov.quiz.service.question.QuestionService;
import net.mysmirnov.quiz.service.ui.InputUIService;
import net.mysmirnov.quiz.service.ui.OutputUIService;
import net.mysmirnov.quiz.utils.JdbcUtils;

import java.sql.*;
import java.util.Calendar;
import java.util.Optional;

public class QuizApplicationImpl implements QuizApplication {
    private QuestionService questionService;
    private InputUIService input;
    private OutputUIService output;
    private AttemptDao attemptDao;
    private AnswerDao answerDao;
    private Attempt attempt;

    private boolean exit;
    private static boolean setUpIsDone = false;

    public QuizApplicationImpl(QuestionService questionService, InputUIService input, OutputUIService output, AttemptDao attemptDao, AnswerDao answerDao) {
        this.questionService = questionService;
        this.input = input;
        this.output = output;
        this.attemptDao = attemptDao;
        this.answerDao = answerDao;
    }

    public void run() throws SQLException {
        Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        attempt = new Attempt(date, 1.00);
        attemptDao.insert(attempt);

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
            Answer answer = new Answer(attempt.getId(), i, answerText.get());
            answerDao.insert(answer);
            return;
        }
        exit = true;
    }

    public void init() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }
    }

    public void destroy() {
        if (setUpIsDone) {
            JdbcUtils.closeConnection();
        }
    }
}
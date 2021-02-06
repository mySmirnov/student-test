package net.mysmirnov.quiz.service.question;

import net.mysmirnov.quiz.dao.QuestionDao;
import net.mysmirnov.quiz.daoimpl.QuestionDaoImpl;
import net.mysmirnov.quiz.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbQuestionService extends QuestionServiceImpl{
    QuestionDao questionDao;

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public DbQuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);

    public void init() throws SQLException {
        logger.info("initialisation");
        setQuestions(questionDao.findAll());
    }
}

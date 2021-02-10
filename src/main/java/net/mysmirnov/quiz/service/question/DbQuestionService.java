package net.mysmirnov.quiz.service.question;

import net.mysmirnov.quiz.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DbQuestionService extends QuestionServiceImpl{
    private QuestionDao questionDao;

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

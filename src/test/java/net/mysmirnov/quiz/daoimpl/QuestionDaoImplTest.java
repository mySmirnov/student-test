package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.utils.JdbcUtils;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {
    QuestionDaoImpl questionDao = new QuestionDaoImpl();
    Question question = new Question("Text of question #1" , "Text of answer #1");

    private static boolean setUpIsDone = false;

    @BeforeAll
    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }

    }

    @AfterClass
    public static void close() {
        if (setUpIsDone) {
            JdbcUtils.closeConnection();
        }
    }

    @BeforeEach()
    public void clearDatabase() throws SQLException {
        questionDao.deleteAll();
    }

    @Test
    void insert() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(question, questionFromDB.get());
    }


    @Test
    void update() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(question, questionFromDB.get());
        question.setQuestionText("other text of question #1");
        questionDao.update(question);
        Optional<Question> updateQuestionFromDB = questionDao.findById(question.getId());
        assertEquals(questionFromDB.get().getId(), updateQuestionFromDB.get().getId());
        assertNotEquals(questionFromDB.get().getQuestionText(), updateQuestionFromDB.get().getQuestionText());
    }

    @Test
    void delete() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(question, questionFromDB.get());
        questionDao.delete(question.getId());
        questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.empty(), questionFromDB);
    }

    @Test
    void deleteAll() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(question, questionFromDB.get());
        questionDao.deleteAll();
        questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.empty(), questionFromDB);
    }
}
package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.utils.JdbcUtils;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDaoImplTest {
    AnswerDaoImpl answerDao = new AnswerDaoImpl();
    Answer answer = new Answer(1, 1, "text of answer #1");

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
        answerDao.deleteAll();
    }

    @Test
    void insertAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.getById(answer.getId());
        assertEquals(answer, answerFromDB.get());
    }

    @Test
    void updateAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.getById(answer.getId());
        assertEquals(answer, answerFromDB.get());
        answer.setAnswerText("other text of answer #1");
        answerDao.update(answer);
        Optional<Answer> updateAnswerFromDB = answerDao.getById(answer.getId());
        assertEquals(answerFromDB.get().getId(), updateAnswerFromDB.get().getId());
        assertNotEquals(answerFromDB.get().getAnswerText(), updateAnswerFromDB.get().getAnswerText());
    }

    @Test
    void deleteAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.getById(answer.getId());
        assertEquals(answer, answerFromDB.get());
        answerDao.delete(answer.getId());
        answerFromDB = answerDao.getById(answer.getId());
        assertEquals(Optional.empty(), answerFromDB);
    }

    @Test
    void deleteAnswers() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.getById(answer.getId());
        assertEquals(answer, answerFromDB.get());
        answerDao.deleteAll();
        answerFromDB = answerDao.getById(answer.getId());
        assertEquals(Optional.empty(), answerFromDB);
    }
}
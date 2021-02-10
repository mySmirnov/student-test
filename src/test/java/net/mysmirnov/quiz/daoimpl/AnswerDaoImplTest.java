package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.dao.daoimpl.AnswerDaoImpl;
import net.mysmirnov.quiz.model.Answer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDaoImplTest {
    private static  JdbcProvider jdbcProvider = new JdbcProvider(
            "root",
            "rei36djg",
            "jdbc:mysql://localhost:3306/quiz?useUnicode=yes&characterEncoding=UTF8&useSSL=false"
    );

    private static AnswerDaoImpl answerDao = new AnswerDaoImpl();
    private final Answer answer = new Answer(1, 1, "text of answer #1");

    @BeforeAll
    public static void setUp() {
        answerDao.setJdbcProvider(jdbcProvider);
        jdbcProvider.init();
    }
    @BeforeEach()
    public void clearDatabase() throws SQLException {
        answerDao.deleteAll();
    }

    @AfterAll
    public static void close() throws SQLException {
        jdbcProvider.destroy();
    }

    @Test
    void insertAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.of(answer), answerFromDB);
    }

    @Test
    void updateAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.of(answer), answerFromDB);
        answer.setAnswerText("other text of answer #1");
        answerDao.update(answer);
        Optional<Answer> updateAnswerFromDB = answerDao.findById(answer.getId());
        assertEquals(answerFromDB.get().getId(), updateAnswerFromDB.get().getId());
        assertNotEquals(answerFromDB.get().getAnswerText(), updateAnswerFromDB.get().getAnswerText());
    }

    @Test
    void deleteAnswer() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.of(answer), answerFromDB);
        answerDao.delete(answer.getId());
        answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.empty(), answerFromDB);
    }

    @Test
    void deleteAnswers() throws SQLException {
        answerDao.insert(answer);
        Optional<Answer> answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.of(answer), answerFromDB);
        answerDao.deleteAll();
        answerFromDB = answerDao.findById(answer.getId());
        assertEquals(Optional.empty(), answerFromDB);
    }
}
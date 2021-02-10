package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.dao.daoimpl.QuestionDaoImpl;
import net.mysmirnov.quiz.model.Question;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {
    private static JdbcProvider jdbcProvider = new JdbcProvider(
            "root",
            "rei36djg",
            "jdbc:mysql://localhost:3306/quiz?useUnicode=yes&characterEncoding=UTF8&useSSL=false"
    );
    private static QuestionDaoImpl questionDao = new QuestionDaoImpl();
    private Question question = new Question("Text of question #1" , "Text of answer #1");

    @BeforeAll
    public static void setUp() {
        questionDao.setJdbcProvider(jdbcProvider);
        jdbcProvider.init();
    }

    @AfterAll
    public static void close() throws SQLException {
        jdbcProvider.destroy();
    }

    @BeforeEach()
    public void clearDatabase() throws SQLException {
        questionDao.deleteAll();
    }

    @Test
    void insert() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.of(question), questionFromDB);
    }

    @Test
    void findAll() throws SQLException{
        questionDao.insert(question);
        questionDao.insert(question);
        List<Question> questions = questionDao.findAll();
        assertEquals(2,questions.size());
    }


    @Test
    void update() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.of(question), questionFromDB);
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
        assertEquals(Optional.of(question), questionFromDB);
        questionDao.delete(question.getId());
        questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.empty(), questionFromDB);
    }

    @Test
    void deleteAll() throws SQLException {
        questionDao.insert(question);
        Optional<Question> questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.of(question), questionFromDB);
        questionDao.deleteAll();
        questionFromDB = questionDao.findById(question.getId());
        assertEquals(Optional.empty(), questionFromDB);
    }
}
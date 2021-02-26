package net.mysmirnov.quiz.service.question;

import net.mysmirnov.quiz.dao.QuestionDao;
import net.mysmirnov.quiz.model.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class DbQuestionServiceTest {
    private final List<Question> questions = Arrays.asList(
            new Question(0, "0 + 1 = ", "1"),
            new Question(1, "2 - 1 = ", "1"),
            new Question(2, "1 * 1 = ", "1")
    );

    @Test
    void shouldReturnQuestionsLoadedFromDataBase() throws SQLException {
        QuestionDao questionDao = mock(QuestionDao.class);
        when(questionDao.findAll()).thenReturn(questions);
        DbQuestionService questionService = new DbQuestionService(questionDao);
        questionService.init();
        assertEquals("0 + 1 = ", questionService.getQuestion(0).get());
    }

    @Test
    void shouldReturnEmptyIfDontQuestionInDataBase() throws SQLException {
        QuestionDao questionDao = mock(QuestionDao.class);
        DbQuestionService questionService = new DbQuestionService(questionDao);
        questionService.init();
        assertEquals(Optional.empty(), questionService.getQuestion(0));
    }

}

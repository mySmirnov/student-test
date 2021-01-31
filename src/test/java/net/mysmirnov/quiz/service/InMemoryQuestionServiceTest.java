package net.mysmirnov.quiz.service;

import net.mysmirnov.quiz.service.InMemoryQuestionService;
import net.mysmirnov.quiz.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryQuestionServiceTest {
    private InMemoryQuestionService questionService;
    private final List<Question> questions = Arrays.asList(
            new Question(0, "0 + 1 = ", "1"),
            new Question(1, "2 - 1 = ", "1"),
            new Question(2, "1 * 1 = ", "1")
    );

    @BeforeEach
    void setUp() {
        questionService = new InMemoryQuestionService(questions);
        questionService.init();
    }

    @Test
    void shouldReturnStringIfMaxNumberOfWrongAnswersLessThanCountFalse() {
        assertTrue(questionService.getQuestion(0).isPresent());
    }

    @Test
    void shouldReturnIsEmptyIfMaxNumberOfWrongAnswersMoreOrEqualsThanCountFalse() {
        questionService.setMaxNumberOfWrongAnswers(-1);
        assertFalse(questionService.getQuestion(0).isPresent());
    }

    @Test
    void shouldReturnEmptyIfIndexMoreThanExist() {
        assertFalse(questionService.getQuestion(3).isPresent());
    }

    @Test
    void shouldOk() {
        assertTrue(questionService.checkAnswer(1, "1"));
        assertFalse(questionService.checkAnswer(1, "Wrong Answer"));
    }

    @Test
    void shouldTrowExceptionIfWrongIndexQuestion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> questionService.checkAnswer(3, "1"));
    }

    @Test
    void shouldReturnFalseIfWrongAnswerMoreLessAllowed() {
        questionService.checkAnswer(0, "1");
        questionService.checkAnswer(1, "1");
        questionService.checkAnswer(2, "1");
        assertTrue(questionService.resultAll());
    }

    @Test
    void shouldReturnFalseIfWrongAnswerMoreMoreAllowed() {
        questionService.checkAnswer(0, "1");
        questionService.checkAnswer(1, "1");
        questionService.checkAnswer(2, "Wrong Answer");
        assertFalse(questionService.resultAll());
    }

    @Test
    void shouldReturnStringResultIfTextReportOk() {
        questionService.checkAnswer(0, "1");
        questionService.checkAnswer(1, "1");
        questionService.checkAnswer(2, "Wrong Answer");
        assertEquals(
                String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 2, 1),
                questionService.report()
        );
    }

    @Test
    void shouldOkIfLengthASExpected() {
        assertEquals(questions.size(), questionService.length());
    }

    @Test
    void should(){
        List<Question> list = Arrays.asList(
                new Question()
        );
        InMemoryQuestionService questionService = new InMemoryQuestionService(list);
        questionService.init();
        assertEquals(Optional.empty(),questionService.getQuestion(0));
    }
}
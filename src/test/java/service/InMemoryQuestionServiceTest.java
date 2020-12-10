package service;

import model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryQuestionServiceTest {
    List<Question> questions = new ArrayList<>();
    InMemoryQuestionService questionService = new InMemoryQuestionService(questions);
    Question question0 = new Question(0, "0 + 1 = ", "1");
    Question question1 = new Question(1, "2 - 1 = ", "1");
    Question question2 = new Question(2, "1 * 1 = ", "1");

    @BeforeEach
    void setUp() {
        questions.add(question0);
        questions.add(question1);
        questions.add(question2);
        questionService.init();
    }

    @Test
    void ShouldReturnStringIfMaxNumberOfWrongAnswersLessThanCountFalse() {
        assertTrue(questionService.getQuestion(0).isPresent());
    }

    @Test
    void ShouldReturnIsEmptyIfMaxNumberOfWrongAnswersMoreOrEqualsThanCountFalse() {
        questionService.setMaxNumberOfWrongAnswers(-1);
        assertFalse(questionService.getQuestion(0).isPresent());
    }

    @Test
    void ShouldReturnEmptyIfIndexMoreThanExist() {
        assertFalse(questionService.getQuestion(3).isPresent());
    }

    @Test
    void ShouldOk() {
        assertTrue(questionService.checkAnswer(1, "1"));
        assertFalse(questionService.checkAnswer(1, "Wrong Answer"));
    }

    @Test
    void shouldTrowExceptionIfWrongIndexQuestion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionService.checkAnswer(3, "1");
        });
    }

    @Test
    void shouldReturnFalseIfWrongAnswerMoreLessAllowed() {
        questionService.checkAnswer(question0.getId(), question0.getAnswer());
        questionService.checkAnswer(question1.getId(), question1.getAnswer());
        questionService.checkAnswer(question2.getId(), question2.getAnswer());
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
}
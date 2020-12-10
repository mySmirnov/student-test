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
        // этот метод возвращает текст вопроса по запросу его номера
        // если вопрос с таки номером существует и количество неверных ответов меньше допустимого максимума
        // возвращается текст вопроса
        // иначе выбрасывает ошибку индексации
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
        int num = question0.getId();
        String answer = question0.getAnswer();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionService.checkAnswer(3, "1");
        });
    }

    @Test
        // этот метод возвращает значение типа boolean
        // FALSE - если количество неверных ответов привысило допустимый максимум
        // TURE - в обратном случае тест пройден успешно
    void shouldReturnFalseIfWrongAnswerMoreLessAllowed() {
        questionService.checkAnswer(question0.getId(), question0.getAnswer());
        questionService.checkAnswer(question1.getId(), question1.getAnswer());
        questionService.checkAnswer(question2.getId(), question2.getAnswer());
        // Проверяем что тест завершен успешео т.е. количество правильных ответов не превысело допустимый максимум
        assertTrue(questionService.resultAll());
    }

    @Test
    void resultAll_shouldReturnFalseIfWrongAnswerMoreMoreAllowed() {
        questionService.checkAnswer(0, "1");
        questionService.checkAnswer(1, "1");
        questionService.checkAnswer(2, "");
        // Проверяем что тест завершен успешео т.е. количество правильных ответов не превысело допустимый максимум
        assertFalse(questionService.resultAll());
    }

    @Test
    void shouldReturnStringResultIfTextReportOk() {
        questionService.checkAnswer(0, "1");
        questionService.checkAnswer(1, "1");
        questionService.checkAnswer(2, "");
        String expected = String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 2, 1);
        String actual = questionService.report();
        assertEquals(expected, actual);
    }

    @Test
    void shouldOkIfLengthASExpected() {
        assertEquals(questions.size(), questionService.length());
    }
}
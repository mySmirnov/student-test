package service;

import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryQuestionService;
import service.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {
    List<Question> questions = new ArrayList<>();
    QuestionService questionService = new InMemoryQuestionService(questions);
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
    void getQuestion_ShouldReturnStringIfMaxNumberOfWrongAnswersLessThanCountFalse() {
        questionService.setMaxNumberOfWrongAnswers(1);
        assertTrue(questionService.getQuestion(0).isPresent());
    }

    @Test
    void getQuestion_ShouldReturnIsEmptyIfMaxNumberOfWrongAnswersMoreOrEqualsThanCountFalse() {
        questionService.setMaxNumberOfWrongAnswers(0);
        assertFalse(questionService.getQuestion(0).isPresent());
    }

    @Test
    void getQuestion_ShouldTrowExceptionIfIndexMoreThanExist() {
        questionService.setMaxNumberOfWrongAnswers(1);
        try {
            questionService.getQuestion(3);
            fail();
        } catch (IllegalArgumentException ex) {
            String expected = String.format("Question with number=%d does not exist", questions.size());
            String actual = ex.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
        // этот метод возвращает правильность ответа полученного от пользователя
        // и считает количесво правельных и не правильных ответов
    void checkAnswer() {
        int num = question0.getId();
        String answer = question0.getAnswer();
        questionService.setMaxNumberOfWrongAnswers(1);
        // Проверка верности ответа
        assertTrue(questionService.checkAnswer(num, answer));
        assertFalse(questionService.checkAnswer(num, ""));
    }

    @Test
        // этот метод возвращает значение типа boolean
        // FALSE - если количество неверных ответов привысило допустимый максимум
        // TURE - в обратном случае тест пройден успешно
    void resultAll_shouldReturnFalseIfWrongAnswerMoreLessAllowed() {
        questionService.setMaxNumberOfWrongAnswers(1);
        questionService.checkAnswer(question0.getId(), question0.getAnswer());
        questionService.checkAnswer(question1.getId(), question1.getAnswer());
        questionService.checkAnswer(question2.getId(), question2.getAnswer());
        // Проверяем что тест завершен успешео т.е. количество правильных ответов не превысело допустимый максимум
        assertTrue(questionService.resultAll());
    }

    @Test
    void resultAll_shouldReturnFalseIfWrongAnswerMoreMoreAllowed() {
        int num = question0.getId();
        String question = question0.getQuestion();
        String answer = question0.getAnswer();
        questionService.setMaxNumberOfWrongAnswers(1);
        questionService.checkAnswer(0, answer);
        questionService.checkAnswer(1, answer);
        questionService.checkAnswer(2, "");
        // Проверяем что тест завершен успешео т.е. количество правильных ответов не превысело допустимый максимум
        assertFalse(questionService.resultAll());
    }

    @Test
        // этот метод возвращает сообщение которое содежит
        // количество заданых вопросов, количество верных и не верных ответов
    void report() {
        int num = 0;
        String question = "0 + 1 = ";
        String answer = "1";
        questions.add(new Question(num, question, answer));
        questionService.setMaxNumberOfWrongAnswers(1);
        questionService.checkAnswer(0, answer);
        questionService.checkAnswer(1, answer);
        questionService.checkAnswer(2, "");
        String expected = String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d",3,2,1);
        String actual = questionService.report();
        // проверить что отчет вывает соответствующий текст
        assertEquals(expected, actual);
    }

    @Test
        // этот метод возвращает количество вопросов в тесте
    void length() {
        questionService.setMaxNumberOfWrongAnswers(0);
        // проверить что доинна теста соответствует длинне добавляемого списка
        assertEquals(questions.size(), questionService.length());
    }

    @Test
        // этот метод добавляет вопросы в тест
        // 1. вопросы добавляются из внешнего CSV-файла
        // 2. вопросы захардкожены в тексте программы
    void init() {
    }
}
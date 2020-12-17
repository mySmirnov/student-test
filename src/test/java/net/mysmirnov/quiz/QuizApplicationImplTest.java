package net.mysmirnov.quiz;

import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.service.CsvQuestionService;
import net.mysmirnov.quiz.service.InMemoryQuestionService;
import net.mysmirnov.quiz.service.QuestionService;
import net.mysmirnov.quiz.ui.InputUIService;
import net.mysmirnov.quiz.ui.OutputUIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class QuizApplicationImplTest {
    private InMemoryQuestionService questionService;

    @BeforeEach
    void setUp() {
        List<Question> questions = Arrays.asList(
                new Question(0, "0 + 1 = ", "1"),
                new Question(1, "2 - 1 = ", "1"),
                new Question(2, "1 * 1 = ", "1")
        );
        questionService = new InMemoryQuestionService(questions);
        questionService.init();
    }

    @Test
    void shouldOkIfAnswerTrueAndPrintQuestionAndResultAnswer() {
        InMemoryQuestionService questionService1 = new InMemoryQuestionService(Arrays.asList(
                new Question(0, "0 + 1 = ", "1"))
        );
        questionService1.init();
        InputUIService inputUIService = mock(InputUIService.class);
        OutputUIService outputUIService = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService1, inputUIService, outputUIService);
        when(inputUIService.read()).thenReturn("1");
        quizApplication.run();

        verify(outputUIService).write("0 + 1 = ");
        verify(outputUIService).write(true);
    }

    @Test
    void shouldOkIfAnswerFalseAndPrintQuestionAndResultAnswer() {
        InMemoryQuestionService questionService1 = new InMemoryQuestionService(Arrays.asList(
                new Question(0, "0 + 1 = ", "1"))
        );
        questionService1.init();
        InputUIService inputUIService = mock(InputUIService.class);
        OutputUIService outputUIService = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService1, inputUIService, outputUIService);
        when(inputUIService.read()).thenReturn("2");
        quizApplication.run();

        verify(outputUIService).write("0 + 1 = ");
        verify(outputUIService).write(false);
    }

    @Test
    void shouldOkIfQuizPassAndPrintAllQuestionAndResultAnswerAndResult() {
        InputUIService inputUIService = mock(InputUIService.class);
        OutputUIService outputUIService = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, inputUIService, outputUIService);
        when(inputUIService.read()).thenReturn("1", "1", "1");
        quizApplication.run();

        InOrder inOrder = inOrder(outputUIService);
        inOrder.verify(outputUIService).write("0 + 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService).write("2 - 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService).write("1 * 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 3, 0));
        inOrder.verify(outputUIService).write("Тест пройден успешно!");
    }

    @Test
    void shouldOkIfQuizFailedAndPrintAllQuestionAndResultAnswerAndResult() {
        InputUIService inputUIService = mock(InputUIService.class);
        OutputUIService outputUIService = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, inputUIService, outputUIService);
        when(inputUIService.read()).thenReturn("1", "1", "2");
        quizApplication.run();

        InOrder inOrder = inOrder(outputUIService);
        inOrder.verify(outputUIService).write("0 + 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService).write("2 - 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService).write("1 * 1 = ");
        inOrder.verify(outputUIService).write(false);
        inOrder.verify(outputUIService)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 2, 1));
        inOrder.verify(outputUIService).write("Тест провален!");
    }

    @Test
    void shouldOkIfQuizEarlyFailedAndPrintAllQuestionAndResultAnswerAndResult() {
        InputUIService inputUIService = mock(InputUIService.class);
        OutputUIService outputUIService = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, inputUIService, outputUIService);
        when(inputUIService.read()).thenReturn("1", "2", "2");
        quizApplication.run();

        InOrder inOrder = inOrder(outputUIService);
        inOrder.verify(outputUIService).write("0 + 1 = ");
        inOrder.verify(outputUIService).write(true);
        inOrder.verify(outputUIService).write("2 - 1 = ");
        inOrder.verify(outputUIService).write(false);
        inOrder.verify(outputUIService)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 2, 1, 1));
        inOrder.verify(outputUIService).write("Тест провален!");
    }
}
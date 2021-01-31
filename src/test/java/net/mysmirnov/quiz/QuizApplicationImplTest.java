package net.mysmirnov.quiz;

import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.service.InMemoryQuestionService;
import net.mysmirnov.quiz.service.QuestionService;
import net.mysmirnov.quiz.ui.InputUIService;
import net.mysmirnov.quiz.ui.InputUIServiceImpl;
import net.mysmirnov.quiz.ui.OutputUIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    // проверяем что при приеме empty из QuestionService QuizApplication заевершается корректно

    @Test
    void shouldFailSafelyInCaseIfQuestionServiceDoesNotHaveExpectedQuestion() throws SQLException {
        QuestionService questionService1 = mock(QuestionService.class);
        InputUIServiceImpl input = mock(InputUIServiceImpl.class);
        OutputUIService output = mock(OutputUIService.class);

        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService1, input, output);

        when(questionService1.resultAll()).thenReturn(true);
        when(questionService1.length()).thenReturn(1);
        when(questionService1.report()).thenReturn("должен выйти из строя в случае, если служба вопросов не ожидала вопроса");
        when(questionService1.getQuestion(1)).thenReturn(Optional.empty());
        when(questionService1.length()).thenReturn(1);
        quizApplication.run();

                InOrder inOrder = inOrder(output);
        inOrder.verify(output)
                .write("должен выйти из строя в случае, если служба вопросов не ожидала вопроса");
        inOrder.
                verify(output).write("Тест пройден успешно!");
    }

    @Test
    void shouldOkIfAnswerTrueAndPrintQuestionAndResultAnswer() throws SQLException {
        questionService = new InMemoryQuestionService(Arrays.asList(
                new Question(0, "0 + 1 = ", "1"))
        );
        questionService.init();
        InputUIServiceImpl input = mock(InputUIServiceImpl.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true);
        when(input.read()).thenReturn(Optional.of("1"));
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write(true);
    }

    @Test
    void shouldOkIfQuizPassAndPrintAllQuestionAndResultAnswerAndResult() throws SQLException {
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true);
        when(input.read()).thenReturn(Optional.of("1"), Optional.of("1"), Optional.of("1"));
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output).write("2 - 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output).write("1 * 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 3, 0));
        inOrder.verify(output).write("Тест пройден успешно!");
    }

    @Test
    void shouldOkIfQuizFailedAndPrintAllQuestionAndResultAnswerAndResult() throws SQLException {
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true);
        when(input.read()).thenReturn(Optional.of("1"), Optional.of("1"), Optional.of("2"));
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output).write("2 - 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output).write("1 * 1 = ");
        inOrder.verify(output).write(false);
        inOrder.verify(output)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 2, 1));
        inOrder.verify(output).write("Тест провален!");
    }

    @Test
    void shouldOkIfQuizEarlyFailedAndPrintAllQuestionAndResultAnswerAndResult() throws SQLException {
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true);
        when(input.read()).thenReturn(Optional.of("1"), Optional.of("1"), Optional.of("2"));
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write(true);
        inOrder.verify(output).write("2 - 1 = ");
        inOrder.verify(output).write(false);
        inOrder.verify(output)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 3, 2, 1));
        inOrder.verify(output).write("Тест провален!");
    }


    @Test
    void shouldPrintMessageAboutResultQuizIfEnterExit() throws SQLException {
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(false);
        when(input.read()).thenReturn(Optional.empty());
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 0, 0, 0));
        inOrder.verify(output).write("Тест провален!");
    }

    @Test
    void shouldPrintMessageAboutEmptyEnteringValueAndAnswerIfEnterAnswer() throws SQLException {
        questionService.init();
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true);
        when(input.read()).thenReturn(Optional.empty(),Optional.of("1"));
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write("Вы ввели пустое значение, ответьте на вопрос: ");
        inOrder.verify(output).write(true);
    }

    @Test
    void shouldPrintMessageAboutEmptyEnteringValueAndResultIfEnterExit() throws SQLException {
        questionService.init();
        InputUIService input = mock(InputUIService.class);
        OutputUIService output = mock(OutputUIService.class);
        QuizApplicationImpl quizApplication = new QuizApplicationImpl(questionService, input, output);
        when(input.hasNextLine()).thenReturn(true, false);
        when(input.read()).thenReturn(Optional.empty());
        quizApplication.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).write("0 + 1 = ");
        inOrder.verify(output).write("Вы ввели пустое значение, ответьте на вопрос: ");        inOrder.verify(output)
                .write(String.format("Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d", 0, 0, 0));
        inOrder.verify(output).write("Тест провален!");
    }
}
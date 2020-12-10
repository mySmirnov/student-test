package service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionServiceTest {

    @Test
    void setFileName() {

    }

    @Test
    void init() {
        QuestionService questionService = new CsvQuestionService("data.csv");
        questionService.init();
        questionService.setMaxNumberOfWrongAnswers(1);
        assertTrue(questionService.getQuestion(0).isPresent());
    }

    @Test
    void init_exception() {
        try {
            QuestionService questionService = new CsvQuestionService("data.csv");
            questionService.init();
            questionService.setMaxNumberOfWrongAnswers(1);
            assertTrue(questionService.getQuestion(0).isPresent());
        } catch (IllegalStateException exception) {
            String actual = exception.getMessage();
            String expected = "Exception file";
            assertEquals(expected, actual);
        }
    }


}
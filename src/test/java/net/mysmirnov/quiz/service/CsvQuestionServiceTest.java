package net.mysmirnov.quiz.service;

import net.mysmirnov.quiz.service.CsvQuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CsvQuestionServiceTest {

    @Test
    void shouldReturnQuestionsLoadedFromFile() {
        CsvQuestionService questionService = new CsvQuestionService("/data.csv");
        questionService.init();
        assertEquals("1 + 0 = ", questionService.getQuestion(0).get());
    }

    @Test
    void shouldReturnNoQuestionIfLoadedFileIsEmpty() {
        CsvQuestionService questionService = new CsvQuestionService("/dataEmpty.csv");
        questionService.init();
        assertFalse(questionService.getQuestion(0).isPresent());
    }

    @Test
    void shouldFailIfFileIsNotAvailable() {
        CsvQuestionService questionService = new CsvQuestionService("/doesNotExist.csv");
        Assertions.assertThrows(IllegalStateException.class, questionService::init);
    }
}
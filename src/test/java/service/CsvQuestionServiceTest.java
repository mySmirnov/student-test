package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionServiceTest {

    @Test
    void init_shouldReturnQuestionsLoadedFromFile() {
        CsvQuestionService questionService = new CsvQuestionService("/data.csv");
        questionService.init();
        assertEquals("1 + 0 = ", questionService.getQuestion(0).get());
    }

    @Test
    void shouldFailIfFileIsNotAvailable() {
        CsvQuestionService questionService = new CsvQuestionService("/doesNotExist.csv");
        Assertions.assertThrows(IllegalStateException.class, questionService::init);
    }
}
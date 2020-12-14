package net.mySmirnov.quiz;

import net.mySmirnov.quiz.QuizApplication;
import org.junit.jupiter.api.Test;
import net.mySmirnov.quiz.service.CsvQuestionService;

class QuizApplicationTest {
    CsvQuestionService questionService = new CsvQuestionService();
    QuizApplication quizApplication = new QuizApplication(questionService);

    @Test
    void run() {

    }
}
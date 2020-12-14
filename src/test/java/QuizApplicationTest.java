import org.junit.jupiter.api.Test;
import service.CsvQuestionService;
import service.InMemoryQuestionService;
import service.QuestionService;

import static org.junit.jupiter.api.Assertions.*;

class QuizApplicationTest {
    CsvQuestionService questionService = new CsvQuestionService();
    QuizApplication quizApplication = new QuizApplication(questionService) ;

    @Test
    void run() {

    }
}
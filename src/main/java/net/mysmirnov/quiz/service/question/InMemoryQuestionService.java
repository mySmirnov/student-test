package net.mysmirnov.quiz.service.question;

import net.mysmirnov.quiz.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class InMemoryQuestionService extends QuestionServiceImpl {
    private List<Question> questions = new ArrayList<>();

    public InMemoryQuestionService(List<Question> questions) {
        this.questions = questions;
    }

    public InMemoryQuestionService() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);

    public void init() {
        logger.info("initialisation");
        setQuestions(questions);
    }
}


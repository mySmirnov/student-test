package service;

import model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.Optional;

public class InMemoryQuestionService extends QuestionServiceImpl {
    List <Question> list = new ArrayList<>();

    public InMemoryQuestionService(List<Question> list) {
        this.list = list;
    }

    public InMemoryQuestionService() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);

    public void init() {
        logger.info("initialisation");
        setQuestions(list);
    }
}


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
        super();
        this.list = list;
    }

    public InMemoryQuestionService() {
        super();
    }

    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);

    public void init() {
        logger.info("initialisation");
//        list.add(new Question(0, "1 + 0 = ", "1"));
//        list.add(new Question(1, "2 - 1 = ", "1"));
//        list.add(new Question(2, "1 * 1 = ", "1"));
//        list.add(new Question(3, "2 в степени 0", "1"));
//        list.add(new Question(4, "Один за всех, и все за (укажите число)", "1"));
//        list.add(new Question(5, "(-1) * (-1) =", "1"));
//        list.add(new Question(6, "Два стула...", "1"));
//        list.add(new Question(7, "Один", "1"));
        setQuestions(list);
    }
}


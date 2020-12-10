package service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import model.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvQuestionService extends QuestionServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);
    private String fileName;

    public CsvQuestionService() {
        super();
    }

    public CsvQuestionService(String fileName) {
        super();
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void init() {
        List<Question> questions = new ArrayList<>();
        logger.info("initialisation");
        CsvToBean csv = new CsvToBean();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)))) {
            List list = csv.parse(setColumnMapping(), csvReader);
            for (Object object : list) {
                Question question = (Question) object;
                questions.add(question);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new IllegalStateException("Exception file", ex);
        }
        setQuestions(questions);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumnMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Question.class);
        String[] columns = new String[]{"id", "question", "answer"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}

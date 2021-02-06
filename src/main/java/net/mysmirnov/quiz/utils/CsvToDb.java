package net.mysmirnov.quiz.utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import net.mysmirnov.quiz.daoimpl.QuestionDaoImpl;
import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.service.question.CsvQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvToDb {
    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);
    private String resourceName;

    public CsvToDb(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void init() {
        CsvToBean csv = new CsvToBean();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(resourceName)))) {
            List list = csv.parse(setColumnMapping(), csvReader);
            QuestionDaoImpl questionDao = new QuestionDaoImpl();
            for (Object object : list) {
                Question question = (Question) object;
                System.out.println(question);
                questionDao.insert(question);
                System.out.println(question);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new IllegalStateException("Exception file", ex);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumnMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Question.class);
        String[] columns = new String[]{"id", "questionText", "answerText"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}

class main{
    private static boolean setUpIsDone = false;

    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }

    }

    public static void close() {
        if (setUpIsDone) {
            JdbcUtils.closeConnection();
        }
    }

    public static void main(String[] args) {
        CsvToDb csvToDb = new CsvToDb("/data.csv");
        setUp();
        csvToDb.init();
        close();
    }
}
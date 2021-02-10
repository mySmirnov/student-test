package net.mysmirnov.quiz.migration;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.dao.daoimpl.QuestionDaoImpl;
import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.service.question.CsvQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class CsvMigrationToDb {
    private static JdbcProvider jdbcProvider = new JdbcProvider(
            "root",
            "rei36djg",
            "jdbc:mysql://localhost:3306/quiz?useUnicode=yes&characterEncoding=UTF8&useSSL=false"
    );

    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionService.class);
    private String resourceName;

    public CsvMigrationToDb(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void init() {
        CsvToBean csv = new CsvToBean();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(resourceName)))) {
            List list = csv.parse(setColumnMapping(), csvReader);
            QuestionDaoImpl questionDao = new QuestionDaoImpl(jdbcProvider);
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

    public static void main(String[] args) throws SQLException {
        CsvMigrationToDb csvMigrationToDb = new CsvMigrationToDb("/data.csv");
        jdbcProvider.init();
        csvMigrationToDb.init();
        jdbcProvider.destroy();
    }
}
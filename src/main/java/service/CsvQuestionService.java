package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import model.Question;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import model.Question;

public class CsvQuestionService implements QuestionService {
    List<Question> questions = new ArrayList<Question>();
    int countTrue = 0;
    int countFalse = 0;
    int maxNumberOfWrongAnswers;


    public String askQuestion(int num) {
        if (countFalse >= maxNumberOfWrongAnswers) {
            return "break";
        } else {
            return questions.get(num).getQuestion();
        }
    }

    public int length() {
        return questions.size();
    }

    public String takeAnswer(int num, String answer) {
        if (questions.get(num).getAnswer().equals(answer)) {
            countTrue++;
            return "Верно!";
        } else {
            countFalse++;
            return "Неверно!";
        }
    }

    // 3. ответьить юзеру (выдать результат)
    public String reportResult() {
        if (countFalse <= maxNumberOfWrongAnswers) {
            return "У вас " + countTrue + " верных и " + countFalse + " не верных ответов" + "\n" +
                    "Опрос пройден успешно!";
        } else {
            return "============================================!!" + "\n" +
                    "Вы ответили на " + (countTrue + countFalse) +
                    " вопросов, верных ответов - " + countTrue +
                    ", неверных - " + countFalse + "\n" +
                    "Не унываейте следующий раз получиться лучше!";
        }
    }

    public void setMaxNumberOfWrongAnswers(int maxNumberOfWrongAnswers) {
        this.maxNumberOfWrongAnswers = maxNumberOfWrongAnswers;
    }


    {
        CsvToBean csv = new CsvToBean();
        String csvFilename = "data.csv";
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(csvFilename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Set column mapping strategy
        List list = csv.parse(setColumnMapping(), csvReader);
        for (Object object : list) {
            Question question = (Question) object;
            questions.add(question);
        }
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

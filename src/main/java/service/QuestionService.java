package service;

import model.Question;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    /**
     * @param num - номер вопроса
     * @return вопрос из списка по его номеру
     */
    Optional<String> getQuestion(int num);

    /**
     * @param num    - номер вопроса
     * @param answer - ответ пользователя на соответствующий вопрос
     * @return истенность ответа пользователя
     */
    boolean checkAnswer(int num, String answer);


    /**
     * @return общий результат тестирования
     */
    boolean resultAll();

    /**
     * @return результат тестирования
     */
    String report();

    /**
     * @return количество вопросов в тесте
     */
    int length();

    /**
     * инициализирует данные
     */
    void init();
    //-------------------------------------------------------------------------------------
    void setMaxNumberOfWrongAnswers(int maxNumberOfWrongAnswers);
//
//    void set();
}

package service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class QuestionServiceImpl implements QuestionService{
    Map<String,String> questions = new TreeMap<String, String>();

    public void saveResponse(String question, String response) {
        questions.put(question, response);
    }

    public Map<String, String> getQuestion() {
        return questions;
    }
}

//4. Добавить сервис хранения/получения вопросов ответов, они должны быть захардкожены в какую-то коллекцию
//
//        Использовать этот сервис чтобы задавать вопросы юзеру, получать его ответы и отвечать "правильный ответ или нет"
//
//interface QuestionService {
//}
//
//class InMemoryQuestionService implements QuestionService {
//....
//}

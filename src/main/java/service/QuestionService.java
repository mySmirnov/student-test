package service;

public interface QuestionService {
    // 1. Задать вопрос юзеру
    String askQuestion(int num);

    // 2. Принять ответ от юзера
    String takeAnswer(int num, String question);

    // 3. ответьить юзеру (выдать результат)
    String reportResult();
}

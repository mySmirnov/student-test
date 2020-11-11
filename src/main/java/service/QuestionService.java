package service;

public interface QuestionService {
    // 1. Задать вопрос юзеру
    String askQuestion(int num);

    // 2. Принять ответ от юзера
    String takeAnswer(int num, String question);

    // 3. ответьить юзеру (выдать результат)
    String reportResult();

    // [?] определение длинны списка вопросов (для сокрытия реализакии)
    // либо вернуть список или что то заранее подготовить либо что-то еще
    int length();
}

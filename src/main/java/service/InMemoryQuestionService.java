package service;

import model.Question;

import java.util.*;

public class InMemoryQuestionService implements QuestionService {

    Map<Integer, Question> questions = new HashMap<Integer, Question>();
    int countTrue = 0;
    int countFalse = 0;
    int maxNumberOfWrongAnswers;

    {
        questions.put(0, new Question("1 + 0 = ", "1"));
        questions.put(1, new Question("2 - 1 = ", "1"));
        questions.put(2, new Question("1 * 1 = ", "1"));
        questions.put(3, new Question("2 в степени 0", "1"));
        questions.put(4, new Question("Один за всех, и все за (укажите число)", "1"));
        questions.put(5, new Question("(-1) * (-1) =", "1"));
        questions.put(6, new Question("Два стула...", "1"));
        questions.put(7, new Question("Один", "1"));
    }

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
            return "У вас " + countTrue + " верных и " + countFalse + " не верных ответов" + "\n"+
                    "Опрос пройден успешно!";
        } else {
            return "============================================!!"+ "\n"+
                    "Вы ответили на " + (countTrue + countFalse) +
                    " вопросов, верных ответов - " + countTrue +
                    ", неверных - " + countFalse +"\n"+
                    "Не унываейте следующий раз получиться лучше!";
        }
    }

    public void setMaxNumberOfWrongAnswers(int maxNumberOfWrongAnswers) {
        this.maxNumberOfWrongAnswers = maxNumberOfWrongAnswers;
    }
}


package net.mysmirnov.quiz.service;

import net.mysmirnov.quiz.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class QuestionServiceImpl implements QuestionService {
    private List<Question> questions = new ArrayList<>();
    private int countOfCorrectAnswer;
    private int countOfWrongAnswer;
    private int maxNumberOfWrongAnswers;

    public QuestionServiceImpl() {
    }

    private void checkNum(int num) {
        if (num >= length()) {
            throw new IllegalArgumentException(String.format("Question with number=%d does not exist", num));
        }
    }

    public Optional<String> getQuestion(int num) {
        if (countOfWrongAnswer > maxNumberOfWrongAnswers || num >= length()) {
            return Optional.empty();
        } else {
            return Optional.of(questions.get(num).getQuestion());
        }
    }

    public boolean checkAnswer(int num, String answer) {
        checkNum(num);
        if (answer.equals(questions.get(num).getAnswer())) {
            countOfCorrectAnswer++;
            return true;
        } else {
            countOfWrongAnswer++;
            return false;
        }
    }

    public boolean resultAll() {
        return maxNumberOfWrongAnswers >= countOfWrongAnswer &&
                countOfCorrectAnswer + countOfWrongAnswer == length();
    }

    public String report() {
        return String.format(
                "Вы ответили на  %d вопросов, верных ответов - %d, неверных - %d",
                (countOfCorrectAnswer + countOfWrongAnswer), countOfCorrectAnswer, countOfWrongAnswer
        );
    }

    public void setMaxNumberOfWrongAnswers(int maxNumberOfWrongAnswers) {
        this.maxNumberOfWrongAnswers = maxNumberOfWrongAnswers;
    }

    public int length() {
        return questions.size();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}

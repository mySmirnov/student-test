package net.mysmirnov.quiz.model;

public class Answer {
    private int id;
    private int attemptId;
    private int questionId;
    private String answerText;

    public Answer(int id, int attemptId, int questionId, String answerText) {
        this.id = id;
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.answerText = answerText;
    }

    public Answer(int attemptId, int questionId, String answerText) {
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.answerText = answerText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", attemptId=" + attemptId +
                ", questionId=" + questionId +
                ", answerText='" + answerText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (getId() != answer.getId()) return false;
        if (getAttemptId() != answer.getAttemptId()) return false;
        if (getQuestionId() != answer.getQuestionId()) return false;
        return getAnswerText() != null ? getAnswerText().equals(answer.getAnswerText()) : answer.getAnswerText() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getAttemptId();
        result = 31 * result + getQuestionId();
        result = 31 * result + (getAnswerText() != null ? getAnswerText().hashCode() : 0);
        return result;
    }
}

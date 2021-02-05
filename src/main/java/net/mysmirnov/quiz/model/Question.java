package net.mysmirnov.quiz.model;

public class Question {
    private int id;
    private String questionText;
    private String answerText;

    public Question() {
    }

    public Question(int id, String questionText, String answerText) {
        this.id = id;
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public Question(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question [id=" + id +  ", question=" + questionText + ", answer=" + answerText + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (getId() != question.getId()) return false;
        if (getQuestionText() != null ? !getQuestionText().equals(question.getQuestionText()) : question.getQuestionText() != null)
            return false;
        return getAnswerText() != null ? getAnswerText().equals(question.getAnswerText()) : question.getAnswerText() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getQuestionText() != null ? getQuestionText().hashCode() : 0);
        result = 31 * result + (getAnswerText() != null ? getAnswerText().hashCode() : 0);
        return result;
    }
}

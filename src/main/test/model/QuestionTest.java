package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test
    void test() {
        Question question = new Question(0, "Вопрос", "Ответ");
        assertEquals(0, question.getId());
        assertEquals("Вопрос", question.getQuestion());
        assertEquals("Ответ", question.getAnswer());
        question.setId(1);
        question.setQuestion("Вопрос 1");
        question.setAnswer("Ответ 1");
        assertEquals(1, question.getId());
        assertEquals("Вопрос 1", question.getQuestion());
        assertEquals("Ответ 1", question.getAnswer());
    }
}
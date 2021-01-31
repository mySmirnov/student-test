package net.mysmirnov.quiz.dao;

import net.mysmirnov.quiz.model.Answer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AnswerDao {
    // create
    void insert(Answer answer) throws SQLException;

    // read
    Optional<Answer> getById(int id) throws SQLException;

    // update
    void update(Answer answer) throws SQLException;

    // delete
    void delete(int id) throws SQLException;

    void deleteAll() throws SQLException;
}

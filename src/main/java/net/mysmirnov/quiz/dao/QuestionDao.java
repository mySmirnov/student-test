package net.mysmirnov.quiz.dao;

import net.mysmirnov.quiz.model.Question;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface QuestionDao {
    // create
    void insert(Question question) throws SQLException;

    // read
    Optional<Question> findById(int id) throws SQLException;

    // TODO: 06.02.2021 сделал так что бы не добавлять в сущьность поля. Просто возвращаем все что есть
    List<Question> findAll() throws SQLException;

    // update
    void update(Question question) throws SQLException;

    // delete
    void delete(int id) throws SQLException;

    void deleteAll() throws SQLException;
}

package net.mysmirnov.quiz.dao;

import net.mysmirnov.quiz.model.Attempt;

import java.sql.SQLException;
import java.util.Optional;

public interface AttemptDao {
    // create
    void insert(Attempt attempt) throws SQLException;

    // read
    Optional <Attempt> findById(int id) throws SQLException;

    // update
    void update(Attempt attempt) throws SQLException;

    // delete
    void delete(int id) throws SQLException;

    void deleteAll() throws SQLException;
}

package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Attempt;
import net.mysmirnov.quiz.utils.JdbcUtils;

import java.sql.*;
import java.util.Optional;

public class AttemptDaoImpl implements AttemptDao {
    Connection con = JdbcUtils.getConnection();

    @Override
    public void insert(Attempt attempt) throws SQLException {
        String query = "INSERT INTO attempt(id, dates, rating) VALUES (?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setDate(2, attempt.getDate());
            stmt.setDouble(3, attempt.getResult());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    attempt.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating attempt failed, no ID obtained.");
                }
            }
        }

    }

    @Override
    public Optional<Attempt> getById(int id) throws SQLException {
        String query = "SELECT * FROM attempt WHERE id = " + id;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Date date = rs.getDate("dates");
                double rating = rs.getDouble("rating");
                return Optional.of(new Attempt(id, date, rating));
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Attempt attempt) throws SQLException {
        String query = "UPDATE attempt SET dates = ? , rating = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, attempt.getDate());
            stmt.setDouble(2, attempt.getResult());
            stmt.setInt(3, attempt.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM attempt WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM attempt";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }
}

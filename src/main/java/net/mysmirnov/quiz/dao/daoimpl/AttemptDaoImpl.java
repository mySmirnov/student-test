package net.mysmirnov.quiz.dao.daoimpl;

import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.model.Attempt;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class AttemptDaoImpl implements AttemptDao {
    private JdbcProvider jdbcProvider;
    private String queryInsert;
    private String queryFindById;
    private String queryUpdate;
    private String queryDelete;
    private String queryDeleteAll;

    public AttemptDaoImpl() {
    }

    public AttemptDaoImpl(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    public void setJdbcProvider(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    @Override
    public void insert(Attempt attempt) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "INSERT INTO attempt(id, dates, rating) VALUES (?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            stmt.setDouble(3, 0);
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
    public Optional<Attempt> findById(int id) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "SELECT * FROM attempt WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate("dates");
                double rating = rs.getDouble("rating");
                return Optional.of(new Attempt(id, date, rating));
            }
            return Optional.empty();
        }
    }

    @Override
    public void update(Attempt attempt) throws SQLException {
        Connection con = jdbcProvider.getConnection();
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
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM attempt WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM attempt";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public void setQueryInsert(String queryInsert) {
        this.queryInsert = queryInsert;
    }

    public void setQueryFindById(String queryFindById) {
        this.queryFindById = queryFindById;
    }

    public void setQueryUpdate(String queryUpdate) {
        this.queryUpdate = queryUpdate;
    }

    public void setQueryDelete(String queryDelete) {
        this.queryDelete = queryDelete;
    }

    public void setQueryDeleteAll(String queryDeleteAll) {
        this.queryDeleteAll = queryDeleteAll;
    }
}

package net.mysmirnov.quiz.dao.daoimpl;

import net.mysmirnov.quiz.dao.AnswerDao;
import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.model.Answer;

import java.sql.*;
import java.util.Optional;

public class AnswerDaoImpl implements AnswerDao {
    private JdbcProvider jdbcProvider;

    private String queryInsert;
    private String queryFindById;
    private String queryUpdate;
    private String queryDelete;
    private String queryDeleteAll;

    public AnswerDaoImpl() {
    }

    public AnswerDaoImpl(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    public void setJdbcProvider(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    @Override
    public void insert(Answer answer) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "INSERT INTO answer(id, attemptId, questionId, answerText) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(2, answer.getAttemptId());
            stmt.setInt(3, answer.getQuestionId());
            stmt.setString(4, answer.getAnswerText());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    answer.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating answer failed, no ID obtained.");
                }
            }
        }
    }


    @Override
    public Optional<Answer> findById(int id) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "SELECT * FROM answer WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int attemptId = rs.getInt("attemptId");
                int questionId = rs.getInt("questionId");
                String answerText = rs.getString("answerText");
                return Optional.of(new Answer(id, attemptId, questionId, answerText));
            }
            return Optional.empty();
        }
    }

    @Override
    public void update(Answer answer) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "UPDATE answer SET attemptId = ? , questionId = ? , answerText = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, answer.getAttemptId());
            stmt.setInt(2, answer.getQuestionId());
            stmt.setString(3, answer.getAnswerText());
            stmt.setInt(4, answer.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM answer WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM answer";
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

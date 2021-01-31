package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.AnswerDao;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.utils.JdbcUtils;

import java.sql.*;
import java.util.Optional;

public class AnswerDaoImpl implements AnswerDao {
    Connection con = JdbcUtils.getConnection();

    @Override
    public void insert(Answer answer) throws SQLException {
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


    // TODO: 24.01.2021 getAnswerById(int answerId) имя параметра метода??? и используемого внутри метода id
    @Override
    public Optional<Answer> getById(int answerId) throws SQLException {
        String query = "SELECT * FROM answer WHERE id = " + answerId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int id = rs.getInt("id");
                int attemptId = rs.getInt("attemptId");
                int questionId = rs.getInt("questionId");
                String answerText = rs.getString("answerText");
                return Optional.of(new Answer(id, attemptId, questionId, answerText));
            }
        }
        return Optional.empty();
    }


    @Override
    public void update(Answer answer) throws SQLException {
        String query = "UPDATE answer SET attemptId = ? , questionId = ? , answerText = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, answer.getAttemptId());
            stmt.setInt(2, answer.getQuestionId());
            stmt.setString(3, answer.getAnswerText());
            stmt.setInt(4, answer.getId());
            stmt.executeUpdate();
        }
    }

    // TODO: 26.01.2021 что обычно принимаю в качестве параметра Объект или ID
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM answer WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM answer";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }
}

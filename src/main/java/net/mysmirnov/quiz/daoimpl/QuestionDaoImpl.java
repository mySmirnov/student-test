package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.QuestionDao;
import net.mysmirnov.quiz.model.Question;
import net.mysmirnov.quiz.utils.JdbcUtils;

import java.sql.*;
import java.util.Optional;

public class QuestionDaoImpl implements QuestionDao {
    Connection con = JdbcUtils.getConnection();

    @Override
    public void insert(Question question) throws SQLException {
        String query = "INSERT INTO question(id, questionText, answerText) VALUES (?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2,question.getQuestionText());
            stmt.setString(3,question.getAnswerText());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating question failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public Optional<Question> findById(int id) throws SQLException {
        String query = "SELECT * FROM question WHERE id = " + id;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String questionText = rs.getString("questionText");
                String answerText = rs.getString("answerText");
                return Optional.of(new Question(id, questionText, answerText));
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Question question) throws SQLException {
        String query = "UPDATE question SET questionText = ? , answerText = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getAnswerText());
            stmt.setInt(3, question.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM question WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM question";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }


}

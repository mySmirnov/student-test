package net.mysmirnov.quiz.dao.daoimpl;

import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.dao.QuestionDao;
import net.mysmirnov.quiz.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoImpl implements QuestionDao {
    private JdbcProvider jdbcProvider;

    private String queryInsert;
    private String queryFindById;
    private String queryUpdate;
    private String queryDelete;
    private String queryDeleteAll;

    public QuestionDaoImpl() {
    }

    public QuestionDaoImpl(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    public void setJdbcProvider(JdbcProvider jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }


    @Override
    public void insert(Question question) throws SQLException {
        Connection con = jdbcProvider.getConnection();
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
        Connection con = jdbcProvider.getConnection();
        String query = "SELECT * FROM question WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setInt(1, id);
            // TODO: 08.02.2021 нужен ли отдельный try with resource
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String questionText = rs.getString("questionText");
                String answerText = rs.getString("answerText");
                return Optional.of(new Question(id, questionText, answerText));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Question> findAll() throws SQLException {
        Connection con = jdbcProvider.getConnection();
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM question";
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("questionText");
                String answerText = rs.getString("answerText");
                questions.add(new Question(id, questionText, answerText));
            }
        }
        return questions;
    }

    @Override
    public void update(Question question) throws SQLException {
        Connection con = jdbcProvider.getConnection();
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
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM question WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection con = jdbcProvider.getConnection();
        String query = "DELETE FROM question";
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

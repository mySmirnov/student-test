package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.JdbcProvider;
import net.mysmirnov.quiz.dao.daoimpl.AttemptDaoImpl;
import net.mysmirnov.quiz.model.Attempt;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AttemptDaoImplTest {
    private static JdbcProvider jdbcProvider = new JdbcProvider(
            "root",
            "rei36djg",
            "jdbc:mysql://localhost:3306/quiz?useUnicode=yes&characterEncoding=UTF8&useSSL=false"
    );
    private static AttemptDaoImpl attemptDao = new AttemptDaoImpl();
    private Attempt attempt = new Attempt();

    @BeforeAll
    public static void setUp() {
        attemptDao.setJdbcProvider(jdbcProvider);
        jdbcProvider.init();
    }

    @BeforeEach()
    public void clearDatabase() throws SQLException {
        attemptDao.deleteAll();
    }

    @AfterAll
    public static void close() throws SQLException {
        jdbcProvider.destroy();
    }


    @Test
    void insert() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.of(attempt), attemptFromDB);
    }

    @Test
    void update() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.of(attempt), attemptFromDB);
        attempt.setResult(2.00);
        attemptDao.update(attempt);
        Optional <Attempt>  attemptFromDbUpdate = attemptDao.findById(attempt.getId());
        assertEquals(attemptFromDB.get().getId(),attemptFromDbUpdate.get().getId());
        assertNotEquals(attemptFromDB,attemptFromDbUpdate);
    }

    @Test
    void delete() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.of(attempt), attemptFromDB);
        attemptDao.delete(attempt.getId());
        attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.empty(), attemptFromDB);
    }

    @Test
    void deleteAll() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.of(attempt), attemptFromDB);
        attemptDao.deleteAll();
        attemptFromDB = attemptDao.findById(attempt.getId());
        assertEquals(Optional.empty(), attemptFromDB);
    }
}
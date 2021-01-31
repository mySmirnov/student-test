package net.mysmirnov.quiz.daoimpl;

import net.mysmirnov.quiz.dao.AttemptDao;
import net.mysmirnov.quiz.model.Answer;
import net.mysmirnov.quiz.model.Attempt;
import net.mysmirnov.quiz.utils.JdbcUtils;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AttemptDaoImplTest {
    AttemptDaoImpl attemptDao = new AttemptDaoImpl();
    Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
    Attempt attempt = new Attempt(date, 1.00);

    private static boolean setUpIsDone = false;

    @BeforeAll
    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }

    }

    @BeforeEach()
    public void clearDatabase() throws SQLException {
        attemptDao.deleteAll();
    }

    @AfterClass
    public static void close() {
        if (setUpIsDone) {
            JdbcUtils.closeConnection();
        }
    }


    @Test
    void insert() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(attempt, attemptFromDB.get());
    }

    @Test
    void update() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(attempt, attemptFromDB.get());
        attempt.setResult(2.00);
        attemptDao.update(attempt);
        Optional <Attempt>  attemptFromDbUpdate = attemptDao.getById(attempt.getId());
        assertEquals(attemptFromDB.get().getId(),attemptFromDbUpdate.get().getId());
        assertNotEquals(attemptFromDB,attemptFromDbUpdate);
    }

    @Test
    void delete() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(attempt, attemptFromDB.get());
        attemptDao.delete(attempt.getId());
        attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(Optional.empty(), attemptFromDB);
    }

    @Test
    void deleteAll() throws SQLException {
        attemptDao.insert(attempt);
        Optional <Attempt> attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(attempt, attemptFromDB.get());
        attemptDao.deleteAll();
        attemptFromDB = attemptDao.getById(attempt.getId());
        assertEquals(Optional.empty(), attemptFromDB);
    }
}
package net.mysmirnov.quiz.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputUIServiceImplTest {

    private static InputStream defaultSystemIn = System.in;

    private InputUIServiceImpl inputUIService = new InputUIServiceImpl();

    @AfterEach
    void tearDown() {
        System.setIn(defaultSystemIn);
    }

    @Test
    void read() {
        setSystemIn("1");
        inputUIService.init();
        assertEquals(Optional.of("1"), inputUIService.read());
    }

    private void setSystemIn(String s) {
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
    }
}

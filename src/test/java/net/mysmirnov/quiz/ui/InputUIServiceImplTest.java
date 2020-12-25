package net.mysmirnov.quiz.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputUIServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void read() {
        InputUIServiceImpl inputUIService = new InputUIServiceImpl();
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("1", inputUIService.getInput());
    }




    @Test
    void hasNextLine() {
    }
}
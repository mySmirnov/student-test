package net.mysmirnov.quiz.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InputUIServiceImplTest {

    private static InputStream defaultSystemIn = System.in;

    private InputUIServiceImpl inputUIService = new InputUIServiceImpl();

    @AfterEach
    void tearDown() {
        System.setIn(defaultSystemIn);
    }

    @Test
    void shouldReturnLineIfEnterLine() {
        setSystemIn("1");
        inputUIService.init();
        assertEquals(Optional.of("1"), inputUIService.read());
    }

    @Test
    void shouldReturnSeveralLineIfEnterSeveralValue() {
        setSystemIn("1\n2\nsome Text\n ");
        inputUIService.init();
        assertEquals(Optional.of("1"), inputUIService.read());
        assertEquals(Optional.of("2"), inputUIService.read());
        assertEquals(Optional.of("some Text"), inputUIService.read());
        assertEquals(Optional.empty(), inputUIService.read());
    }

    @Test
    void shouldReturnEmptyIfEnterEmpty(){
        setSystemIn(" ");
        inputUIService.init();
        assertEquals(Optional.empty(), inputUIService.read());
    }

    @Test
    void shouldReturnEmptyIfEnterSeveralEmpty(){
        setSystemIn("\n      \n");
        inputUIService.init();
        assertEquals(Optional.empty(), inputUIService.read());
    }

    @Test
    void shouldReturnFalseIfEnterEmpty(){
        setSystemIn("");
        inputUIService.init();
        assertFalse(inputUIService.hasNextLine());
    }

    @Test
    void shouldReturnTrueIfEnterEmpty(){
        setSystemIn("SomeText");
        inputUIService.init();
        assertTrue(inputUIService.hasNextLine());
    }

    private void setSystemIn(String s) {
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
    }
}
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleWithOptionalStudyTest {
    @Test
    void shouldReturnLineIfEnterLine2() {
        String line = "cat dog cat dog";
        Optional<Integer> result = compute(line);
        assertEquals(Optional.of(4), result);
    }

    @Test
    void shouldReturnLineIfEnterLine3() {
        String line = "cat dog cat";
        Optional<Integer> result = compute(line);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void shouldReturnLineIfEnterLine0() {
        String line = null;
        Optional<Integer> result = compute(line);
        assertEquals(Optional.empty(), result);
    }


//    1. Если сервис вернул null,
//          код должен вернуть Optional.empty

//    2. Если сервис вернул не null,
//          то необходимо разбить строчку на слова и посчитать их количество (делается при помощи map)

    //    3. Если количество нечетное
//          вернуть Optional с этим значением, иначе вернуть Optional.empty (делается при помощи filter)
    private Optional<Integer> compute(String line) {
        return Optional.ofNullable(line).map(l -> line.split(" ").length).filter(l -> l % 2 == 0);
    }
}
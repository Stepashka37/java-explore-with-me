package ru.dimax.main;

import org.junit.jupiter.api.Test;
import ru.dimax.main.EwmMainService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EwmMainServiceTest {

    @Test
    void testMain() {
        assertDoesNotThrow(EwmMainService::new);
        assertDoesNotThrow(() -> EwmMainService.main(new String[]{}));
    }
}

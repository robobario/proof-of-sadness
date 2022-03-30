
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTest {

    @Test
    void test() throws InterruptedException {
        Thread.sleep(20 + Math.abs(new Random().nextLong() % 1000));
        assertTrue(true);
    }
}
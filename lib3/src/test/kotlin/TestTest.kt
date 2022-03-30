import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class TestTest {

    @Test
    fun test(){
        Thread.sleep(20 + Math.abs(Random().nextLong() % 1000))
        assertTrue(true)
    }
}
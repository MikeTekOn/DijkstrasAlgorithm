import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("libraryHealth should return 'true'", classUnderTest.someLibraryMethod());
    }
}

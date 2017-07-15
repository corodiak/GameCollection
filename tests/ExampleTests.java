import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jann-Niklas on 15.07.2017.
 */
public class ExampleTests {
    int test1;
    int test2;
    int test3;

    @Before
    public void setValues() {
        test1 = 3;
        test2 = 3;
        test3 = test1 + test2;
    }

    @Test
    public void firstTest() {
        Assert.assertTrue(test2 == test3-test1);
    }
}

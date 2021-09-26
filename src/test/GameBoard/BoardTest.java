package test.GameBoard;

import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.assertEquals;

public class BoardTest {

    private SetupTest st;

    public void setUp() {
        st = new SetupTest();
    }

    @Test
    public void test1() {
        assertEquals(st.t01.getCurrentLevel(), 0);
    }
}

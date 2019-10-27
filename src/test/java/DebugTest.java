import org.junit.Before;
import org.junit.Test;
import rsa.RSA;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DebugTest {
    private RSA rsa;

    @Before
    public void setUp() {
        rsa = new RSA(1024);
    }

    @Test
    public void testEncDecNum() {
        BigInteger input = new BigInteger("0");
        BigInteger output = rsa.encDecNum(input);
        assertEquals(output, input);
    }
}

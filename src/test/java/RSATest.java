import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rsa.RSA;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RSATest {
    private RSA rsa;
    private int bitLength;
    private String message, expected;

    @Parameterized.Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {1024, "a", "a"},
                {1024, "1", "1"},
                {1024, "a1b/?=).f", "a1b/?=).f"},
                {1024, "中", "中"},
                {1024, "中国", "中国"},
                {32, "abc", "abc"},
        });
    }

    public RSATest(int bitLen, String msg, String exp) {
        bitLength = bitLen;
        message = msg;
        expected = exp;
    }

    @Before
    public void setUp() {
        rsa = new RSA(bitLength);
    }

    @Test
    public void testEncryptDecrypt() {
        String enc = rsa.encrypt(message);
        String dec = rsa.decrypt(enc);
        assertEquals(expected, dec);
    }
}

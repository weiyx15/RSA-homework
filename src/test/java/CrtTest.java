import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CrtUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * test {@code utils.CrtUtils}
 */
@RunWith(Parameterized.class)
public class CrtTest {
    private BigInteger x;
    private BigInteger p;
    private BigInteger q;
    private BigInteger d;

    @Parameterized.Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {new BigInteger("19824397"), new BigInteger("9105594677247342700115742029706314687540855389438587709315300908081085613037782088452139752022667249347306218821492289242693799961696355510100405572741873"), new BigInteger("8704172974509462367389946461877532062465499768714539397718128523758214170979676305790770546024915636436746103082824394994564040271178566246130203204359471"), new BigInteger("4231777235")},
                {new BigInteger("38928596"), new BigInteger("6727277978661635257560903983593525958365726912972764767637222678944422933507453465007305584322648006043081787961241630254070418531349978756994704632187913"), new BigInteger("12989907654794101704608279459059746012876351609539646997879569539033938403040459392493791190603684502505941753982633711475555326801182410145305659625138739"), new BigInteger("1039412347")},
                {new BigInteger("25893679"), new BigInteger("12989907654794101704608279459059746012876351609539646997879569539033938403040459392493791190603684502505941753982633711475555326801182410145305659625138739"), new BigInteger("9155633932661643878394768869091200305769981981939819599964365686009382648081382001961898435495043928823663335910257056546558463771677276497372796951391703"), new BigInteger("2453875099")},
                {new BigInteger("13408080"), new BigInteger("8853893811480392880764813289315891926983605143557783042952213062258903638123814859738257072784132470037225452534799464063283417435943880729987820297564567"), new BigInteger("11524061292764253508180768397512502174818166902050913548647338427586008018449435794090944366068466368779378533061345375921907830144089092381380819625010599"), new BigInteger("2348958971")},
        });
    }

    public CrtTest(BigInteger x, BigInteger p, BigInteger q, BigInteger d) {
        this.x = x;
        this.p = p;
        this.q = q;
        this.d = d;
    }

    @Test
    /**
     * {@code CrtUtils.modPow} result validation
     */
    public void testModPow() {
        assertEquals(x.modPow(d, p.multiply(q)), CrtUtils.modPow(x, p, q, d));
    }

//    @Test
//    /**
//     * time comparision between {@code BigInteger.modPow} and {@code CrtUtils.modPow}
//     */
//    public void testModPowTime() {
//        int RUN_TIMES = 1000, i = 0;
//        long start = System.currentTimeMillis();
//        for (i=0; i<RUN_TIMES; ++i) {
//            x.modPow(d, p.multiply(q));
//        }
//        System.out.println("BigInteger.modPow: " + (System.currentTimeMillis() - start) + " millis");
//        start = System.currentTimeMillis();
//        for (i=0; i<RUN_TIMES; ++i) {
//            CrtUtils.modPow(x, p, q, d);
//        }
//        System.out.println("CrtUtils.modPow: " + (System.currentTimeMillis() - start) + " millis");
//    }
}

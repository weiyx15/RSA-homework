package utils;

import java.math.BigInteger;

/**
 * Math operations optimized by CRT
 * @Author weiyuxuan, weiyuxua19@mails.tsinghua.edu.cn
 * @Time 2019-10-27
 */
public class CrtUtils {
    /**
     * base ^ exp mod (p * q), p, q are primes
     * @param base
     * @param p
     * @param q
     * @param exp
     * @return
     */
    public static BigInteger modPow(BigInteger base, BigInteger p, BigInteger q, BigInteger exp) {
        BigInteger expP = exp.mod(p.subtract(BigInteger.ONE)),
                expQ = exp.mod(q.subtract(BigInteger.ONE)),
                resP = base.modPow(expP, p),
                resQ = base.modPow(expQ, q),
                revP = p.modInverse(q),
                revQ = q.modInverse(p);
        return resP.multiply(q).multiply(revQ).add(resQ.multiply(p).multiply(revP)).mod(p.multiply(q));
    }
}

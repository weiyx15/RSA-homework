package rsa;

import java.awt.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

import utils.StringUtils;

/**
 * RSA cipher system
 * @Author weiyuxuan, weiyuxua19@mails.tsinghua.edu.cn
 * @Time 2019-10-26
 */
public class RSA {
    private int bitLength = 1024;       // default bit length of n = 1024
    private Random rand = new Random(System.currentTimeMillis());   // random seed

    private BigInteger privateKey;      // d
    private BigInteger publicKey;       // e
    private BigInteger modulus;         // n
    private BigInteger phi;             // phi(n)
    private BigInteger primeP;          // p
    private BigInteger primeQ;          // q

    public RSA(int bitLength) {
        this.bitLength = bitLength;
        genKeyPair();
    }

    public void setBitLength(int bitLength) {
        this.bitLength = bitLength;
    }

    /**
     * generate p, q, n, phi(n)
     * p = randomPrime()
     * q = randomPrime()
     * n = p * q
     * phi(n) = (p - 1) * (q - 1)
     */
    private void genModulus() {
        do {
            primeP = BigInteger.probablePrime(bitLength / 2, rand);
            primeQ = BigInteger.probablePrime(bitLength / 2, rand);
        } while (primeP.equals(primeQ));
        modulus = primeQ.multiply(primeP);
        phi = primeQ.subtract(BigInteger.ONE).multiply(primeP.subtract(BigInteger.ONE));
    }

    /**
     * generate e
     * e = random(1, phi(n)) && gcd(e, phi(n)) == 1
     */
    private void genPublicKey() {
        do {
            publicKey = new BigInteger(bitLength/2, rand);
        } while (publicKey.compareTo(BigInteger.ONE) <= 0
                || publicKey.compareTo(phi) >= 0
                || publicKey.remainder(new BigInteger("2")).equals(BigInteger.ZERO)
                || !publicKey.gcd(phi).equals(BigInteger.ONE));
    }

    /**
     * generate d
     * d * e = 1 mod phi(n)
     */
    private void genPrivateKey() {
        privateKey = publicKey.modInverse(phi);
    }

    /**
     * generate and retrieve publicKey and privateKey
     * @return {"publicKey": publicKey in hex string, "privateKey": privateKey in hex string}
     */
    public HashMap<String, String> genKeyPair() {
        genModulus();
        genPublicKey();
        genPrivateKey();
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("publicKey", publicKey.toString(16));
        ret.put("privateKey", privateKey.toString(16));
        return ret;
    }

    /**
     * encrypt string
     * @param msg UTF-8 string message to be encrypted
     * @return encrypted hex string
     */
    public String encrypt(String msg) throws RuntimeException {
        try {
            msg = StringUtils.utf8ToHex(msg);
            return transformHexString(msg, publicKey, modulus, bitLength);
        } catch (ArithmeticException | NullPointerException e) {
            return e.getMessage();
        }
    }

    /**
     * decrypt string
     * @param scr hex string message to be decrypted
     * @return decrypted UTF-8 string
     */
    public String decrypt(String scr) {
        if (!StringUtils.checkHexString(scr)) {
            return "Input must be a hex string";
        }
        try {
            String hex = transformHexString(scr, privateKey, modulus, bitLength);
            return StringUtils.hexToUtf8(hex);
        } catch (ArithmeticException | NullPointerException e) {
            return e.getMessage();
        }
    }

    /**
     * for debug
     * @param puk publicKey
     * @param prk privateKey
     * @param mod modulus
     */
    public void setKeys(String puk, String prk, String mod) {
        publicKey = new BigInteger(puk);
        privateKey = new BigInteger(prk);
        modulus = new BigInteger(mod);
    }

    /**
     * for debug, test RSA algorithm only, not to deal with string encoding
     * BigInteger in, BigInteger out
     * @param input BigInteger to be encrypted
     * @return decrypted BigInteger
     */
    public BigInteger encDecNum(BigInteger input) {
        BigInteger tmp = input.modPow(publicKey, modulus);
        return tmp.modPow(privateKey, modulus);
    }

    /**
     * Common part of {@code encrypt} and {@code decrypt}, using hex string
     * @param src hex string to be parsed
     * @param key publicKey or privateKey
     * @param modu modulus
     * @param bitLength length of modulus, control the max bytes of input string
     * @return parsed hex string
     */
    private static String transformHexString(String src, BigInteger key, BigInteger modu, int bitLength) throws NullPointerException, ArithmeticException {
        if (src == null) {
            throw new NullPointerException("Message must not be null");
        }
        if (key == null || modu == null) {
            throw new NullPointerException("Keys not set");
        }
        BigInteger bint;
        try {
            bint = new BigInteger(src, 16);
        } catch (NumberFormatException e) {
            return "";
        }
        if (bint.compareTo(modu) >= 0) {
            throw new ArithmeticException("Bytes of String must be under " + bitLength/8);
        }
        bint = bint.modPow(key, modu);
        return bint.toString(16);
    }
}

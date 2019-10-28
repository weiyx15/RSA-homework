package utils;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.Arrays;

/**
 * a naive java implementation of unsigned big integer
 * @Author weiyuxuan, weiyuxua19@mails.tsinghua.edu.cn
 * @Time 2019-10-27
 */
public class BigInt implements Comparable<BigInt> {
    private static final int CHUNK = 1000000000;
    public static final BigInt ZERO = new BigInt(new int[0]);

    final int[] digits;

    /**
     * construct BigInt with string
     * @param s string representing radix-10 integer
     */
    public BigInt(String s) {
        int slen = s.length(), i = slen, j = 0;
        digits = new int[slen % 9 == 0? slen / 9: slen / 9 + 1];
        for (; i > 0; i -= 9) {
            digits[j++] = Integer.parseInt(s.substring(i < 9? 0: i - 9, i));
        }
    }

    /**
     * construct BigInt with int array
     * @param zdigits int array probably with leading zeros
     */
    public BigInt(int[] zdigits) {
        if (zdigits == null || zdigits.length == 0) {
            digits = new int[0];
            return;
        }
        int i = 0, zlen = zdigits.length;
        while (i < zlen) {
            if (zdigits[i] == 0) {
                ++i;
            } else {
                break;
            }
        }
        if (i == zlen) {
            digits = new int[0];
            return;
        }
        digits = new int[zlen - i];
        int j = 0;
        for (; i < zlen; ++i) {
            digits[j++] = zdigits[i];
        }
    }

    /**
     * {@code digits} = arr[low: high]
     * @param arr
     * @param high
     * @param low
     */
    public BigInt(int[] arr, int high, int low) {
        digits = Arrays.copyOfRange(arr, low, high + 1);
    }

    /**
     * copy constructor
     * @param other
     */
    public BigInt(BigInt other) {
        this.digits = Arrays.copyOf(other.digits, other.digits.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int dlen = digits.length, i = dlen - 1; i >= 0; --i) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }

    /**
     *
     * @param other
     * @return 1 if this > other; 0 if this = other; -1 if this < other
     */
    @Override
    public int compareTo(BigInt other) {
        int len = digits.length, olen = other.digits.length, i = len - 1, j = olen - 1;
        if (len > olen) {
            return 1;
        } else if (len < olen) {
            return -1;
        }
        while (i >= 0 && j >= 0) {
            if (digits[i] > digits[j]) {
                return 1;
            } else if (digits[i] < digits[j]) {
                return -1;
            } else {
                --i;
                --j;
            }
        }
        return 0;
    }

    public BigInt add(BigInt other) {
        int len = digits.length, olen = other.digits.length, i = 0, carry = 0, mlen = Math.min(len, olen);
        int[] retds = new int[Math.max(len, olen) + 1];
        for (i = 0; i < mlen; ++i) {
            retds[i] = digits[i] + other.digits[i] + carry;
            if (retds[i] >= CHUNK) {
                carry = 1;
                retds[i] -= CHUNK;
            }
        }
        for (; i < len; ++i) {
            retds[i] = digits[i] + carry;
            if (retds[i] >= CHUNK) {
                carry = 1;
                retds[i] -= CHUNK;
            }
        }
        for (; i < olen; ++i) {
            retds[i] = other.digits[i] + carry;
            if (retds[i] >= CHUNK) {
                carry = 1;
                retds[i] -= CHUNK;
            }
        }
        return new BigInt(retds);
    }

    /**
     * must ensure that this.compareTo(other) >= 0
     * @param other
     * @return
     */
    public BigInt subtract(BigInt other) throws ArithmeticException {
        if (compareTo(other) < 0) {
            throw new ArithmeticException("a - b requires a >= b");
        }
        if (compareTo(other) == 0) {
            return ZERO;
        }
        int len = digits.length, olen = other.digits.length, i = 0, carry = 0;
        int[] retds = new int[len];
        for (i = 0; i < olen; ++i) {
            retds[i] = digits[i] - other.digits[i] - carry;
            if (retds[i] < 0) {
                carry = 1;
                retds[i] += CHUNK;
            }
        }
        for (; i < len; ++i) {
            retds[i] = digits[i] - carry;
            if (retds[i] < 0) {
                carry = 1;
                retds[i] += CHUNK;
            }
        }
        return new BigInt(retds);
    }

    public BigInt multiply(BigInt other) {
        int len = digits.length, olen = other.digits.length, i = 0, j = 0;
        int[] retds = new int[len + olen];
        for (i = 0; i < len; ++i) {
            for (j = 0; j < olen; ++j) {
                retds[i + j] += digits[i] * other.digits[j];
                if (retds[i + j] >= CHUNK) {
                    retds[i + j + 1] += retds[i + j] / CHUNK;
                    retds[i + j] %= retds[i + j] / CHUNK;
                }
            }
        }
        return new BigInt(retds);
    }

    public BigInt leftShift(int shifts) {
        int len = digits.length, i = 0;
        int[] retds = new int[len + shifts];
        for (i = 0; i < len; ++i) {
            retds[i + shifts] = digits[i];
        }
        return new BigInt(retds);
    }

    public BigInt divide(BigInt other) throws ArithmeticException {
        if (other.compareTo(ZERO) == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        if (compareTo(other) < 0) {
            return ZERO;
        }
        int len = digits.length, olen = other.digits.length, i = 0, times = 0;
        int[] retds = new int[len - olen + 1];
        BigInt tmpBigInt = new BigInt(digits, len - olen, len);
        for (i = len - olen; i >= 0; --i) {
            if (tmpBigInt.compareTo(other) < 0) {
                continue;
            }
            tmpBigInt = tmpBigInt.leftShift(1);
            tmpBigInt.digits[0] = digits[i];
            long dividedFirst = (long) tmpBigInt.digits[tmpBigInt.digits.length - 1],
                    dividorFirst = (long) other.digits[other.digits.length - 1];
            if (dividedFirst < dividorFirst) {
                long diviedSecond = (long) tmpBigInt.digits[tmpBigInt.digits.length - 2];
                times = (int)(((dividedFirst * (long) CHUNK) + diviedSecond) / dividorFirst);
            } else {
                times = (int) (dividedFirst / dividorFirst);
            }
            retds[i] = times;
            for (int j = 0; j < times; ++j) {
                tmpBigInt = tmpBigInt.subtract(other);
            }
        }
        return new BigInt(retds);
    }

    public BigInt mod(BigInt other) {
        if (other.compareTo(ZERO) == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        if (compareTo(other) < 0) {
            return new BigInt(this);
        }
        int len = digits.length, olen = other.digits.length, i = 0, times = 0;
        BigInt tmpBigInt = new BigInt(digits, len - olen, len);
        for (i = len - olen; i >= 0; --i) {
            if (tmpBigInt.compareTo(other) < 0) {
                continue;
            }
            tmpBigInt = tmpBigInt.leftShift(1);
            tmpBigInt.digits[0] = digits[i];
            long dividedFirst = (long) tmpBigInt.digits[tmpBigInt.digits.length - 1],
                    dividorFirst = (long) other.digits[other.digits.length - 1];
            if (dividedFirst < dividorFirst) {
                long diviedSecond = (long) tmpBigInt.digits[tmpBigInt.digits.length - 2];
                times = (int)(((dividedFirst * (long) CHUNK) + diviedSecond) / dividorFirst);
            } else {
                times = (int) (dividedFirst / dividorFirst);
            }
            for (int j = 0; j < times; ++j) {
                tmpBigInt = tmpBigInt.subtract(other);
            }
        }
        return tmpBigInt;
    }
}

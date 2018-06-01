package edu.mweis.main.data;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimeSystemInt implements Comparable<PrimeSystemInt> {

    private static final int[] PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
            107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
            167, 173, 179, 181, 191, 193, 197, 199
    };

    private final BigInteger value;
    private final int size;
    private final BigInteger[] digits; // where 0 is LSB
    private final BigInteger[] reversedDigits; // todo remove

    public PrimeSystemInt(BigInteger value) {
        this.value = value;

        final List<BigInteger> digitValues = new ArrayList<>();
        BigInteger tmp = value;
        int counter = 1;
        /**
         * Start at PRIMES[0] for radixs: 1, 3, 5, ...
         * Start at PRIMES[1] for radixs: 3, 5, ... (in other words ignore the "1" bit case at end)
         *  => however calculating value in expanded notation still uses 1, 3, 5, ...
         *  => ex: 35 = [1, 5, 2] = 1 * 5 + 5 * 3 + 2 * 1 =
         */

        do {
            final BigInteger[] ret = tmp.divideAndRemainder(BigInteger.valueOf(PRIMES[counter]));
            tmp = ret[0];
            digitValues.add(ret[1]);
            counter++;
            if (counter == PRIMES.length) {
                throw new IllegalArgumentException("TOO BIG xd");
            }
        } while (!tmp.equals(BigInteger.ZERO));

        this.size = counter-1;
        this.digits = new BigInteger[size]; // "exact" means throw exception if too large
        final BigInteger[] ret = digitValues.toArray(digits);

        Collections.reverse(digitValues);
        this.reversedDigits = new BigInteger[size];
        digitValues.toArray(reversedDigits);

        assert (Arrays.deepEquals(ret, digits));
        // if assertion never thrown, can replace with "digitValues.toArray(digits);"
    }

    public BigInteger getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public BigInteger getDigit(int index) {
        return digits[index];
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("size", size)
                .add("rdigits", reversedDigits)
                .toString();
    }

    public String toShortString() {
        return Arrays.toString(reversedDigits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimeSystemInt that = (PrimeSystemInt) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(PrimeSystemInt o) {
        if (o != null) {
            return value.compareTo(o.value);
        }
        return 1;
    }
}

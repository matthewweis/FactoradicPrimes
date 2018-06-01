package edu.mweis.main.data;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DoubleFacInt implements Comparable<DoubleFacInt> {

    private final BigInteger value;
    private final int size;
    private final BigInteger[] digits; // where 0 is LSB
    private final BigInteger[] reversedDigits; // todo remove

    public DoubleFacInt(BigInteger value) {

        this.value = value;

        final List<BigInteger> digitValues = new ArrayList<>();
        BigInteger tmp = value;
        final BigInteger two = BigInteger.valueOf(2);
        BigInteger radix = BigInteger.valueOf(3);
        int counter = 0;

        do {
            final BigInteger[] ret = tmp.divideAndRemainder(radix);
            tmp = ret[0];
            digitValues.add(ret[1]);
            radix = radix.multiply(radix.add(two));
            counter++;
        } while (!tmp.equals(BigInteger.ZERO));

        this.size = counter;
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
        DoubleFacInt that = (DoubleFacInt) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(DoubleFacInt o) {
        if (o != null) {
            return value.compareTo(o.value);
        }
        return 1;
    }
}

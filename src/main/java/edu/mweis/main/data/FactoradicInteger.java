package edu.mweis.main.data;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoradicInteger {

    private final BigInteger value;
    private final int size;
    private final BigInteger[] digits; // where 0 is LSB

    public FactoradicInteger(BigInteger value) {
        this.value = value;

        final List<BigInteger> digitValues = new ArrayList<>();
        BigInteger tmp = value;
        BigInteger radix = BigInteger.valueOf(2);

        do {
            final BigInteger[] ret = tmp.divideAndRemainder(radix);
            tmp = ret[0];
            digitValues.add(ret[1]);
            radix = radix.add(BigInteger.ONE);
        } while (!tmp.equals(BigInteger.ZERO));

        this.size = radix.intValueExact() - 2; // subtract 2 (since we started at 2)
        this.digits = new BigInteger[size]; // "exact" means throw exception if too large
        final BigInteger[] ret = digitValues.toArray(digits);

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
                .add("digits", digits)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactoradicInteger that = (FactoradicInteger) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

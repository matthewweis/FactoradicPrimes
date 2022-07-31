package edu.mweis.main.data;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FactoradicInteger implements Comparable<FactoradicInteger> {

    private final BigInteger value;
    private final int numDigits;
    private final BigInteger[] digits; // where 0 is LSB
    private final BigInteger[] reversedDigits; // todo remove

    public FactoradicInteger(BigInteger value) {
        this.value = value;

        final List<BigInteger> digitValues = new ArrayList<>();
        BigInteger tmp = value;
        BigInteger radix = BigInteger.valueOf(2);
        int counter = 0;
        do {
            final BigInteger[] ret = tmp.divideAndRemainder(radix);
            tmp = ret[0];
            digitValues.add(ret[1]);
            radix = radix.add(BigInteger.ONE);
            counter++;
        } while (!tmp.equals(BigInteger.ZERO));

//        this.numDigits = radix.intValueExact() - 2; // subtract 2 (since we started at 2)
        this.numDigits = counter;
        this.digits = new BigInteger[numDigits]; // "exact" means throw exception if too large
        final BigInteger[] ret = digitValues.toArray(digits);

        Collections.reverse(digitValues);
        this.reversedDigits = new BigInteger[numDigits];
        digitValues.toArray(reversedDigits);

        assert (Arrays.deepEquals(ret, digits));
        // if assertion never thrown, can replace with "digitValues.toArray(digits);"
    }

    public BigInteger getValue() {
        return value;
    }

    public int getNumDigits() {
        return numDigits;
    }

    public BigInteger getDigit(int index) {
        return digits[index];
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("numDigits", numDigits)
                .add("rdigits", reversedDigits)
                .toString();
    }

    public String toShortString() {
        return Arrays.toString(reversedDigits);
    }

  public String toPrettyString() {
    return Stream.of(reversedDigits)
            .map(BigInteger::toString)
            .collect(Collectors.joining("! + ", "", "!"));
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

    @Override
    public int compareTo(FactoradicInteger o) {
        if (o != null) {
            return value.compareTo(o.value);
        }
        return 1;
    }
}

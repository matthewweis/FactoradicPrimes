package edu.mweis.main.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Factorial implements Comparable<Factorial> {

    private static int largestCachedValue = 0;
    private final static SortedSet<Factorial> cache = new TreeSet<>();
    static {
        cache.add(new Factorial());
    }

    private final BigInteger n;
    private final BigInteger value;

    private Factorial(int n) {
        this.n = BigInteger.valueOf(n);
        this.value = cache.last().value.multiply(this.n);

        largestCachedValue = n;
        assert (!cache.contains(this));
        cache.add(this);
    }

    private Factorial() {
        this.n = BigInteger.ONE;
        this.value = BigInteger.ONE;
        largestCachedValue = 1;
        assert (!cache.contains(this));
        cache.add(this);
    }

    public static Factorial of(int n) {
        if (n == 1) {
            return cache.first();
        } else if (largestCachedValue < n) {
            of(n-1);
            return new Factorial(n);
        } else if (largestCachedValue == n) {
            return cache.last();
        } else {
            return Iterators.get(cache.iterator(), n-1);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("n", n)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factorial factorial = (Factorial) o;
        return Objects.equal(n, factorial.n);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(n);
    }

    @Override
    public int compareTo(Factorial o) {
        return n.compareTo(o.n);
    }
}

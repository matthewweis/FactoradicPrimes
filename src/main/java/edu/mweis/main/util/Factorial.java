package edu.mweis.main.util;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Factorial implements Comparable<Factorial> {

    private static long largestCachedValue = 0L;
    private final static SortedSet<Factorial> cache = new TreeSet<>();
    static {
        cache.add(new Factorial());
    }

    private final BigInteger n;
    private final BigInteger value;

    private Factorial(long n) {
        this.n = BigInteger.valueOf(n);
        this.value = cache.last().value.multiply(this.n);

        largestCachedValue = n;
        assert (!cache.contains(this));
        cache.add(this);
    }

    private Factorial() {
        this.n = BigInteger.ONE;
        this.value = BigInteger.ONE;
        largestCachedValue = 1L;
        assert (!cache.contains(this));
        cache.add(this);
    }

    public static Factorial of(long n) {
        if (n == 1) {
            return cache.first();
        } else if (largestCachedValue < n) {
            of(n-1);
            return new Factorial(n);
        } else if (largestCachedValue == n) {
            return cache.last();
        } else {
            return Iterators.get(cache.iterator(), (int)n-1);
        }
    }

    @Override
    public String toString() {
        return "Factorial{" +
                "n=" + n +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Factorial)){
            return false;
        } else {
            return n.equals(((Factorial) o).n);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(n);
    }

    @Override
    public int compareTo(Factorial o) {
        return this.n.compareTo(o.n);
    }
}

package edu.mweis.main.util;

import com.google.common.base.Objects;
import com.google.common.collect.*;

import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public final class PrimeFactorization implements Comparable<PrimeFactorization> {

    private static long largestCachedValue = 0L;
    private final static SortedSet<PrimeFactorization> cache = new TreeSet<>();
    static {
        cache.add(new PrimeFactorization());
    }

    private final long n;
    private final ImmutableSortedMultiset<BigInteger> factors;

    /**
     * see: https://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/
     *
     * THIS CONSTRUCTOR IS CALLED IF AND ONLY IF IT DOESN'T YET EXIST IN THE CACHE.
     * case of n > 1
     *
     * @param n the value whose prime factorization is to be computed. note that values should be relatively small
     *          (don't let the type long imply a large n value, as this type is done to make converting from long
     *          to {@link BigInteger} easier). Anything above 35! would be more than enough to change the world.
     */
    private PrimeFactorization(long n) {
        assert (n > 2);
        assert (n < 2000); // anything above 35! would change the the world. be smart.

        this.n = n;

        final SortedMultiset<BigInteger> tmp = TreeMultiset.create();

//        cache.iterator().forEachRemaining(primeFactorization -> tmp.addAll(primeFactorization.getFactors()));

        while (n % 2L == 0L) {
            tmp.add(BigInteger.valueOf(2L));
            n /= 2L;
        }

        for (long i = 3L; i <= Math.sqrt(n); i += 2L) {
            while (n % i == 0L)
            {
                tmp.add(BigInteger.valueOf(i));
                n /= i;
            }
        }

        if (n > 2L) {
            tmp.add(BigInteger.valueOf(n));
        }

        this.factors = ImmutableSortedMultiset.copyOf(tmp);
        largestCachedValue = this.n;
        assert (!cache.contains(this));
        cache.add(this);
    }

    /**
     * case of n == 1
     */
    private PrimeFactorization() {
        this.n = 1L;
        this.factors = ImmutableSortedMultiset.of(BigInteger.ONE);
        largestCachedValue = this.n;
        assert (!cache.contains(this));
        cache.add(this);
    }

    /**
     * Gets the cached prime factorization, or creates l...n factorizations and returns newly created maximal factorization
     * @param n the number to get
     * @return the PrimeFactorization representing this value n
     */
    public static PrimeFactorization of(long n) {
        if (n == 1) {
            return cache.first();
        } else if (largestCachedValue < n) {
            of(n-1); // recursively create all required factorizations
            return new PrimeFactorization(n); // return desired factorization
        } else if (largestCachedValue == n) {
            return cache.last();
        } else {
            return Iterators.get(cache.iterator(), (int)n-1);
        }
    }

    /**
     * DO NOT USE EQUALS ON THE RETURN VALUE OF THIS METHOD.
     * (see https://google.github.io/guava/releases/22.0/api/docs/com/google/common/collect/TreeMultiset.html)
     * @return an immutable sorted multiset of all prime factors and their occurrences. DO NOT .EQUALS() THIS VALUE.
     */
    public ImmutableSortedMultiset<BigInteger> getFactors() {
        return factors;
    }

    @Override
    public String toString() {
        return "PrimeFactorization{" +
                "n=" + n +
                ", factors=" + factors.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof PrimeFactorization)){
            return false;
        } else {
            return n == ((PrimeFactorization) o).n;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(n);
    }

    public int compareTo(PrimeFactorization o) {
        return Long.compare(n, o.n);
    }
}

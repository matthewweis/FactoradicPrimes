package edu.mweis.main;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import edu.mweis.main.data.FactoradicInteger;
import edu.mweis.main.data.Factorial;
import edu.mweis.main.data.PrimeFactorization;
import edu.mweis.main.data.PrimeSystemInt;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

public class BigPrimeTest {

    /**
     * determines the primality of a potentialPrime using the factorization technique
     * @param potentialPrime the integer whose prime value is to be determined (must be greater than 3)
     * @return the primality of potentialPrime
     */
    public static boolean isPrime(BigInteger potentialPrime) {

        if (potentialPrime.mod(BigInteger.valueOf(2L)).equals(BigInteger.ZERO)) {
            return potentialPrime.equals(BigInteger.valueOf(2L));
        }

        /**
          1) Decompose potentialPrime into factoradic form
         */
        final FactoradicInteger factoradic = new FactoradicInteger(potentialPrime);

        /**
          2) Iterate from low to high over all non-zero place values whose position is in the range [3, n]
         */

        BigInteger lastSum = factoradic.getDigit(0).multiply(Factorial.of(1).getValue()); // for 2.a.opt1
        final Set<BigInteger> cumFactors = new TreeSet<>(PrimeFactorization.of(1).getFactors()); // for 2.b.opt2
//        final int sum = 1 + (((factoradic.getNumDigits() + 2) * (factoradic.getNumDigits() + 3)) / 1);
//        final int sum = (int) Math.ceil(Math.sqrt(potentialPrime.intValueExact()));

        for (int i = 1; i < factoradic.getNumDigits(); i++) {

            /**
              2.a) Sum all values less than j, where j < i, and place(j) has at least 1 less distinct element (not count)

                  OPTIMIZATION 1: adding to previous value instead of recomputing all
                  OPTIMIZATION 2: skip any places where digit equals 0
             */

            cumFactors.addAll(PrimeFactorization.of(i+1).getFactors()); // for 2.b.opt2

//            if (factoradic.getDigit(i).equals(BigInteger.ZERO)) { // for 2.a.opt2
//                continue;
//            }

            final BigInteger cumSum = lastSum.add(factoradic.getDigit(i).multiply(Factorial.of(i+1).getValue()));

//            System.out.println(cumSum + ", " + potentialPrime.subtract(cumSum));

            if (!cumSum.equals(potentialPrime) && !cumSum.equals(BigInteger.ZERO)) {
                if (!cumSum.gcd(potentialPrime.subtract(cumSum)).equals(BigInteger.ONE)) {
                    return false;
                }
            }


            /**
              2.b) Return "composite" if cumSum divides any factor of i!

                  OPTIMIZATION 1: no need to check past place(i), as place(i) is a subset of all place(j) where j > i
                  OPTIMIZATION 2: keep track of previous Factors
             */

            final Predicate<BigInteger> predicate = factor -> cumSum.mod(factor).equals(BigInteger.ZERO);

            if (Iterators.any(cumFactors.iterator(), predicate::test)) {
                return false;
            }


//            if (diff.intValueExact() > 0 && Sets.intersection(
//                    ImmutableSortedSet.copyOf(PrimeFactorization.of(cumSum.intValueExact()).getFactors()),
//                    ImmutableSortedSet.copyOf(PrimeFactorization.of(diff.intValueExact()).getFactors()))
//                    .isEmpty()) {
//                return false;
//            }
//            System.out.format("potPrime: %d : cumSum: %d : diff: %d\n", potentialPrime.intValueExact(), cumSum, diff);

//            Iterator<BigInteger> iterator = cumFactors.iterator();
//            while (iterator.hasNext()) {
//                final BigInteger next = iterator.next();
//                //System.out.printf("does %s mod %s equal 0?  ", cumSum.toString(), next);
//                if (cumSum.mod(next).equals(BigInteger.ZERO)) {
//                    //System.out.println("YES");
//                    return false;
//                }
//                //System.out.println("NO");
//            }

            lastSum = cumSum; // for 2.a.opt1
        }

        /**
          3) Otherwise, return "Prime"
         */

        // tests all pseudoprimes, except those that are also square numbers
//        if (PrimeFactorization.of(Factorial.of(sum)).stream()
//                .anyMatch(bigInteger -> potentialPrime.mod(bigInteger).equals(BigInteger.ZERO))) {
//
//            return false;
//        }

//        for (int j=1; j <= cumFactors.size(); j++) {
//            for (Set<BigInteger> s1 : Sets.combinations(cumFactors, j)) {
//                for (Set<BigInteger> s2 : Sets.combinations(cumFactors, j)) {
//                    if (Sets.intersection(s1, s2).isEmpty()) {
//                        final int sumOfSet1 = s1.stream().mapToInt(BigInteger::intValueExact).sum();
//                        final int sumOfSet2 = s2.stream().mapToInt(BigInteger::intValueExact).sum();
//                        ImmutableSortedSet.copyOf(PrimeFactorization.of(sumOfSet1).getFactors());
//
//                        if (!Sets.intersection(
//                                ImmutableSortedSet.copyOf(PrimeFactorization.of(sumOfSet1).getFactors()),
//                                ImmutableSortedSet.copyOf(PrimeFactorization.of(sumOfSet2).getFactors()))
//                                .isEmpty()) {
//
//                            return false;
//                        }
//                    }
//                }
//            }
//        }


        return true;
    }

    public static void main(String[] args) {
//        for (int i=3; i < 100; i++) {
//            System.out.println(new PrimeSystemInt(BigInteger.valueOf(i)));
//        }

//        System.out.println(new PrimeSystemInt(BigInteger.valueOf(25)));
//        System.out.println(new PrimeSystemInt(BigInteger.valueOf(25)));

        for (int i=3; i < 300; i++) {
            System.out.println(new PrimeSystemInt(BigInteger.valueOf(i)));
        }

//
//        p(Multisets.union(PrimeFactorization.of(2).getFactors(), PrimeFactorization.of(3).getFactors()).toString());
//
//        for (int i=2; i < 20; i++) {
//            p(Factorial.of(i).toString());
//        }
//
//        try (Scanner scanner = new Scanner(System.in)) {
//            while (scanner.hasNextBigInteger()) {
//                System.out.println(BigPrimeTest.isPrime(scanner.nextBigInteger()));
//            }
//        }

    }
}

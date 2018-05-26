package edu.mweis.main;

import com.google.common.collect.Iterators;
import edu.mweis.main.data.FactoradicInteger;
import edu.mweis.main.data.Factorial;
import edu.mweis.main.data.PrimeFactorization;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class BigPrimeTest {

    private BigPrimeTest() { }

    /**
     * determines the primality of a potentialPrime using the factorization technique
     * @param potentialPrime the integer whose prime value is to be determined
     * @return the primality of potentialPrime
     */
    public boolean isPrime(BigInteger potentialPrime) {

        /**
          1) Decompose potentialPrime into factoradic form
         */
        final FactoradicInteger factoradic = new FactoradicInteger(potentialPrime);
//        System.out.println(factoradic);

        /**
          2) Iterate from low to high over all non-zero place values whose position is in the range [3, n]
         */

        BigInteger lastSum = factoradic.getDigit(0).multiply(Factorial.of(1).getValue()); // for 2.a.opt
        final Set<BigInteger> cumFactors = new TreeSet<>(PrimeFactorization.of(1).getFactors()); // for 2.b.opt2

        for (int i=1; i < factoradic.getSize(); i++) {

            /**
              2.a) Sum all values less than i

                  OPTIMIZATION: adding to previous value instead of recomputing all
             */

            final BigInteger cumSum = lastSum.add(factoradic.getDigit(i).multiply(Factorial.of(i+1).getValue()));
            cumFactors.addAll(PrimeFactorization.of(i+1).getFactors()); // for 2.b.opt2

            /**
              2.b) Return "composite" if cumSum divides any factor of i!

                  OPTIMIZATION 1: no need to check past place(i), as place(i) is a subset of all place(j) where j > i
                  OPTIMIZATION 2: keep track of previous Factors
             */

            final Predicate<BigInteger> predicate = factor -> cumSum.mod(factor).equals(BigInteger.ZERO);
//            System.out.println(cumFactors.toString());
            if (Iterators.any(cumFactors.iterator(), predicate::test)) {
                return false;
            }

            lastSum = cumSum; // for 2.a.opt
        }

        /**
          3) Otherwise, return "Prime"
         */
        return true;
    }

    public static void main(String[] args) {
//        for (int i=1; i < 20; i++) {
//            System.out.println(PrimeFactorization.of(i).toString());
//        }
//
//        p(Multisets.union(PrimeFactorization.of(2).getFactors(), PrimeFactorization.of(3).getFactors()).toString());
//
//        for (int i=2; i < 20; i++) {
//            p(Factorial.of(i).toString());
//        }

        try (Scanner scanner = new Scanner(System.in)) {
            final BigPrimeTest bigPrimeTest = new BigPrimeTest();

            while (scanner.hasNextBigInteger()) {
                System.out.println(bigPrimeTest.isPrime(scanner.nextBigInteger()));
            }
        }

    }
}

package edu.mweis.main;

import com.google.common.collect.Multisets;
import edu.mweis.main.util.FactoradicInteger;
import edu.mweis.main.util.Factorial;
import edu.mweis.main.util.PrimeFactorization;

import java.math.BigInteger;
import java.util.Scanner;

public class BigPrimeTest {

    private BigPrimeTest() { }

    /**
     * determines the primality of a potentialPrime using the factorization technique
     * @param potentialPrime the integer whose prime value is to be determined
     * @return the primality of potentialPrime
     */
    public boolean isPrime(BigInteger potentialPrime) {

        final FactoradicInteger factoradic = new FactoradicInteger(potentialPrime);
        System.out.println(factoradic);

        return false;
    }

    public static void main(String[] args) {
//        for (int i=2; i < 20; i++) {
//            p(PrimeFactorization.of(i).toString());
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

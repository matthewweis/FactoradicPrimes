package edu.mweis.main;

import com.google.common.base.Strings;
import edu.mweis.main.data.FactoradicInteger;
import edu.mweis.main.data.PrimeFactorization;
import edu.mweis.main.util.TestSieve;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class BigPrimeTestTest {

    private final static int NUM_PRIMES_TO_TEST = 2000;//200000;

    @org.junit.Test
    public void isPrime() {

        final TestSieve testSieve = new TestSieve(NUM_PRIMES_TO_TEST);

        for (int n=3; n <= testSieve.getLimit(); n++) {

            if (testSieve.isPrime(n) != BigPrimeTest.isPrime(BigInteger.valueOf(n))) {
                System.out.print(n + ", ");
//                final FactoradicInteger f = new FactoradicInteger(BigInteger.valueOf(n));
//                System.out.println(f + ", " + PrimeFactorization.of(n));
//
//                Set<FactoradicInteger> s = new TreeSet<>();
//                for (int i=1; i <= f.getSize(); i++) {
//                    PrimeFactorization.of(i).getFactors().forEach(a -> s.add(new FactoradicInteger(a)));
//                }
//                System.out.println(s.toString() +"\n");
//
////                System.out.println(Strings.padStart(Integer.toString(n, 10), 10, ' '));
//                System.out.println(Strings.padStart(Integer.toString(n, 5), 15, ' '));
//                System.out.println(Strings.padStart(Integer.toString(n, 3), 15, ' '));
//                System.out.println(Strings.padStart(Integer.toString(n, 2), 15, ' '));

                // ideas: look at them in base 6 -or- look at base of all factors transformed some how (xor?)

//                System.out.println(Integer.toString(n, 6) + "  (base 6)");
//                PrimeFactorization.of(i).getFactors();

            }
            //assertEquals(testSieve.isPrime(n), BigPrimeTest.isPrime(BigInteger.valueOf(n)));
        }

    }
}
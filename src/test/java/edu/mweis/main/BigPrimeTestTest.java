package edu.mweis.main;

import com.google.common.base.Strings;
import edu.mweis.main.data.DoubleFacInt;
import edu.mweis.main.data.FactoradicInteger;
import edu.mweis.main.data.PrimeFactorization;
import edu.mweis.main.data.PrimeSystemInt;
import edu.mweis.main.util.TestSieve;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class BigPrimeTestTest {

//    private final static int NUM_PRIMES_TO_TEST = 7919;//200000;
private final static int NUM_PRIMES_TO_TEST = 100000000;//200000;

    // see: https://gist.githubusercontent.com/cblanc/46ebbba6f42f61e60666/raw/8306166ecb892cedb0555f87eaa4f5f7e02980de/gistfile1.txt

    @org.junit.Test
    public void isPrime() {
//        PrintStream out = null;
//        try {
//            out = new PrintStream(new FileOutputStream("generated.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.setOut(out);

        final TestSieve testSieve = new TestSieve(NUM_PRIMES_TO_TEST);

//        for (int n=3; n < testSieve.getLimit(); n += 2) {
//            final BigInteger potPrime = BigInteger.valueOf(n);
////            System.out.format("%d: %s (actual %s)\n", n, BigPrimeTest.isPrime(potentialPrime), testSieve.isPrime(n));
//            if (testSieve.isPrime(n)) {
//                System.out.format("prime %d = %s\n", n, new DoubleFacInt(potPrime).toShortString());
//            }
//
//        }
//
//        System.out.println();
//
//        for (int n=3; n < testSieve.getLimit(); n += 2) {
//            final BigInteger potPrime = BigInteger.valueOf(n);
//            if (!testSieve.isPrime(n)) {
//                final String s = new DoubleFacInt(potPrime).toShortString();
//                System.out.format("non-prime %d = %s\n", n, s);
//            }
//        }


        for (int n=3; n <= NUM_PRIMES_TO_TEST; n ++) {
//            if (!testSieve.isPrime(n)) {
//                continue;
//            }
            final BigInteger potPrime = BigInteger.valueOf(n);

            final String s = new FactoradicInteger(potPrime).toShortString();
            final String sPad = Strings.padStart(s, 22, ' ');
//            System.out.format("%s = %d (%s)\n", sPad, n, testSieve.isPrime(n));
//            System.out.format("%s = %d\n", sPad, n);
            boolean isPrime = BigPrimeTest.isPrime(potPrime);
            if (testSieve.isPrime(n) != isPrime) {
                System.out.format("%s = %d (%s)\n", sPad, n, isPrime);
            }
        }
//        System.out.println();
//        for (int n=3; n <= testSieve.getLimit(); n += 2) {
//            if (testSieve.isPrime(n)) {
//                continue;
//            }
//            final BigInteger potPrime = BigInteger.valueOf(n);
//
//            final String s = new FactoradicInteger(potPrime).toShortString();
//            final String sPad = Strings.padStart(s, 22, ' ');
//            System.out.format("%s = %d (%s)\n", sPad, n, testSieve.isPrime(n));
//        }
//
//        System.out.println();System.out.println();System.out.println();
//        for (int n=1; n <= testSieve.getLimit(); n += 2) {
//            if (testSieve.isPrime(n)) {
//                continue;
//            }
//            final BigInteger potPrime = BigInteger.valueOf(n);
//
//            if (potPrime.mod(BigInteger.valueOf(3)).intValue() == 0) {
//                continue;
//            }
//
//            if (potPrime.mod(BigInteger.valueOf(5)).intValue() == 0) {
//                continue;
//            }
//
//            final String s = new FactoradicInteger(potPrime).toShortString();
//            final String sPad = Strings.padStart(s, 22, ' ');
//            System.out.format("%s = %d (%s)\n", sPad, n, testSieve.isPrime(n));
//        }
//        System.out.println();
//        for (int n=1; n <= testSieve.getLimit(); n+=2) {
//            if (testSieve.isPrime(n)) {
//                continue;
//            }
//            final BigInteger potPrime = BigInteger.valueOf(n);
//            final String s = new DoubleFacInt(potPrime).toShortString();
//            final String sPad = Strings.padStart(s, 22, ' ');
//            System.out.format("%s = %d (%s)\n", sPad, n, testSieve.isPrime(n));
//        }

//        int j = 3;
//        while (j < testSieve.getLimit()) {
//            final FactoradicInteger _j = new FactoradicInteger(BigInteger.valueOf(j));
//            final FactoradicInteger f = new FactoradicInteger(BigInteger.valueOf(j * j));
//            if (j*j % 5 == 0) {
//                j += 2;
//                continue;
//            }
//            System.out.println(_j + " : " + f);
//            j += 2;
//        }

//        for (int n=3; n <= testSieve.getLimit(); n += 2) {

//            if (BigPrimeTest.isPrime(BigInteger.valueOf(n))) {
//                System.out.print(n + " prime \n");
//            } else {
//                System.out.print(n + " composite \n");
//            }

//            if (BigPrimeTest.isPrime(BigInteger.valueOf(n*n))) {
//                System.out.print(n + ", ");
//            }

//            if (testSieve.isPrime(n) != BigPrimeTest.isPrime(BigInteger.valueOf(n))) {
//                System.out.print(new FactoradicInteger(BigInteger.valueOf(n)) + "," + '\n');
//                final FactoradicInteger f = new FactoradicInteger(BigInteger.valueOf(n));
//                System.out.println(f + ", " + PrimeFactorization.of(n));
//
//                Set<FactoradicInteger> s = new TreeSet<>();
//                for (int i=1; i <= f.getNumDigits(); i++) {
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

//            }
            //assertEquals(testSieve.isPrime(n), BigPrimeTest.isPrime(BigInteger.valueOf(n)));
//        }

    }
}
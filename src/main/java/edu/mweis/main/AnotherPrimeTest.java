package edu.mweis.main;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import edu.mweis.main.data.FactoradicInteger;
import edu.mweis.main.data.Factorial;
import edu.mweis.main.data.PrimeFactorization;

import java.math.BigInteger;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Scanner;

public class AnotherPrimeTest {

  private static final BigInteger TWO = BigInteger.valueOf(2L);

    /**
     * Strategy:
     * for each prime factor, from lo to hi:
     *  pull out said factor from all other
     *
     * @param potentialPrime
     * @return
     */
    public static boolean isPrime(BigInteger potentialPrime) {
        final FactoradicInteger factoradic = new FactoradicInteger(potentialPrime);

        if (isEven(potentialPrime)) {
            return equalsTwo(potentialPrime);
        }

//        final ImmutableList<ImmutableSortedMultiset<Integer>> terms = convertFactoradicToTermsList(factoradic);
        final ImmutableList<SortedMultiset<Integer>> mutableTerms = convertFactoradicToMutableTermsList(factoradic);
        final int numTerms = mutableTerms.size();
        final int lastIndex = numTerms - 1;

        int lastPrimeFactorEvaluated = 1;

        final SortedMultiset<Integer> primesUsed = TreeMultiset.create();

        final Unit first = new Unit();
        Unit current = first;

//        for (int i=0; i < numTerms; i++) {
        int i=1;

        while (i < lastIndex) {
            System.out.println(mutableTerms.toString());
//            // check for prime existing across all terms
//            if (i != 0 && i != lastIndex) { // if not on last term
//                for (int previousPrime : primesUsed) { // for all previous primes
//                    // the current and all future terms contain 1 or more of these primes
//                    if (Iterators.all(mutableTerms.subList(i, lastIndex).iterator(),
//                            termsGreaterThanI -> termsGreaterThanI.<Integer>contains(previousPrime))) {
//
//                        // find smallest number of occurrences of this prime across all terms
//                        final Optional<SortedMultiset<Integer>> minCount = mutableTerms.subList(i, lastIndex).stream()
//                                .min(Comparator.comparingInt(o -> o.count(previousPrime)));
//
//                        // remove this many occurrences from all affected terms
//
//                        if (!minCount.isPresent()) {
//                            throw new RuntimeException("Impossible state reached!");
//                        }
//
//                        final int occurrences = minCount.get().count(previousPrime);
//                        mutableTerms.subList(i, lastIndex).forEach(s -> s.remove(previousPrime, occurrences));
//
//                        final Unit next = new Unit();
//                        next.coeff = ImmutableSortedMultiset
//                                .<Integer>naturalOrder()
//                                .addCopies(previousPrime, occurrences)
//                                .build();
//
//                        next.outer = ImmutableSortedMultiset.of();
//                        current.inner = next; // point last Unit to this Unit
//                        current = next; // set this Unit to the current Unit (iterate forward)
//                    }
//                }
//            }


            // find smallest int in set which is also larger than the lastPrimeFactorEvaluated
            final SortedMultiset<Integer> currentTerm = mutableTerms.get(i);
            final int currentFactor = getNextUnevaluatedFactor(currentTerm, lastPrimeFactorEvaluated);

            final OptionalInt minOccurrences = mutableTerms.subList(i, lastIndex).stream()
                    .mapToInt(s -> s.count(currentFactor)).reduce(Integer::min);

            if (!minOccurrences.isPresent()) {
                throw new RuntimeException("Impossible state reached!");
            }

            primesUsed.add(currentFactor, minOccurrences.getAsInt());

            Unit next = new Unit();
            next.coeff = ImmutableSortedMultiset
                    .<Integer>naturalOrder()
                    .addCopies(currentFactor, minOccurrences.getAsInt())
                    .build();
            next.outer = ImmutableSortedMultiset.copyOfSorted(mutableTerms.get(i-1));

            current.inner = next; // point current to next
            current = next; // make current equal next (iterate forward)

            lastPrimeFactorEvaluated = currentFactor;

            // increment to next term
            System.out.println("i is " + i);
            i++;
            System.out.println("i is now " + i);
        }

//        Unit last = new Unit();
//        last.
//        current.inner =

        // print progress
//        Unit iter = first;
//        while (iter.inner != null) {
//
//            iter = iter.inner;
//        }

        System.out.println(first.toString());
        System.out.println(current.toString());

        return false;
    }

    private static boolean isEven(BigInteger potentialPrime) {
        return potentialPrime.mod(TWO).equals(BigInteger.ZERO);
    }

    private static boolean equalsTwo(BigInteger potentialPrime) {
        return potentialPrime.equals(TWO);
    }

    private static ImmutableList<SortedMultiset<Integer>> convertFactoradicToMutableTermsList(FactoradicInteger factoradic) {
        final ImmutableList.Builder<SortedMultiset<Integer>> termsBuilder =
                ImmutableList.builderWithExpectedSize(factoradic.getNumDigits());

        final SortedMultiset<Integer> term = TreeMultiset.create();

        // build all terms
        for (int i=1; i <= factoradic.getNumDigits(); i++) {
//            term.addAll(PrimeFactorization.of(i).getIntFactors());
            final ImmutableSortedMultiset<Integer> intFactors = PrimeFactorization.of(Factorial.of(i));
            intFactors.forEach(integer -> term.add(integer, intFactors.count(integer)));

            term.add(factoradic.getDigit(i-1).intValueExact());
            termsBuilder.add(TreeMultiset.create(term));
        }

        return termsBuilder.build();
    }

    private static int getNextUnevaluatedFactor(SortedMultiset<Integer> currentTerm, int lastPrimeEvaluated) {

        for (int factor : currentTerm) {
            if (factor > lastPrimeEvaluated) {
                return factor;
            }
        }

        throw new RuntimeException("Next factor could not be found");
    }

    private static class Unit {

//        private final static Unit EMPTY_UNIT = new Unit(ImmutableSortedMultiset.of(), ImmutableSortedMultiset.of(), )

        private ImmutableSortedMultiset<Integer> coeff;
        private ImmutableSortedMultiset<Integer> outer;
        private Unit inner;

//        private Unit(ImmutableSortedMultiset<Integer> coeff, ImmutableSortedMultiset<Integer> outer) {
//            this(coeff, outer, null);
//        }
//
//        private Unit(ImmutableSortedMultiset<Integer> coeff, ImmutableSortedMultiset<Integer> outer, Unit inner) {
//            this.coeff = coeff;
//            this.outer = outer;
//            this.inner = inner;
//        }

        @Override
        public String toString() {
            return String.format("%s(%s)+%s",
                    Objects.toString(coeff, ""),
                    Objects.toString(inner, ""),
                    Objects.toString(outer, "")); // todo fix mutiset formatting
        }
    }

    private AnotherPrimeTest() {

    }
}

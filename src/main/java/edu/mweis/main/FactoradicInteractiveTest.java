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

public class FactoradicInteractiveTest {

  public static void main(String[] args) {
    System.out.println("Welcome! Try typing a number and press enter...");
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextBigInteger()) {
        final BigInteger n = scanner.nextBigInteger();
        final FactoradicInteger factoradic = new FactoradicInteger(n);
        System.out.println(factoradic.toPrettyString());
      }
    }
  }

}

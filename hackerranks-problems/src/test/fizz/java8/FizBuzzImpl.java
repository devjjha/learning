package test.fizz.java8;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

interface DividerFunction<N, D, R, S> {
    S apply(N n, D d, R r);
}

interface FizzBuzz {
    Optional<String> divideReturn(Integer number);
}

public class FizBuzzImpl {

    static BiPredicate<Integer, Integer> predicate = (number, divisor) -> (number % divisor) == 0;
    static DividerFunction<Integer, Integer, String, Optional> dividorFunction = (num, div, str) -> predicate.test(num, div) ?
            Optional.of(str) : Optional.empty();

    static FizzBuzz defaultValue = (number) -> Optional.of(String.valueOf(number));
    static FizzBuzz fizz = (number) -> dividorFunction.apply(number, 3, "Fizz");
    static FizzBuzz buzz = (number) -> dividorFunction.apply(number, 5, "Buzz");

    private static String fizzBuzzNumbers(Integer number, List<FizzBuzz> suppliers) {
        return Optional.of(suppliers.stream()
                .map(supplier -> supplier.divideReturn(number))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining())
        ).map(val -> val.isEmpty() ? defaultValue.divideReturn(number).get() : val).get();
    }

    public static void main(String[] args) {

        List<FizzBuzz> fiz = new LinkedList<>();
        fiz.add(fizz);
        fiz.add(buzz);

        for (int i = 1; i < 16; i++) {
            String returns = fizzBuzzNumbers(i, fiz);
            System.out.println("int " + i + "= " + returns);
        }
    }
}

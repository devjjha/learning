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
    static FizzBuzz seven = (number) -> dividorFunction.apply(number, 7, "Seven");
    static FizzBuzz Four = (number) -> dividorFunction.apply(number, 4, "Four");

    /**
     * Steps:
     * Taking instances of FizzBuzz to apply on integer. Iterating over it and returning String
     * Once all the FizzBuzz applied then collecting the returned String
     * If result of intermediate operation is empty then returning the default value as String value of Integer.
     */
    private String fizzBuzzNumbers(Integer number, List<FizzBuzz> suppliers) {
        return Optional.of(suppliers.stream()
                .map(supplier -> supplier.divideReturn(number))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining())
        ).map(val -> val.isEmpty() ? defaultValue.divideReturn(number).get() : val).get();
    }

    public static void main(String[] args) {

        FizBuzzImpl impl = new FizBuzzImpl();
        List<FizzBuzz> fiz = new LinkedList<>();
        fiz.add(fizz);
        fiz.add(buzz);
        fiz.add(Four);
        fiz.add(seven);

        for (int i = 1; i < 50; i++) {
            String returns = impl.fizzBuzzNumbers(i, fiz);
            System.out.println("int " + i + "= " + returns);
        }
    }
}

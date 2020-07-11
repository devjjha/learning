package test.fizz.java8;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    private String fizzBuzzNumbersLinear(Integer number, List<FizzBuzz> suppliers) {
        return Optional.of(suppliers.stream()
                .map(supplier -> supplier.divideReturn(number))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining())
        ).map(val -> val.isEmpty() ? defaultValue.divideReturn(number).get() : val).get();
    }

    private String fizzBuzzNumbersParallel(Integer number, List<FizzBuzz> suppliers) {
        Stream<FizzBuzz> stream = suppliers.stream().parallel();
        System.out.println(stream.isParallel());
        return Optional.of(stream
                .map(supplier -> supplier.divideReturn(number))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining())
        ).map(val -> val.isEmpty() ? defaultValue.divideReturn(number).get() : val).get();
    }

    private long doLinearOperation(int range, List<FizzBuzz> fiz) {
        long fizzBuzzNumbersST = System.currentTimeMillis();
        IntStream.range(1, range).forEach(i -> System.out.println(fizzBuzzNumbersLinear(i, fiz)));
        long fizzBuzzNumbersET = System.currentTimeMillis();

        return (fizzBuzzNumbersET - fizzBuzzNumbersST);
    }

    private long doParallelOperation(int range, List<FizzBuzz> fiz) {
        long fizzBuzzNumbersST = System.currentTimeMillis();
        IntStream.range(1, range).forEach(i -> System.out.println(fizzBuzzNumbersParallel(i, fiz)));
        long fizzBuzzNumbersET = System.currentTimeMillis();

        return (fizzBuzzNumbersET - fizzBuzzNumbersST);
    }

    public static void main(String[] args) {

        FizBuzzImpl impl = new FizBuzzImpl();
        List<FizzBuzz> fiz = new LinkedList<>();
        fiz.add(fizz);
        fiz.add(buzz);
        fiz.add(Four);
        fiz.add(seven);
        int range = 5000;
        long linerTime = impl.doLinearOperation(range, fiz);
        long parallelTime = impl.doParallelOperation(range, fiz);
        System.out.println("Linear Time elapse in millis >> " + linerTime);
        System.out.println("Parallel Time elapse in millis >> " + parallelTime);

    }
}

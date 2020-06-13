package test.fizz.java7;

import java.util.Deque;
import java.util.LinkedList;

public class Test {

    public void Test() {
        fizzBuzz(50);
    }

    public static void fizzBuzz(int n) {
        Deque<String> stack = new LinkedList<>();
        for (int i = n; i > 0; --i) {
            //System.out.println(i);
            boolean canBeDividedByThree = ((i % 3) == 0);
            boolean canBeDividedByFive = ((i % 5) == 0);

            if (canBeDividedByThree && canBeDividedByFive) {
                stack.push("FizzBuzz");
            } else if (canBeDividedByThree && !canBeDividedByFive) {
                stack.push("Fizz");
            } else if (!canBeDividedByThree && canBeDividedByFive) {
                stack.push("Buzz");
            } else {
                stack.push(String.valueOf(i));
            }
        }
        stack.forEach(System.out::println);


    }

    public static void main(String[] args) {
        Test.fizzBuzz(50);
    }
}

package test.fizz.java7;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharCount {

    private static long beforeJave9(Matcher matcher) {
        long count = 0;
        while (matcher.find())
            ++count;
        return count;
    }

    private static long byJava9(Matcher matcher) {
        return matcher.results().count();
    }

    public static void main(String[] args) {
        String str = "bcAaxazza";
        Pattern pattern = Pattern.compile("[aA]");

        //here matchers works as on Index >>
        System.out.println("Before Java 9 >> " + beforeJave9(pattern.matcher(str)));
        System.out.println("By Java 9 >> " + byJava9(pattern.matcher(str)));


        Predicate<String> predicateMatcher = Pattern.compile("[aA]").asMatchPredicate();
        System.out.println(predicateMatcher.test("abc"));
        System.out.println(predicateMatcher.test("a"));
    }
}

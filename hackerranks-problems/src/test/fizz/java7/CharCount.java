package test.fizz.java7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharCount {
    private static long beforeJave9(Matcher matcher){
        long count = 0;
        while (matcher.find())
            ++count;
        return count;
    }
    private static long byJava9(Matcher matcher){
       return matcher.results().count();
    }

    public static void main(String[] args) {
        String str = "bcAaxazza";
        Pattern pattern = Pattern.compile("[aA]");
        Matcher matcher = pattern.matcher(str);

        System.out.println(beforeJave9(matcher));
        System.out.println(byJava9(matcher));
    }
}

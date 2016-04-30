package Hotpad;

/**
 * Created by rliu on 4/21/16.
 * ​Given a string, write a routine (in Java) that converts the string to a long, without using the built
 * in functions that would do this. Describe what (if any) limitations the code has. For example:
 * long stringToLong(String s)
 * {
 * /* code goes here to convert a string to a long
 * }
 * void test()
 * {long i = stringToLong("123");
 * if (i == 123)
 * // success
 * else
 * // failure
 * }
 */

public class stringToLong {
    public static void main(String[] args) throws Exception {
        //System.out.print( -Long.MAX_VALUE / 10);
        System.out.print(Long.parseLong("9223372036854775807") + "      ");
        System.out.print(dostringToLong("9223372036854775800"));

    }

    public static long dostringToLong(String s) throws Exception {
        if (s == null) {
            throw new Exception("input can not be null");
        }

        long result = 0;
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        int length = s.length();
        boolean negative = false;
        int i = 0;
        int radix = 10;
        long firstMin = max / 10;
        if (length > 0) {
            char firstChar = s.charAt(i);
            if (firstChar < '0' || firstChar > '9') { //first character can only be number, +, -
                switch (firstChar) {
                    case '-':
                        negative = true;
                        i++;
                        break;
                    case '+':
                        i++;
                        break;
                    default:
                        throw new Exception("invalid input sign");
                }
            }
            while (i < length) {
                char curr = s.charAt(i++);
                if (curr < '0' || curr > '9') //following character should be all numbers
                    throw new Exception("invalid input");
                int digit = curr - '0';
                if (result > firstMin) {
                    throw new Exception("exceed the boundary");
                }
                result *= radix;
                if (!negative && (result > max - digit))
                    throw new Exception("exceed the maximum boundary");
                else if (negative && ((-result) < min + digit))
                    throw new Exception("exceed the minimum boundary");

                result += digit;
            }

        } else {
            throw new Exception("input string invalid");
        }
        return negative ? -result : result;
    }


}


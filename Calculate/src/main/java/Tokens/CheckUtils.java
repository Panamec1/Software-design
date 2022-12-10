package Tokens;

public class CheckUtils {

    static boolean bracketsCheck(char c) {
        if ( c == '(' ) return true;
        if ( c == ')' ) return true;
        return false;
    }

    static boolean spaceCheck(char c) {
        return c == ' ';
    }

    static boolean operatorsCheck(char c) {
        if ( c == '+' ) return true;
        if ( c == '-' ) return true;
        if ( c == '*' ) return true;
        if ( c == '/' ) return true;
        return false;
    }

    static boolean isDigit(char c) {
        if (c <= '9' && c >= '0' ) return true;
        return false;
    }
}

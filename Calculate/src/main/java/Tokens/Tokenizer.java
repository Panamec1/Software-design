package Tokens;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import TokenTypes.BracketsToken;
import TokenTypes.NumberToken;
import TokenTypes.OperationsToken;

public class Tokenizer {

    private Tokenizer() {}

    private List<Token> parse(String expression) throws ParseException {
        List<Token> result = new ArrayList<Token>();
        for (int i = 0; i < expression.length(); i++) {
            char charAt = expression.charAt(i);
            if (CheckUtils.spaceCheck(charAt)) continue;

            if (CheckUtils.bracketsCheck(charAt)) {
                result.add(new BracketsToken(charAt));
                continue;
            }
            if (CheckUtils.operatorsCheck(charAt)) {
                result.add(new OperationsToken(charAt));
                continue;
            }
            if (CheckUtils.isDigit(charAt)) {
                StringBuilder num = new StringBuilder();
                for (int j = i; j <expression.length()  ; j++) {
                    if (!CheckUtils.isDigit(expression.charAt(j))) break;
                    num.append(expression.charAt(j));
                    i = j;
                }
                result.add(new NumberToken(num.toString()));

                continue;
            }
            throw new ParseException("Symbol " + charAt + " is bad at" + i, 1);
        }
        return result;
    }

    public static List<Token> getTokens(String expr) throws ParseException {
        return new Tokenizer().parse(expr);
    }
}

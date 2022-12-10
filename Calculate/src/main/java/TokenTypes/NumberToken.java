package TokenTypes;

import Tokens.Token;
import Visitor.TokenVisitor;

public class NumberToken implements Token {
    private int number = 0;

    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public NumberToken(String exp) {
        number = Integer.parseInt(exp);
    }

    public String toString() {
        return "NUM(" + number + ")";
    }

    public TokenType getTokenType() {
        return TokenType.NUM;
    }

    public int getValue() {
        return number;
    }
}

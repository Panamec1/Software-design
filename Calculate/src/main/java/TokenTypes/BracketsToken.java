package TokenTypes;

import Tokens.Token;
import Visitor.TokenVisitor;

import java.text.ParseException;

public class BracketsToken implements Token {
    TokenType type;

    public void accept(TokenVisitor visitor) throws ParseException {
        visitor.visit(this);
    }

    public BracketsToken(char exp) {
        if ('(' == exp)  type = TokenType.BR_LEFT;
        if (')' == exp)  type = TokenType.BR_RIGHT;
    }

    public String toString() {
        return type.toString();
    }

    public TokenType getTokenType() {
        return type;
    }
}

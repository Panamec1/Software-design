package TokenTypes;

import Tokens.Token;
import Visitor.TokenVisitor;

public class OperationsToken implements Token {

    private TokenType type;
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public OperationsToken(char exp) {
        if ('+' == exp) type = TokenType.ADD;
        if ('-' == exp) type = TokenType.SUB;
        if ('*' == exp) type = TokenType.MUL;
        if ('/' == exp) type = TokenType.DIV;
    }

    public String toString() {
        return type.toString();
    }

    public TokenType getTokenType() {
        return type;
    }

}

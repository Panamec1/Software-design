package Tokens;

import Visitor.TokenVisitor;
import java.text.ParseException;


public interface Token {
    public String toString();
    public TokenType getTokenType();
    void accept(TokenVisitor visitor) throws ParseException;


    enum TokenType {ADD, SUB, MUL,
        DIV, BR_LEFT,
        BR_RIGHT, NUM}

}


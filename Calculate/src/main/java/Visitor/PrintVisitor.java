package Visitor;

import Tokens.Token;
import TokenTypes.BracketsToken;
import TokenTypes.NumberToken;
import TokenTypes.OperationsToken;

import java.text.ParseException;
import java.util.List;

public class PrintVisitor implements TokenVisitor {

    String result = "";
    private PrintVisitor() {}

    private String print(List<Token> tokens) throws ParseException {
        for (Token token: tokens) token.accept(this);
        return result;
    }

    public static String printTokens(List<Token> tokens) throws ParseException {
        return new PrintVisitor().print(tokens);
    }



    public void visit(BracketsToken token) {
        result = result + token.toString() + " ";
    }

    public void visit(OperationsToken token) {
        result = result + token.toString() + " ";
    }

    public void visit(NumberToken token) {
        result = result + token.toString() + " ";
    }
}

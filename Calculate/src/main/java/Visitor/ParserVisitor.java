package Visitor;

import Tokens.Token;
import TokenTypes.BracketsToken;
import TokenTypes.NumberToken;
import TokenTypes.OperationsToken;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ParserVisitor implements TokenVisitor {

    List<Token> result = new ArrayList<Token>();
    Stack<Token> stack = new Stack<Token>();

    public void visit(NumberToken token) {
        result.add(token);
    }

    public void visit(BracketsToken token) throws ParseException {
        if (token.getTokenType() == Token.TokenType.BR_LEFT) {
            stack.add(token);
            return;
        }
        while (!stack.isEmpty()) {
            if (stack.peek().getTokenType() == Token.TokenType.BR_LEFT) break;
            result.add(stack.pop());
        }
        if (stack.isEmpty()) throw new ParseException("Left bracket not found", 1);
        stack.pop();
    }

    private boolean isOpers(Token.TokenType type) {
        if (type == Token.TokenType.ADD) return true;
        if (type == Token.TokenType.SUB) return true;
        if (type == Token.TokenType.MUL) return true;
        if (type == Token.TokenType.DIV) return true;
        return false;
    }

    public void visit(OperationsToken token) {
        while (!stack.isEmpty() && (token.getTokenType() == Token.TokenType.ADD ||
                token.getTokenType() == Token.TokenType.SUB)) {
            if (!isOpers(stack.peek().getTokenType())) break;
            result.add(stack.pop());
        }
        stack.add(token);
    }

    private List<Token> parse(List<Token> tokens) throws ParseException {
        for (Token token: tokens) token.accept(this);
        while (!stack.isEmpty()) result.add(stack.pop());
        return result;
    }

    public static List<Token> parsePolish(List<Token> tokens) throws ParseException {
        return new ParserVisitor().parse(tokens);
    }
}

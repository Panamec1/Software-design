package Visitor;

import Tokens.Token;
import TokenTypes.BracketsToken;
import TokenTypes.NumberToken;
import TokenTypes.OperationsToken;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;


public class CalculationVisitor implements TokenVisitor {
    private Stack<Integer> stack = new Stack<Integer>();

    private int evaluate(List<Token> tokens) throws ParseException {
        for (Token token: tokens) token.accept(this);
        return stack.pop();
    }

    public static int evaluateExpression(List<Token> tokens) throws ParseException {
        return new CalculationVisitor().evaluate(tokens);
    }

    public void visit(NumberToken token) {
        stack.add(token.getValue());
    }

    public void visit(BracketsToken token) {}

    public void visit(OperationsToken token) {
        int left = stack.pop();
        int right = stack.pop();
        if (token.getTokenType() == Token.TokenType.ADD) stack.add(left + right);
        if (token.getTokenType() == Token.TokenType.SUB) stack.add(right - left);
        if (token.getTokenType() == Token.TokenType.MUL) stack.add(left * right);
        if (token.getTokenType() == Token.TokenType.DIV) stack.add(right / left);
    }

}

package Visitor;

import TokenTypes.BracketsToken;
import TokenTypes.NumberToken;
import TokenTypes.OperationsToken;

import java.text.ParseException;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(BracketsToken token) throws ParseException;
    void visit(OperationsToken token);
}

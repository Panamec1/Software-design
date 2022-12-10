import Tokens.Token;
import Tokens.Tokenizer;
import Visitor.CalculationVisitor;
import Visitor.ParserVisitor;
import Visitor.PrintVisitor;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String expr = in.nextLine();
        try {
            List<Token> tokens = Tokenizer.getTokens(expr);
            List<Token> polishTokens = ParserVisitor.parsePolish(tokens);
            System.out.println(PrintVisitor.printTokens(polishTokens));
            System.out.println("Answer " + CalculationVisitor.evaluateExpression(polishTokens));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

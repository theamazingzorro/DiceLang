package dice.parser;

import java.util.ArrayList;
import java.util.List;

import dice.error.UnexpectedTokenException;
import dice.program.Function;
import dice.program.Program;
import dice.program.condition.Comparison;
import dice.program.condition.Comparison.CompType;
import dice.program.condition.Condition;
import dice.program.condition.Logic;
import dice.program.condition.Logic.LogicType;
import dice.program.expression.BinaryOp;
import dice.program.expression.BinaryOp.BinaryType;
import dice.program.expression.Constant;
import dice.program.expression.DiceOp;
import dice.program.expression.Expression;
import dice.program.expression.FunctionCall;
import dice.program.expression.Variable;
import dice.program.statement.Block;
import dice.program.statement.Command;
import dice.program.statement.Command.CommandType;
import dice.program.statement.IfElse;
import dice.program.statement.While;
import dice.tokenizer.Token;
import dice.tokenizer.Token.TokenType;

public class Parser {

    private final List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token consume() {
        return this.tokens.remove(0);
    }

    private Token consumeIf(TokenType t) throws UnexpectedTokenException {
        Token token = this.tokens.remove(0);
        if (token.type() != t) {
            throw new UnexpectedTokenException(token);
        }

        return token;
    }

    public Program parse() throws UnexpectedTokenException {
        Program p = new Program();

        List<Function> functions = new ArrayList<>();
        List<Command> globals = new ArrayList<>();

        while (this.peek().type() != TokenType.MAIN) {
            switch (this.peek().type()) {
                case FUNC:
                    functions.add(this.parseFunction());
                    break;
                case IDENTIFIER:
                    globals.add(this.parseAssignment());
                    break;
                default:
                    throw new UnexpectedTokenException(this.consume());
            }
        }

        this.consumeIf(TokenType.MAIN);
        Block b = this.parseBlock();

        while (this.peek().type() != TokenType.EOF) {
            switch (this.peek().type()) {
                case FUNC:
                    functions.add(this.parseFunction());
                    break;
                case IDENTIFIER:
                    globals.add(this.parseAssignment());
                    break;
                default:
                    throw new UnexpectedTokenException(this.consume());
            }
        }

        p.replaceBody(b);

        for (Function f : functions) {
            p.addFunction(f);
        }

        for (Command c : globals) {
            p.addGlobal(c);
        }

        return p;
    }

    private Command parseAssignment() throws UnexpectedTokenException {
        Token var = this.consumeIf(TokenType.IDENTIFIER);
        this.consumeIf(TokenType.ARROW);
        Expression e = this.parseExpression();

        this.consumeIf(TokenType.SEMICOLON);

        return new Command(CommandType.ASSIGNMENT, var.token(), e);
    }

    private Block parseBlock() throws UnexpectedTokenException {
        this.consumeIf(TokenType.L_BRACE);

        Block b = new Block(null);

        while (this.peek().type() != TokenType.R_BRACE) {
            switch (this.peek().type()) {
                case PRINT:
                    b.addStatement(this.parsePrint());
                    break;
                case RETURN:
                    b.addStatement(this.parseReturn());
                    break;
                case IF:
                    b.addStatement(this.parseIf());
                    break;
                case WHILE:
                    b.addStatement(this.parseWhile());
                    break;
                case IDENTIFIER:
                    if (this.peekNext().type() == TokenType.ARROW) {
                        b.addStatement(this.parseAssignment());
                    } else {
                        b.addStatement(this.parseExpressionStatement());
                    }
                    break;
                default:
                    b.addStatement(this.parseExpressionStatement());
                    break;
            }
        }

        this.consumeIf(TokenType.R_BRACE);

        return b;
    }

    private Condition parseComparison() throws UnexpectedTokenException {
        if (this.peek().type() == TokenType.L_PAREN) {
            this.consume();
            Condition c = this.parseCondition();
            this.consumeIf(TokenType.R_PAREN);
            return c;
        } else {
            Expression e1 = this.parseExpression();
            Token op = this.consume();
            Expression e2 = this.parseExpression();

            switch (op.type()) {
                case EQUALS:
                    return new Comparison(CompType.EQUAL, e1, e2);
                case NOT_EQUALS:
                    return new Comparison(CompType.NOT_EQUAL, e1, e2);
                case LESSER:
                    return new Comparison(CompType.LESS, e1, e2);
                case GREATER:
                    return new Comparison(CompType.GREAT, e1, e2);
                case LESSER_EQUAL:
                    return new Comparison(CompType.LESS_EQUAL, e1, e2);
                case GREATER_EQUAL:
                    return new Comparison(CompType.GREAT_EQUAL, e1, e2);
                default:
                    throw new UnexpectedTokenException(op);
            }
        }
    }

    private Condition parseCondition() throws UnexpectedTokenException {
        Condition o1 = this.parseNegation();
        if (this.peek().type() == TokenType.AND) {
            this.consume();
            Condition o2 = this.parseCondition();
            return new Logic(LogicType.AND, o1, o2);
        } else if (this.peek().type() == TokenType.OR) {
            this.consume();
            Condition o2 = this.parseCondition();
            return new Logic(LogicType.OR, o1, o2);
        } else {
            return o1;
        }
    }

    private Expression parseExpression() throws UnexpectedTokenException {
        Expression e1 = this.parseTerm();

        switch (this.peek().type()) {
            case PLUS:
                this.consume();
                return new BinaryOp(BinaryType.ADD, e1, this.parseExpression());
            case MINUS:
                this.consume();
                return new BinaryOp(BinaryType.SUBTRACT, e1, this.parseExpression());
            default:
                return e1;
        }
    }

    private Command parseExpressionStatement() throws UnexpectedTokenException {
        Expression e = this.parseExpression();
        this.consumeIf(TokenType.SEMICOLON);

        return new Command(CommandType.EXPR, e);
    }

    private Expression parseFactor() throws UnexpectedTokenException {
        switch (this.peek().type()) {
            case L_PAREN: {
                this.consume();
                Expression e = this.parseExpression();
                this.consumeIf(TokenType.R_PAREN);
                return e;
            }
            case NUMBER: {
                int i = Integer.parseInt(this.consume().token());
                return new Constant(i);
            }
            case DICE_OP: {
                this.consume();
                this.consumeIf(TokenType.L_PAREN);
                Expression e = this.parseExpression();
                this.consumeIf(TokenType.R_PAREN);
                return new DiceOp(e);
            }
            default: {
                Token t = this.consumeIf(TokenType.IDENTIFIER);
                if (this.peek().type() == TokenType.L_PAREN) {
                    this.consume();

                    List<Expression> args = new ArrayList<>();

                    while (this.peek().type() != TokenType.R_PAREN) {
                        args.add(this.parseExpression());

                        if (this.peek().type() == TokenType.COMMA) {
                            this.consume();
                        }
                    }

                    this.consume();

                    return new FunctionCall(t.token(), t.line(), args.toArray(new Expression[args.size()]));
                } else {
                    return new Variable(t.token(), t.line());
                }
            }
        }
    }

    private Function parseFunction() throws UnexpectedTokenException {
        this.consumeIf(TokenType.FUNC);
        Token name = this.consumeIf(TokenType.IDENTIFIER);
        this.consumeIf(TokenType.L_PAREN);

        List<String> params = new ArrayList<>();

        while (this.peek().type() != TokenType.R_PAREN) {
            Token p = this.consumeIf(TokenType.IDENTIFIER);

            params.add(p.token());

            if (this.peek().type() != TokenType.R_PAREN) {
                this.consumeIf(TokenType.COMMA);
            }
        }

        this.consumeIf(TokenType.R_PAREN);

        Block b = this.parseBlock();

        return new Function(name.token(), params, b);
    }

    private IfElse parseIf() throws UnexpectedTokenException {
        this.consumeIf(TokenType.IF);
        this.consumeIf(TokenType.L_PAREN);
        Condition c = this.parseCondition();
        this.consumeIf(TokenType.R_PAREN);
        Block b1 = this.parseBlock();

        if (this.peek().type() == TokenType.ELSE) {
            this.consume();
            if (this.peek().type() == TokenType.IF) {
                IfElse b2 = this.parseIf();
                return new IfElse(c, b1, b2);
            } else {
                Block b2 = this.parseBlock();
                return new IfElse(c, b1, b2);
            }
        } else {
            return new IfElse(c, b1, new Block(null));
        }

    }

    private Condition parseNegation() throws UnexpectedTokenException {
        if (this.peek().type() == TokenType.NOT) {
            this.consume();
            Condition o = this.parseComparison();
            return new Logic(LogicType.NOT, o);
        } else {
            return this.parseComparison();
        }
    }

    private Command parsePrint() throws UnexpectedTokenException {
        this.consumeIf(TokenType.PRINT);
        Expression e = this.parseExpression();
        this.consumeIf(TokenType.SEMICOLON);

        return new Command(CommandType.PRINT, e);
    }

    private Command parseReturn() throws UnexpectedTokenException {
        this.consumeIf(TokenType.RETURN);
        Expression e = this.parseExpression();
        this.consumeIf(TokenType.SEMICOLON);

        return new Command(CommandType.RETURN, e);
    }

    private Expression parseTerm() throws UnexpectedTokenException {
        Expression e1 = this.parseFactor();

        switch (this.peek().type()) {
            case STAR:
                this.consume();
                return new BinaryOp(BinaryType.MULTIPLY, e1, this.parseTerm());
            case SLASH:
                this.consume();
                return new BinaryOp(BinaryType.DIVIDE, e1, this.parseTerm());
            case PERCENT:
                this.consume();
                return new BinaryOp(BinaryType.MOD, e1, this.parseTerm());
            default:
                return e1;
        }
    }

    private While parseWhile() throws UnexpectedTokenException {
        this.consumeIf(TokenType.WHILE);
        this.consumeIf(TokenType.L_PAREN);
        Condition c = this.parseCondition();
        this.consumeIf(TokenType.R_PAREN);
        Block b = this.parseBlock();

        return new While(c, b);
    }

    private Token peek() {
        return this.tokens.get(0);
    }

    private Token peekNext() {
        if (this.tokens.size() > 1) {
            return this.tokens.get(1);
        }
        return this.tokens.get(0);
    }
}

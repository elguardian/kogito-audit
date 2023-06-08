package org.kie.kogito.audit.filter.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParserContext {
    
    private List<Token> tokens;
    private int currentIndex;

    public ParserContext(String filteredExpression) {
        this.tokens = tokenizer(filteredExpression);
        this.currentIndex = 0;
    }

    public boolean hasNext() {
        return this.currentIndex < tokens.size();
    }

    public boolean consume() {
        return ++this.currentIndex < tokens.size();
    }

    public Token currentToken() {
        if (this.currentIndex < tokens.size()) {
            return tokens.get(currentIndex);
        } else {
            throw new IllegalStateException("there is no more tokens");
        }

    }
    
    private List<Token> tokenizer(String filterExpression) {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(filterExpression));
            tokenizer.parseNumbers();
            tokenizer.quoteChar('\'');
            List<Token> tokens = new ArrayList<>();

            tokenizer.nextToken();
            while(tokenizer.ttype != StreamTokenizer.TT_EOF) {
                switch(tokenizer.ttype) {
                    case StreamTokenizer.TT_NUMBER:
                        tokens.add(new Token(TokenType.NUMBER, Long.toString((long) tokenizer.nval)));
                        break;
                    case StreamTokenizer.TT_WORD:
                        switch(tokenizer.sval) {
                            case "NOT": 
                                tokens.add(new Token(TokenType.NOT_OPERAND));
                                break;
                            case "EMPTY":
                                tokens.add(new Token(TokenType.IS_EMPTY_OPERAND));
                                break;
                            case "IS_NULL":
                                tokens.add(new Token(TokenType.IS_NULL_OPERAND));
                                break;
                            case "AND":
                                tokens.add(new Token(TokenType.AND));
                                break;
                            case "OR":
                                tokens.add(new Token(TokenType.OR));
                                break;
                            default:
                                tokens.add(new Token(TokenType.IDENTIFIER, tokenizer.sval));
                        }
                        break;
                    case '-':
                        if(tokenizer.nextToken() == '>') {
                            tokens.add(new Token(TokenType.EXTRACT));
                            break;
                        } else {
                            continue;                            
                        }
                    case '=':
                        tokens.add(new Token(TokenType.EQUAL_OPERAND));
                        break;
                    case '<':
                        if(tokenizer.nextToken() == '=') {
                            tokens.add(new Token(TokenType.LE_OPERAND));
                            break;
                        } else {
                            tokens.add(new Token(TokenType.LT_OPERAND));
                            continue;                            
                        }
                    case '>':
                        if(tokenizer.nextToken() == '=') {
                            tokens.add(new Token(TokenType.GE_OPERAND));
                            break;
                        } else {
                            tokens.add(new Token(TokenType.GT_OPERAND));
                            continue;
                        }
                    case '(':
                        tokens.add(new Token(TokenType.OPEN_PARENTHESIS));
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.CLOSE_PARENTHESIS));
                        break;
                    case '\'':
                        tokens.add(new Token(TokenType.LITERAL, tokenizer.sval));
                        break;
                }
                tokenizer.nextToken();
            }
            return tokens;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
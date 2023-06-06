package org.kie.kogito.audit.filter.parser;

public enum TokenType {
    IDENTIFIER,
    AND, 
    OR,
    NOT_OPERAND,
    IS_NULL_OPERAND,
    IS_EMPTY_OPERAND,
    LE_OPERAND,
    LT_OPERAND,
    GE_OPERAND,
    GT_OPERAND,
    EQUAL_OPERAND,
    NUMBER,
    CONSTANT,
    DATE,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS;
};
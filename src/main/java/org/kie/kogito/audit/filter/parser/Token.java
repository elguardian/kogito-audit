package org.kie.kogito.audit.filter.parser;

class Token {
    private String value;
    
    private TokenType type;

    public Token(TokenType type) {
        this(type, null);
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String asString() {
        return value;
    }

    @Override
    public String toString() {
        return "Token [value=" + value + ", type=" + type + "]";
    }
    
}
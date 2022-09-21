package com.memct;

/**
 * 算术解析器
 * 本类实现了一个简单的 Recursive Decent Parser，用以从字符串中解析简单的整数四则运算表达式
 * @author <a href="mailto:caoshengdong@msn.cn">曹胜东</a>
 */
public class ArithmeticParser {

    private final String str;

    public ArithmeticParser(String expression) {
        // 需要被解析的字符串
        this.str = expression;
    }

    // 当前位置
    int currentPos = 0;

    /**
     * 获取当前位置的字符
     *
     * @return 当前字符
     */
    char getCurrentChar() {
        if (currentPos < str.length()) {
            return str.charAt(currentPos);
        } else {
            return (char) -1;
        }
    }

    /**
     * 移动游标到下一个字符
     */
    void nextChar() {
        currentPos++;
    }

    /**
     * 从字符串解析整数运算表达式，并返回运算结果
     *
     * @return 运算结果
     */
    int eval() {
        int result = parseExpression();
        if (currentPos < str.length()) {
            throw new RuntimeException("Unexpected: " + getCurrentChar());
        }
        return result;
    }

    /**
     * 解析表达式
     *
     * @return 表达式的运算结果
     */
    int parseExpression() {
        int result = parseTerm();
        while (true) {
            if (getCurrentChar() == '+') { // 加法运算
                nextChar();
                result += parseTerm();
            } else if (getCurrentChar() == '-') { // 减法运算
                nextChar();
                result -= parseTerm();
            } else {
                return result;
            }
        }
    }

    /**
     * 解析算术表达式中的项
     * @return 该项的值，积或商
     */
    int parseTerm() {
        int result = parseFactor();
        while (true) {
            if (getCurrentChar() == '*') { // 乘法运算
                nextChar();
                result *= parseFactor();
            } else if (getCurrentChar() == '/') { // 除法运算
                nextChar();
                result /= parseFactor();
            } else {
                return result;
            }
        }
    }

    /**
     * 解析算术表达式中的因子
     * @return 该因子的值
     */
    int parseFactor() {
        if (getCurrentChar() >= '0' && getCurrentChar() <= '9') // 数字
            return parseNumber();
        else if (getCurrentChar() == '(') { // 括号
            nextChar(); // '('
            int result = parseExpression();
            if (getCurrentChar() != ')') { // 括号未闭合
                throw new RuntimeException("Parentheses not closed!");
            }
            nextChar(); // ')'
            return result;
        } else if (getCurrentChar() == '-') { // 负数，实际上未用到
            nextChar();
            return -parseFactor();
        } else {
            throw new RuntimeException("Unexpected: " + getCurrentChar());
        }
    }

    /**
     * 解析数字
     *
     * @return 被解析后的数字
     */
    int parseNumber() {
        int startPos = currentPos;
        while ((getCurrentChar() >= '0' && getCurrentChar() <= '9')) {
            nextChar();
        }
        return Integer.parseInt(str.substring(startPos, currentPos));
    }


}

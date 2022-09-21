package com.memct;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spreadsheet {

    private static Map<String, String> dataHolder;

    public Spreadsheet() {
        dataHolder = new HashMap<>();
    }

    /**
     * 该函数的目的是获取在column列的值，内容为等式的话需要计算并返回其结果。举例，若在"A1"列存储的值为"=7+3"，sheet.get("A1")应返回"10"。
     *
     * @param column 列数
     * @return 该列存储的值，默认为空字符串
     */
    public String get(String column) {
        String value = getLiteral(column);
        if (value.trim().matches("^\\d+$")) { // 纯数字，忽略空格
            return value.trim();
        } else if (value.startsWith("=")) { // 表达式，形如 =A+10*(C+6)
            // 尝试 lookup
            try {
                value = lookup(column, value);
            } catch (Exception e) {
                return "#Circular";
            }
            // 尝试解析数学表达式
            try {
                return String.valueOf(new ArithmeticParser(value.replaceFirst("=", "")).eval());
            } catch (Exception e) {
                return "#Error";
            }
        } else { // 其他等同纯字符串的
            return value;
        }
    }

    /**
     * 该函数的目的在于 lookup 其他单元格，如内容包含对其他单元格的引用，则获取该单元格的值，并直接替换内容中的引用。
     * 举例：若在"A"中存储的值为"=B+10"，"B"中存储的值为"=7"，lookup则返回"=7+10"
     * 虽然可以通过测试，但是如果遇到列名为 A1 这种情况还是会出问题
     *
     * @param column 该列名称
     * @param value 该列存储的值
     * @return 该列存储的lookup之后的数学表达式
     */
    private String lookup(String column, String value) {
        Pattern p = Pattern.compile("\\G[^A-Za-z]*([A-Za-z]+?)[^A-Za-z]*");
        Matcher m = p.matcher(value);
        while (m.find()) {
            // 如表达式包含该列名称，则出现回环
            if (m.group(1).equals(column)) {
                throw new RuntimeException("Circular: " + column);
            }
            value = value.replaceFirst(m.group(1), get(m.group(1)));
        }
        return value;
    }

    /**
     * 该函数的目的是获取在column列的字符串值，内容为等式的话不需要计算，直接返回字符串。举例，若在"A1"列存储的值为"=7+3"，sheet.getLiteral("A1")应返回"=7+3"。
     *
     * @param column 列数
     * @return 该列存储的字符串值，默认为空字符串
     */
    public String getLiteral(String column) {
        return dataHolder.getOrDefault(column, "");
    }

    /**
     * 该函数的目的是在column列存储value的值。如果该列已经被占用，则替换为当前值。
     *
     * @param column 列数
     * @param value  在该列需要存储的值
     */
    public void put(String column, String value) {
        dataHolder.put(column, value);
    }
}

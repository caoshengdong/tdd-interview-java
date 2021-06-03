import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class spreadsheetTest2 {

    /**
     * 对于格式不正确的等式(前面多了一个空格), Excel应该不予识别。
     */
    @Test
    public void testFormulaSpec() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("B", " =7"); // note leading space
        assertEquals("Not a formula", " =7", sheet.get("B"));
        assertEquals("Unchanged", " =7", sheet.getLiteral("B"));
    }

    /**
     * Excel应该正确识别等式
     */
    @Test
    public void testConstantFormula() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7");
        assertEquals("Formula", "=7", sheet.getLiteral("A"));
        assertEquals("Value", "7", sheet.get("A"));
    }

    /**
     * Excel应该正确识别包含括号的等式
     */
    @Test
    public void testParentheses() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=(7)");
        assertEquals("Parends", "7", sheet.get("A"));
    }

    /**
     * Excel应该正确识别包含很多括号的等式
     */
    @Test
    public void testDeepParentheses() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=((((10))))");
        assertEquals("Parends", "10", sheet.get("A"));
    }

    /**
     * Excel应该正确计算包含乘法的等式
     */
    @Test
    public void testMultiply() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=2*3*4");
        assertEquals("Times", "24", sheet.get("A"));
    }

    /**
     * Excel应该正确计算包含加法的等式
     */
    @Test
    public void testAdd() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=71+2+3");
        assertEquals("Add", "76", sheet.get("A"));
    }

    /**
     * Excel应该根据先乘后加的顺序计算
     */
    @Test
    public void testPrecedence() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7+2*3");
        assertEquals("Precedence", "13", sheet.get("A"));
    }

    /**
     * Excel应该正确地计算等式
     */
    @Test
    public void testFullExpression() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7*(2+3)*((((2+1))))");
        assertEquals("Expr", "105", sheet.get("A"));
    }

    /**
     * Excel应该返回错误信息若等式输入有错
     */
    @Test
    public void testSimpleFormulaError() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7*");
        assertEquals("Error", "#Error", sheet.get("A"));
    }

    /**
     * Excel应该返回错误信息若等式输入有错
     */
    @Test
    public void testParenthesisError() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=(((((7))");
        assertEquals("Error", "#Error", sheet.get("A"));
    }


}

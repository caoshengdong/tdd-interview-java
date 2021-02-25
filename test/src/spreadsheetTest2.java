import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class spreadsheetTest2 {

    @Test
    public void testFormulaSpec() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("B", " =7"); // note leading space
        assertEquals("Not a formula", " =7", sheet.get("B"));
        assertEquals("Unchanged", " =7", sheet.getLiteral("B"));
    }

    @Test
    public void testConstantFormula() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7");
        assertEquals("Formula", "=7", sheet.getLiteral("A"));
        assertEquals("Value", "7", sheet.get("A"));
    }

    @Test
    public void testParentheses() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=(7)");
        assertEquals("Parends", "7", sheet.get("A"));
    }

    @Test
    public void testDeepParentheses() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=((((10))))");
        assertEquals("Parends", "10", sheet.get("A"));
    }

    @Test
    public void testMultiply() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=2*3*4");
        assertEquals("Times", "24", sheet.get("A"));
    }

    @Test
    public void testAdd() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=71+2+3");
        assertEquals("Add", "76", sheet.get("A"));
    }

    @Test
    public void testPrecedence() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7+2*3");
        assertEquals("Precedence", "13", sheet.get("A"));
    }

    @Test
    public void testFullExpression() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7*(2+3)*((((2+1))))");
        assertEquals("Expr", "105", sheet.get("A"));
    }

    @Test
    public void testSimpleFormulaError() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=7*");
        assertEquals("Error", "#Error", sheet.get("A"));
    }

    @Test
    public void testParenthesisError() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "=(((((7))");
        assertEquals("Error", "#Error", sheet.get("A"));
    }


}

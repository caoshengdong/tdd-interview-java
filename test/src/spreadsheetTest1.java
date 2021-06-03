import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class spreadsheetTest1 {

    /**
     * 每列默认值为空字符串
     */
    @Test
    public void testThatCellsAreEmptyByDefault() {
        spreadsheet sheet = new spreadsheet();
        assertEquals("", sheet.get("A"));
        assertEquals("", sheet.get("ZX"));
    }

    /**
     * Excel可以正确地存储put的值
     */
    @Test
    public void testThatTextCellsAreStored() {
        spreadsheet sheet = new spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "A string");
        assertEquals("A string", sheet.get(theCell));

        sheet.put(theCell, "A different string");
        assertEquals("A different string", sheet.get(theCell));

        sheet.put(theCell, "");
        assertEquals("", sheet.get(theCell));
    }

    /**
     * Excel可以正确地覆盖之前的值
     */
    @Test
    public void testThatManyCellsExist() {
        spreadsheet sheet = new spreadsheet();
        sheet.put("A", "First");
        sheet.put("X", "Second");
        sheet.put("ZX", "Third");

        assertEquals("A", "First", sheet.get("A"));
        assertEquals("X", "Second", sheet.get("X"));
        assertEquals("ZX", "Third", sheet.get("ZX"));

        sheet.put("A", "Fourth");
        assertEquals("A after", "Fourth", sheet.get("A"));
        assertEquals("X same", "Second", sheet.get("X"));
        assertEquals("ZX same", "Third", sheet.get("ZX"));
    }

    /**
     * Excel可以正确识别纯数字，对于纯数字，将多余的空格去掉。
     */
    @Test
    public void testThatNumericCellsAreIdentifiedAndStored() {
        spreadsheet sheet = new spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "X99"); // "Obvious" string
        assertEquals("X99", sheet.get(theCell));

        sheet.put(theCell, "14"); // "Obvious" number
        assertEquals("14", sheet.get(theCell));

        sheet.put(theCell, " 99 X"); // Whole string must be numeric
        assertEquals(" 99 X", sheet.get(theCell));

        sheet.put(theCell, " 1234 "); // Blanks ignored
        assertEquals("1234", sheet.get(theCell));

        sheet.put(theCell, " "); // Just a blank
        assertEquals(" ", sheet.get(theCell));
    }

    /**
     * getLiteral需要返回未处理的纯字符串。
     */
    @Test
    public void testThatWeHaveAccessToCellLiteralValuesForEditing() {
        spreadsheet sheet = new spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "Some string");
        assertEquals("Some string", sheet.getLiteral(theCell));

        sheet.put(theCell, " 1234 ");
        assertEquals(" 1234 ", sheet.getLiteral(theCell));

        sheet.put(theCell, "=7"); // Foreshadowing formulas:)
        assertEquals("=7", sheet.getLiteral(theCell));
    }


}

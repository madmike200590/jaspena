package io.github.madmike200590.jaspena.types;

import org.junit.Assert;
import org.junit.Test;

public class FormatsTest {

    @Test
    public void testVarNames() {
        Assert.assertEquals(true, Formats.matchesVariableFormat("X"));
        Assert.assertEquals(true, Formats.matchesVariableFormat("_"));
        Assert.assertEquals(true, Formats.matchesVariableFormat("X_OR_Y"));
        Assert.assertEquals(false, Formats.matchesVariableFormat("x"));
        Assert.assertEquals(true, Formats.matchesVariableFormat("AVariableName"));
        Assert.assertEquals(true, Formats.matchesVariableFormat("SOMEVAR"));
        Assert.assertEquals(false, Formats.matchesVariableFormat("123ABC"));
        Assert.assertEquals(false, Formats.matchesVariableFormat("1"));
        Assert.assertEquals(false, Formats.matchesVariableFormat("_ABC"));
        Assert.assertEquals(false, Formats.matchesVariableFormat("_abc"));
        Assert.assertEquals(true, Formats.matchesVariableFormat("ABC123A"));
    }

    @Test
    public void testConstNames() {
        Assert.assertEquals(false, Formats.matchesConstantFormat("AMisnamedConstant"));
        Assert.assertEquals(false, Formats.matchesConstantFormat("another misnamed constant"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("name"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("name12"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("nam3sAnd5tu7f"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("x"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("x_or_y_and_z"));
        Assert.assertEquals(true, Formats.matchesConstantFormat("a_123_b_cXX_YY"));
        Assert.assertEquals(false, Formats.matchesConstantFormat("name\""));
    }

    @Test
    public void testStrings() {
        Assert.assertEquals(true, Formats.matchesStringFormat("\"a quoted string\""));
        Assert.assertEquals(true,
                Formats.matchesStringFormat("\"a quoted string with < a few ^ special ° chars!!!!111!\""));
        Assert.assertEquals(false, Formats.matchesStringFormat("\"an incorrectly \" escaped quote\""));
    }

}

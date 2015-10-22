package htsjdk.samtools.cram.encoding.huffman.codec;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadim on 22/04/2015.
 */
public class HuffmanParamsCalculatorTest {

    @Test
    public void test_add_1() {
        HuffmanParamsCalculator c = new HuffmanParamsCalculator();
        c.add(1);
        c.calculate();

        int[] values = c.getValues();
        int[] bitLens = c.getBitLens();

        Assert.assertEquals(1, values.length);
        Assert.assertEquals(1, bitLens.length);

        Assert.assertEquals(1, values[0]);
        Assert.assertEquals(0, bitLens[0]);
    }

    @Test
    public void test_add_1_1() {
        HuffmanParamsCalculator c = new HuffmanParamsCalculator();
        c.add(1, 1);
        c.calculate();

        int[] values = c.getValues();
        int[] bitLens = c.getBitLens();

        Assert.assertEquals(1, values.length);
        Assert.assertEquals(1, bitLens.length);

        Assert.assertEquals(1, values[0]);
        Assert.assertEquals(0, bitLens[0]);
    }

    @Test
    public void test_add_many() {
        HuffmanParamsCalculator c = new HuffmanParamsCalculator();
        c.add(1);
        c.add(2);
        c.add(2);
        c.add(3);
        c.add(3);
        c.add(3);
        c.calculate();

        Map<Integer, Integer> expections_Value2BitLen = new HashMap<Integer, Integer>();
        expections_Value2BitLen.put(1, 2);
        expections_Value2BitLen.put(2, 2);
        expections_Value2BitLen.put(3, 1);

        int[] values = c.getValues();
        int[] bitLens = c.getBitLens();

        Assert.assertEquals(3, values.length);
        Assert.assertEquals(3, bitLens.length);

        for (int i=0; i<values.length; i++) {
            int value = values[i];
            Assert.assertTrue(expections_Value2BitLen.containsKey(value));

            int bitLen = expections_Value2BitLen.get(value);
            Assert.assertEquals(bitLen, bitLens[i], i+": " + value);

            expections_Value2BitLen.remove(value);
        }

        Assert.assertTrue(expections_Value2BitLen.isEmpty());
    }
}

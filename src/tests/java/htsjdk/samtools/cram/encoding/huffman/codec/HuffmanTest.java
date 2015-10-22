package htsjdk.samtools.cram.encoding.huffman.codec;

import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by vadim on 22/04/2015.
 */
public class HuffmanTest {

    @Test
    public void testHuffmanByteHelper() throws IOException {
        int size = 100;

        HuffmanParamsCalculator cal = new HuffmanParamsCalculator();
        for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            cal.add(i, (i + Byte.MIN_VALUE )/3 + 1);
        }
        cal.calculate();

        HuffmanByteHelper helper = new HuffmanByteHelper(cal.getValuesAsBytes(), cal.getBitLens());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DefaultBitOutputStream bos = new DefaultBitOutputStream(baos);

        for (int i = 0; i < size; i++) {
            for (byte b : cal.getValuesAsBytes()) {
                helper.write(bos, b);
            }
        }

        bos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DefaultBitInputStream bis = new DefaultBitInputStream(bais);

        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (byte b : cal.getValuesAsBytes()) {
                byte v = helper.read(bis);
                if (v != b) {
                    Assert.fail("Mismatch: " + v + " vs " + b + " at " + counter);
                }

                counter++;
            }
        }
    }

    @Test
    public void testHuffmanIntHelper() throws IOException {
        int size = 100;

        HuffmanParamsCalculator cal = new HuffmanParamsCalculator();
        for (int i = -300; i < 300; i++) {
            cal.add(i, 1+ (i + 300) / 3);
        }
        cal.calculate();

        HuffmanIntHelper helper = new HuffmanIntHelper(cal.getValues(), cal.getBitLens());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DefaultBitOutputStream bos = new DefaultBitOutputStream(baos);

        for (int i = 0; i < size; i++) {
            for (int b : cal.getValues()) {
                helper.write(bos, b);
            }
        }

        bos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DefaultBitInputStream bis = new DefaultBitInputStream(bais);

        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int b : cal.getValues()) {
                int v = helper.read(bis);
                if (v != b) {
                    Assert.fail("Mismatch: " + v + " vs " + b + " at " + counter);
                }

                counter++;
            }
        }
    }
}

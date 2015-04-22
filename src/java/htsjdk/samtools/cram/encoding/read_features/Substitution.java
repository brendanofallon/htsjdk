/*******************************************************************************
 * Copyright 2013 EMBL-EBI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package htsjdk.samtools.cram.encoding.read_features;

import java.io.Serializable;

/**
 * A substitution event captured in read coordinates. It is characterized by position in read, read base and reference base.
 * The class is also responsible for converting combinations of read base and reference base into a byte value (code).
 */
public class Substitution implements Serializable, ReadFeature {

    /**
     * zero-based position in read
     */
    private int position;
    /**
     * The read base (ACGTN)
     */
    private byte base = -1;
    /**
     * The reference sequence base matching the position of this substitution.
     */
    private byte referenceBase = -1;
    /**
     * A byte value denoting combination of the read base and the reference base.
     */
    private byte code = -1;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public static final byte operator = 'X';

    @Override
    public byte getOperator() {
        return operator;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public byte getBase() {
        return base;
    }

    public void setBase(byte base) {
        this.base = base;
    }

    public byte getReferenceBase() {
        return referenceBase;
    }

    public void setReferenceBase(byte referenceBase) {
        this.referenceBase = referenceBase;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Substitution))
            return false;

        Substitution v = (Substitution) obj;

        if (position != v.position)
            return false;

        if ((code != v.code) & (code == -1 || v.code == -1)) {
            return false;
        }

        if (code > -1 && v.code > -1) {
            if (referenceBase != v.referenceBase) return false;
            if (base != v.base) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf((char) operator) + '@' + position + '\\' + (char) base + (char) referenceBase;
    }
}

package BytesBuffer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestBytesBuffer {
    @Test
    public void testAppend() {
        BytesBuffer bb = new BytesBuffer();
        byte[] b1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] b2 = new byte[]{0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
        try {
            bb.append(b1);
            assertEquals(b1.length, bb.getCapacity());
            assertEquals(b1.length, bb.getSize());
//            System.out.println(Arrays.toString(bb.getBytes()));
            for (int i = 0; i < b1.length; i++) {
                assertEquals(b1[i], bb.getBytes()[i]);
            }
            bb.append(b2);
            assertEquals(b1.length + b2.length, bb.getCapacity());
            assertEquals(b1.length + b2.length, bb.getSize());
            for (int i = 0; i < b1.length; i++) {
                assertEquals(b1[i], bb.getBytes()[i]);
            }
            for (int i = b1.length; i < b1.length + b2.length; i++) {
                assertEquals(b2[i - b1.length], bb.getBytes()[i]);
            }
//            assertEquals(b1, bb.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIndex() {
        BytesBuffer bb = new BytesBuffer();
        byte[] b1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] b2 = new byte[]{0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
        try {
            bb.append(b1);
            assertEquals(0, bb.indexOf((byte) 1));
            assertEquals(8, bb.indexOf((byte) 9));
            assertEquals(0, bb.indexOf(new byte[]{1, 2, 3}));
            assertEquals(6, bb.indexOf(new byte[]{7, 8, 9}));
            assertEquals(-1, bb.indexOf(new byte[]{8, 9, 11}));
            assertEquals(-1, bb.indexOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
            assertEquals(-1, bb.indexOf((byte) 10));
            bb.append(b2);
            assertEquals(0, bb.indexOf((byte) 1));
            assertEquals(8, bb.indexOf((byte) 9));
            assertEquals(6, bb.indexOf(new byte[]{7, 8, 9}));
            assertEquals(8, bb.indexOf(new byte[]{9, 0x10, 0x11, 0x12}));
            assertEquals(9, bb.indexOf((byte) 0x10));
            assertEquals(18, bb.indexOf((byte) 0x19));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubBytes() {
        BytesBuffer bb = new BytesBuffer();
        byte[] b1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] b2 = new byte[]{0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
        byte[] subByte;
        try {
            bb.append(b1);
            assertEquals(0, bb.indexOf((byte) 1));
            subByte = bb.subBytes(bb.indexOf((byte) 1), bb.indexOf((byte) 1) + 1);
            assertEquals(1, subByte.length);
            assertEquals(0, Byte.compare(subByte[0], (byte) 1));
            assertEquals(8, bb.indexOf((byte) 9));
            assertEquals(0, bb.indexOf(new byte[]{1, 2, 3}));
            assertEquals(6, bb.indexOf(new byte[]{7, 8, 9}));
            assertEquals(-1, bb.indexOf(new byte[]{8, 9, 11}));
            assertEquals(-1, bb.indexOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
            assertEquals(-1, bb.indexOf((byte) 10));
            bb.append(b2);
            assertEquals(0, bb.indexOf((byte) 1));
            assertEquals(8, bb.indexOf((byte) 9));
            assertEquals(6, bb.indexOf(new byte[]{7, 8, 9}));
            byte[] searchByte = new byte[]{9, 0x10, 0x11, 0x12};
            assertEquals(8, bb.indexOf(searchByte));
            subByte = bb.subBytes(bb.indexOf(searchByte), bb.indexOf(searchByte) + searchByte.length);
            assertEquals(searchByte.length, subByte.length);
            for (int i = 0; i < subByte.length; i++) {
                assertEquals(searchByte[i], subByte[i]);
            }
            assertEquals(9, bb.indexOf((byte) 0x10));
            assertEquals(18, bb.indexOf((byte) 0x19));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        BytesBuffer bb = new BytesBuffer();
        byte[] b1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] b2 = new byte[]{0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
        try {
            bb.append(b1);
            bb.delete(2, 13);
            assertEquals(2, bb.getSize());
            assertEquals(9, bb.getCapacity());
            assertEquals(2, bb.getBytes().length);
//            assertEquals(0, bb.indexOf((byte) 1));
            assertEquals(1, bb.indexOf((byte) 2));
            assertEquals(-1, bb.indexOf((byte) 3));
            assertEquals(-1, bb.indexOf(new byte[]{1, 2, 3}));
            bb.append(b2);
            assertEquals(0, bb.indexOf(new byte[]{1, 2, 0x10, 0x11,}));
            assertEquals(1, bb.indexOf(new byte[]{2, 0x10, 0x11,}));
            byte[] searchByte = new byte[]{1, 2, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
            assertEquals(0, bb.indexOf(searchByte));
            byte[] subBytes = bb.subBytes(bb.indexOf(searchByte), bb.indexOf(searchByte) + searchByte.length);
            assertEquals(searchByte.length, subBytes.length);
            for (int i = 0; i < searchByte.length; i++) {
                assertEquals(searchByte[i], subBytes[i]);
            }
            assertEquals(-1, bb.indexOf(new byte[]{1, 2, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x20}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
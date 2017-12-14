import BytesBuffer.BytesBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZZ on 17/12/13.
 */
public class main {
    public static void main(String[] argv) {
        BytesBuffer bb = new BytesBuffer();
        byte[] b1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        try {
            bb.append(b1);
//            assertEquals(b1.length, bb.getCapacity());
            System.out.println(Arrays.toString(bb.getBytes()));
//            for (int i = 0; i < b1.length; i++) {
//                assertEquals(b1[i], bb.getBytes()[i]);
//            }
//            assertEquals(b1, bb.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

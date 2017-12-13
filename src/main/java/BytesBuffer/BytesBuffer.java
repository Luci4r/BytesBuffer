package BytesBuffer;

import java.util.Arrays;

/**
 * Created by ZZ on 17/12/13.
 */
public class BytesBuffer {
    private byte[] buffer;
    private int size;
    private int capacity;
    private static final int defaultCapacity = 1024 * 1024;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public BytesBuffer() {
        this(defaultCapacity);
    }

    public BytesBuffer(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.buffer = new byte[this.capacity];
    }

    public BytesBuffer append(byte[] b) throws Exception {
        int len = b.length;
        if (size + len > capacity) {
//            throw new Exception("OutOfMemory");
            //todo how to cal new capacity
            capacity = capacity + len;
        }
        if (capacity > Integer.MAX_VALUE - 8) {
            throw new OutOfMemoryError();
        }
        buffer = Arrays.copyOf(buffer, capacity);
        return this;
    }

    public int indexOf(byte dst) {
        return -1;
    }

    public int indexOf(byte[] dst) {
        return -1;
    }

    public BytesBuffer delete(int start, int end) {
        return this;
    }
}

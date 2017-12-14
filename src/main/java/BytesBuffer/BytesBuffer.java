package BytesBuffer;

import java.util.Arrays;

/**
 * Created by ZZ on 17/12/13.
 */
public class BytesBuffer {
    private byte[] buffer;
    private int size;
    private int capacity;
    private static final int defaultCapacity = 0;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public BytesBuffer() {
        this(defaultCapacity);
    }

    public BytesBuffer(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.buffer = new byte[this.capacity];
    }

    public byte[] getBytes() {
        return Arrays.copyOf(buffer, size);
//        return buffer;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
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
//        int oriSize = buffer.length;
//        System.arraycopy(b, 0, buffer, );
        buffer = Arrays.copyOf(buffer, capacity);
//        byte[] oldBuf = buffer;
//        buffer = new byte[capacity];
        System.arraycopy(b, 0, buffer, size, len);
        size += len;
//        StringBuilder
//                buffer = Arrays.copyOf(buffer, capacity);
        return this;
    }

    static int byteBufferIndexOf(byte[] source, int sourceOffset, int sourceCount,
                                 byte[] target, int targetOffset, int targetCount,
                                 int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        byte first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
//            if (source[i] != first) {
//                while (++i <= max && source[i] != first) ;
//            }
            /* Look for first byte. */
            if (Byte.compare(first, source[i]) != 0) {
                while (++i <= max && Byte.compare(first, source[i]) != 0) ;
            }

            /* Found first byte, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && Byte.compare(source[j], target[k]) == 0; j++, k++) ;
                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
//            if (i <= max) {
//                int j = i + 1;
//                int end = j + targetCount - 1;
//                for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++) ;
//                if (j == end) {
//                    /* Found whole string. */
//                    return i - sourceOffset;
//                }
//            }
        }
        return -1;
    }

    public int indexOf(byte dst) {
        int i;
        for (i = 0; i < size && Byte.compare(dst, buffer[i]) != 0; i++) ;
        return i == size ? -1 : i;
    }

    public int indexOf(byte[] dst) {
        return byteBufferIndexOf(buffer, 0, size, dst, 0, dst.length, 0);
    }

    public byte[] subBytes(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > size)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return Arrays.copyOfRange(buffer, start, end);
    }

    /*
    * delete from start with size delSize
    * */
    public BytesBuffer sizeDelete(int start, int delSize) {
        if (start < 0) {
            throw new IndexOutOfBoundsException(start + " < 0");
        }
        if (delSize > size - start) {
            delSize = size - start;
        }
        //do not delete index of end
//        int len = end - start;
//        System.out.println(start + " " + end + " " + len);
        if (delSize > 0) {
//            System.out.println(Arrays.toString(buffer));
            System.arraycopy(buffer, start + delSize, buffer, start, size - (start + delSize));
//            System.out.println(Arrays.toString(buffer));
            size -= delSize;
        }
        return this;
    }

    /*
    * delete [start,end)
    * */
    public BytesBuffer delete(int start, int end) {
        if (start < 0) {
            throw new IndexOutOfBoundsException(start + " < 0");
        }
        if (end > size) {
            end = size;
        }
//        System.out.println(buffer.length);
        if (start > end) {
            throw new IndexOutOfBoundsException(start + " > " + end);
        }
        //do not delete index of end
        int len = end - start;
//        System.out.println(start + " " + end + " " + len);
        if (len > 0) {
//            System.out.println(Arrays.toString(buffer));
            System.arraycopy(buffer, start + len, buffer, start, size - end);
//            System.out.println(Arrays.toString(buffer));
            size -= len;
        }
        return this;
    }

    public BytesBuffer pop(int start, int end) {
        return this;
    }
}

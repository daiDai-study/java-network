package org.daistudy.network.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TestByteBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c'});
        buffer.flip();

        buffer.get(new byte[2]);

        buffer.compact();
        ByteBufferUtil.debugBuffer(buffer);
    }

    public static void m1(String[] args) {
        ByteBuffer buffer = Charset.defaultCharset().encode("abcd");
        ByteBufferUtil.debugBuffer(buffer);
    }
}

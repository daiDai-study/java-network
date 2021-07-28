package org.daistudy.network.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferExam {

    public static void main(String[] args) {
        /*
        网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
        但由于某种原因，这些数据在接收时，被进行了重新组合
        例如
        原始数据有 3 条为：
            Hello, world\n
            I'm zhangsan\n
            How are you?\n
        变成了下面的两个 ByteBuffer (黏包，半包)
            Hello, world\nI'm zhangsan\nHo
            w are you?\n
        现在要求编写程序，将错乱的数据恢复成原始的数据
         */

        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello, world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source){
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer buffer = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    buffer.put(source.get());
                }
                ByteBufferUtil.debugBuffer(buffer);
            }
        }
        source.compact();
    }
}

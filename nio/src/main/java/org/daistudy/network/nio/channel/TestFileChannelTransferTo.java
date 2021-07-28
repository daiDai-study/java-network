package org.daistudy.network.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
            FileChannel from = new FileInputStream("data/nio-data.txt").getChannel();
            FileChannel to = new FileOutputStream("data/to.txt").getChannel()
        ) {
            // 如果原始文件数据太大，需要分批复制
            long size = from.size();
            long left = size;
            while(left > 0){
                System.out.println("position: " + (size - left) + ", left: " + left);
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

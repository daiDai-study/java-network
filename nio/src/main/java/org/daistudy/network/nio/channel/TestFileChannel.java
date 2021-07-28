package org.daistudy.network.nio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestFileChannel {

    public static void main(String[] args) throws IOException {
        try (FileChannel channel = new FileInputStream("data/nio-data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (true) {
                int read = channel.read(buffer);
                log.debug("read num: {}", read);
                if(read == -1){
                    break;
                }

                buffer.flip();


                while (buffer.hasRemaining()) {
                    log.debug("content: {}", StandardCharsets.UTF_8.decode(buffer).toString());
                }

                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

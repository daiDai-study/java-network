package org.daistudy.network.nio.socket;

import lombok.extern.slf4j.Slf4j;
import org.daistudy.network.nio.buffer.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ServerBlocking {
    public static void main(String[] args) throws IOException {
        // 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 绑定端口
        ssc.bind(new InetSocketAddress(8080));

        ByteBuffer buffer = ByteBuffer.allocate(16);
        List<SocketChannel> scList = new ArrayList<>();
        while(true){
            try{
                // 接收连接
                log.debug("connecting... {}", ssc);
                SocketChannel sc = ssc.accept(); // 阻塞
                if(sc != null){
                    log.debug("connected... {}", sc);
                    scList.add(sc);
                }

                // 处理连接
                for (SocketChannel channel : scList) {
                    log.debug("before read... {}", channel);
                    int read = channel.read(buffer); // 阻塞
                    System.out.println("read: " + read);
                    if(read == 0){

                    }else{
                        buffer.flip();
                        ByteBufferUtil.debugBuffer(buffer);
                        buffer.clear();
                    }
                    log.debug("after read... {}", channel);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

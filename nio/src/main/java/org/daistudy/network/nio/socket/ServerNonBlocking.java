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
public class ServerNonBlocking {
    public static void main(String[] args) throws IOException {
        // 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 设置非阻塞
        ssc.configureBlocking(false);

        // 绑定端口
        ssc.bind(new InetSocketAddress(8080));

        ByteBuffer buffer = ByteBuffer.allocate(16);
        List<SocketChannel> scList = new ArrayList<>();
        while(true){
            try{
                // 接收连接
                SocketChannel sc = ssc.accept(); // 非阻塞
                if(sc != null){
                    log.debug("connected... {}", sc);
                    // 设置非阻塞
                    sc.configureBlocking(false);
                    scList.add(sc);
                }

                // 处理连接
                for (SocketChannel channel : scList) {
                    int read = channel.read(buffer); // 非阻塞
                    if(read == 0){

                    }else{
                        buffer.flip();
                        ByteBufferUtil.debugBuffer(buffer);
                        buffer.clear();
                        log.debug("after read... {}", channel);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

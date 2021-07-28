package org.daistudy.network.nio.socket;

import lombok.extern.slf4j.Slf4j;
import org.daistudy.network.nio.buffer.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ServerSelector {
    public static void main(String[] args) throws IOException {
        // 创建 selector
        Selector selector = Selector.open();

        // 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置非阻塞
        ssc.configureBlocking(false);
        // 绑定端口
        ssc.bind(new InetSocketAddress(8080));

        // 服务器 Socket 注册进 selector，并关注接收连接事件，不需要附加任何附件
        ssc.register(selector, SelectionKey.OP_ACCEPT, null);

        log.debug("{}", ssc);


        while (true) {
            // 接收事件，没有事件发生时会阻塞，有事件发生时向下执行
            selector.select();

            // 处理事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                // 服务器端发生接收连接事件
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    log.debug("{}", channel);

                    SocketChannel sc = channel.accept();
                    log.debug("{}", sc);
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ, null);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    log.debug("{}", channel);

                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        if (read == -1) {
                            // 客户端正常断开，也会出发可读事件，read 结果返回 -1
                            key.cancel();
                            log.debug("client {} closed.", channel);
                        } else {
                            buffer.flip();
                            ByteBufferUtil.debugBuffer(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        key.cancel();
                    }

                }
            }

        }
    }
}

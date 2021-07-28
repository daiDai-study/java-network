package org.daistudy.network.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferUtil {

    public static void debugBuffer(ByteBuffer buffer) {
        System.out.println(firstLine());
        System.out.println(positionAndLimit(buffer));
        System.out.println(lineOfHead());
        System.out.println(infoOfHead());
        System.out.println(lineOfContent());
        System.out.println(infoOfContent(buffer));
        System.out.println(lineOfContent());
    }

    private static String firstLine() {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (int i = 0; i < 8; i++) {
            builder.append("-");
        }
        builder.append("+");
        for (int i = 0; i < 16; i++) {
            if (i == 7) {
                builder.append("- a");
            } else if (i == 8) {
                builder.append("ll ");
            } else {
                builder.append("---");
            }
        }
        builder.append("-+");
        for (int i = 0; i < 16; i++) {
            builder.append("-");
        }
        builder.append("+");
        return builder.toString();
    }

    private static String positionAndLimit(ByteBuffer buffer) {
        return "position: [" + buffer.position() + "], limit: [" + buffer.limit() + "], capacity: [" + buffer.capacity() + "]";
    }

    private static String lineOfHead() {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        for (int i = 0; i < 8; i++) {
            builder.append(" ");
        }
        builder.append("+");
        for (int i = 0; i < 16; i++) {
            builder.append("---");
        }
        builder.append("-+");
        for (int i = 0; i < 16; i++) {
            builder.append(" ");
        }
        builder.append(" ");
        return builder.toString();
    }

    private static String lineOfContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (int i = 0; i < 8; i++) {
            builder.append("-");
        }
        builder.append("+");
        for (int i = 0; i < 16; i++) {
            builder.append("---");
        }
        builder.append("-+");
        for (int i = 0; i < 16; i++) {
            builder.append("-");
        }
        builder.append("+");
        return builder.toString();
    }

    private static String infoOfHead() {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        for (int i = 0; i < 8; i++) {
            builder.append(" ");
        }
        builder.append("|");
        for (int i = 0; i < 16; i++) {
            if (i < 10) {
                builder.append("  ").append(i);
            } else {
                builder.append("  ").append((char) (i - 10 + 'a'));
            }
        }
        builder.append(" |");
        for (int i = 0; i < 16; i++) {
            builder.append(" ");
        }
        builder.append(" ");
        return builder.toString();
    }

    private static String infoOfContent(ByteBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (int i = 0; i < 8; i++) {
            builder.append("0");
        }
        builder.append("|");
        int limit = buffer.limit();
        int capacity = buffer.capacity();
        for (int i = 0; i < 16; i++) {
            if (i < limit) {
                byte b = buffer.get(i);
                builder.append(" ").append(byteToHex(b));
            } else if (i < capacity) {
                builder.append(" ").append(byteToHex((byte) 0));
            } else {
                builder.append("   ");
            }
        }
        builder.append(" |");
        for (int i = 0; i < 16; i++) {
            if (i < limit) {
                char c = (char) buffer.get(i);
                if (c == '\n') {
                    builder.append(".");
                } else {
                    builder.append(c);
                }
            } else if (i < capacity) {
                builder.append(".");
            } else {
                builder.append(" ");
            }
        }
        builder.append("|");
        return builder.toString();
    }

    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }
}

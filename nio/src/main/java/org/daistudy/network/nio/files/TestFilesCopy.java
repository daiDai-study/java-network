package org.daistudy.network.nio.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFilesCopy {
    public static void main(String[] args) throws IOException {
        // 复制多级目录
        String source = "E:\\home\\s-plp";
        String target = "E:\\home\\s-plp-copy";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                final Path pathTarget = Paths.get(path.toString().replace(source, target));
                if(Files.isDirectory(path)){
                    // 创建目录
                    Files.createDirectory(pathTarget);
                }else if(Files.isRegularFile(path)){
                    // 复制文件
                    Files.copy(path, pathTarget);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}

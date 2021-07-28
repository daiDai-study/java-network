package org.daistudy.network.nio.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {
        // 删除有内容的目录
        Files.walkFileTree(Paths.get("E:\\home"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    public static void m2(String[] args) throws IOException {
        final AtomicInteger javaCount = new AtomicInteger(0);
        Files.walkFileTree(Paths.get("E:\\my_projects\\java-network"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(file.toString().endsWith(".java")){
                    System.out.println(file);
                    javaCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("javaCount: " + javaCount);
    }

    public static void m1(String[] args) throws IOException {
        final AtomicInteger dirCount = new AtomicInteger(0);
        final AtomicInteger fileCount = new AtomicInteger(0);
        Files.walkFileTree(Paths.get("E:\\my_projects\\java-network"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("===> " + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("<=== " + dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println("dirCount: " + dirCount + ", fileCount: " + fileCount);
    }
}

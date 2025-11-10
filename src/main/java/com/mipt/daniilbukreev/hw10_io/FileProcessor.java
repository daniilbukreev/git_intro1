package com.mipt.daniilbukreev.hw10_io;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

public class FileProcessor {
    public List<Path> splitFile(String sourcePath, String outputDir, int partSize) throws IOException {
        List<Path> partPaths = new ArrayList<>();
        Path sourceFile = Paths.get(sourcePath);
        Path outputDirectory = Paths.get(outputDir);

        String fileName = sourceFile.getFileName().toString();
        try (FileChannel sourceChannel = FileChannel.open(sourceFile, StandardOpenOption.READ)) {
            long fileSize = sourceChannel.size();
            long bytesRead = 0;
            int partNumber = 1;

            ByteBuffer buffer = ByteBuffer.allocate(partSize);
            while (bytesRead < fileSize) {
                String partName = fileName + ".part" + partNumber;
                Path partPath = outputDirectory.resolve(partName);

                try (FileChannel destChannel = FileChannel.open(partPath,
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                    buffer.clear();
                    int bytesReadThisPart = 0;

                    while (bytesReadThisPart < partSize && bytesRead < fileSize) {
                        int read = sourceChannel.read(buffer);
                        if (read == -1){
                            break;
                        }
                        bytesRead += read;
                        bytesReadThisPart += read;
                        buffer.flip();
                        destChannel.write(buffer);
                        buffer.clear();
                    }
                    partPaths.add(partPath);
                    partNumber++;
                }
            }
        }
        return partPaths;
    }

    public void mergeFiles(List<Path> partPaths, String outputPath) throws IOException {
        Path outputFile = Paths.get(outputPath);

        for (Path part: partPaths) {
            if (!Files.exists(part)) {
                throw new FileNotFoundException("Part file not found: " + part);
            }
        }

        try (FileChannel destChannel = FileChannel.open(outputFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            for (Path part: partPaths) {
                try (FileChannel sourceChannel = FileChannel.open(part, StandardOpenOption.READ)) {
                    long bytesTransferred = 0;
                    long partSize = Files.size(part);

                    while (bytesTransferred < partSize) {
                        buffer.clear();
                        int read = sourceChannel.read(buffer);
                        if (read == -1){
                            break;
                        }
                        buffer.flip();
                        destChannel.write(buffer);
                        bytesTransferred += read;
                    }
                }
            }
        }
    }
}

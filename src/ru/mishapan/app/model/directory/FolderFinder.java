package ru.mishapan.app.model.directory;

import ru.mishapan.app.model.file.FileWorker;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FolderFinder extends SimpleFileVisitor<Path> {

    private List<Path> pathList;
    private String folderName;

    public FolderFinder() {
        pathList = new ArrayList<>();
    }

    public List<Path> getPathList() {
        return pathList;
    }

    private void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public List<Path> findFolders(String folderName, Path startDir, FileVisitor<Path> visitor) throws IOException {

        setFolderName(folderName);

        Files.walkFileTree(startDir, visitor);

        return getPathList();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        try {
            if (Files.isHidden(file) || !Files.isReadable(file) || !Files.isWritable(file)) {
                return FileVisitResult.SKIP_SUBTREE;
            }
        } catch (IOException ex) { ex.printStackTrace();
        }
        return FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {

        if (attrs.isDirectory() && dir.getFileName().toString().equals(getFolderName())) {
            pathList.add(dir);
        }
        return FileVisitResult.CONTINUE;
    }

}

package ru.mishapan.app.model.directory;

import ru.mishapan.app.model.file.FileFinder;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/*TODO
 Задание: найти папку, заданную пользователем, и построить дерево каталогов
 1) Разобраться с символьными ссылками
 2) подумать над возвращаемым значением createTree
 */
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

    public List<Path> findFolders(String folderName, Path startDir, FileVisitor<Path> visitor) {

        setFolderName(folderName);

        try {
            Files.walkFileTree(startDir, visitor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return getPathList();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {

        if (attrs.isDirectory() && dir.getFileName().toString().equals(getFolderName())) {
            pathList.add(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) {

        System.out.println("\n\n");

        FolderFinder dc = new FolderFinder();

        Path startDir = Paths.get("C:/Users/Михаил/Desktop/");

        FileFinder fileFinder = new FileFinder();

        String text = "Error 404:";

        dc.findFolders("findMe", startDir, dc).forEach(path -> {

            List<Path> list = fileFinder.findFiles(path, "*.*");
            if (list.size() > 0) {
                fileFinder.findTextInFiles(list, text).forEach(System.out::println);
            }
        });

    }
}

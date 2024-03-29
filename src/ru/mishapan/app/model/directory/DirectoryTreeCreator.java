package ru.mishapan.app.model.directory;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Создает дерево папок
 */
public class DirectoryTreeCreator {

    /**
     * Разделяет заданный путь на отдельный папки.
     *
     * @param path путь к папке
     * @return массив строк, содержащий папки, начиная с корневой
     */
    public String[] createTree(Path path) {

        if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
            throw new InvalidPathException(path.toString(), "Can not find folder by this path");
        }
        String[] folders = new String[path.getNameCount() + 1];

        folders[0] = path.getRoot().toString();

        for (int i = 0, j = 1; i < path.getNameCount(); i++, j++) {
            folders[j] = path.getName(i).toString();
        }

        return folders;
    }

}

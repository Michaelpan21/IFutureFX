package ru.mishapan.app.model.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с файлами
 */
public class FileWorker {

    /**
     * Ищет файлы с указанным расширением в указанной директории.
     * Если совпадений не найдено, возвращается пустой список.
     *
     * @param path      путь к директории, где будет происходить поиск файлов с указанным разширением
     * @param extension разширение файла
     * @return возвращает список типа ArrayList<Path>, содержащий пути найденных файлов
     * @throws IOException при неверно указанном(несуществующем) пути, невозможности ооздать поток
     */
    public List<Path> findFiles(Path path, String extension) throws IOException {

        if (extension == null) {
            extension = "*.*";
        }

        List<Path> filesList = new ArrayList<>();

        Iterable<Path> stream = Files.newDirectoryStream(path, extension);
        stream.forEach(filesList::add);

        return filesList;
    }

    /**
     * Ищет указанный текст в указанных файлах, если текст не указан, возвращается исходный список.
     * Если совпадений не найдено, возвращается пустой список.
     *
     * @param filesList список List<Path>, содержаший пути к файлам
     * @param text      текст, который необходимо найти
     * @return список List<Path>, содержаший пути к файлам, в которых найден заданный текст
     */
    public List<Path> findTextInFiles(List<Path> filesList, String text) {

        if (filesList.size() == 0) {
            throw new IllegalArgumentException("No satisfying files in current directory!");
        }

        if (text.equals("")) {
            return filesList;
        }

        char[] textChars = text.toCharArray();

        List<Path> filesWithMatches = new ArrayList<>();

        filesList.forEach(path -> {
            try (BufferedReader bf = new BufferedReader(new FileReader(path.toString()))) {

                String fileLine;
                int itr = 0;
                boolean toAdd = false;

                while ((fileLine = bf.readLine()) != null) {

                    char[] fileChars = fileLine.toCharArray();
                    for (int i = 0; i < fileChars.length; i++) {

                        if (fileChars[i] == textChars[itr]) {

                            if (textChars.length == 1) {
                                if (i != fileChars.length - 1 && isEndOfTheWord(fileChars[i + 1])) {
                                    toAdd = true;
                                }
                                break;
                            }

                            itr++;
                            for (int j = i + 1; j < fileChars.length; j++) {

                                if (fileChars[j] != textChars[itr]) {
                                    itr = 0;
                                    break;
                                }

                                if (itr == textChars.length - 1) {
                                    if (j != fileChars.length - 1 && isEndOfTheWord(fileChars[j + 1])) {
                                        toAdd = true;
                                    }
                                    i = fileChars.length - 1;
                                    itr = 0;
                                    break;
                                }

                                if (j == fileChars.length - 1) {
                                    i = fileChars.length - 1;
                                    itr++;
                                    break;
                                }
                                itr++;
                            }
                        }
                    }
                }

                if (toAdd) {
                    filesWithMatches.add(path);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return filesWithMatches;
    }

    /**
     * Проверяется является ли символ разделителем между словами.
     *
     * @param symbol символ
     * @return true, если является.
     */
    private boolean isEndOfTheWord(char symbol) {
        if (symbol == ' ') return true;
        String symbols = ".,/?<>;:'[]{}~`@#$%^&*()-_+=\\|*\"№!\n";
        return symbols.contains(Character.toString(symbol));
    }
}

package ru.mishapan.app.model.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для работы с файлами
 */
public class FileWorker {

    private Map<String, List<Integer>> linesWithMatchesMap = new HashMap<>();

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

        List<Path> filesWithMatches = new ArrayList<>();
        Pattern pattern = Pattern.compile(text.toLowerCase());

        filesList.forEach(path -> {

            try (BufferedReader bf = new BufferedReader(new FileReader(path.toString()))) {

                String fileLine;
                int lineCounter = 0;
                List<Integer> linesWithMatches = new ArrayList<>();

                while ((fileLine = bf.readLine()) != null) {
                    Matcher matcher = pattern.matcher(fileLine.toLowerCase());
                    if (matcher.find()) {
                        if (!filesWithMatches.contains(path)) {
                            filesWithMatches.add(path);
                        }
                        linesWithMatches.add(lineCounter);
                    }
                    lineCounter++;
                }

                if (linesWithMatches.size() != 0) {
                    linesWithMatchesMap.put(path.toString(), linesWithMatches);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return filesWithMatches;
    }

    public Map<String, List<Integer>> getLinesWithMatchesMap() {
        return linesWithMatchesMap;
    }
}

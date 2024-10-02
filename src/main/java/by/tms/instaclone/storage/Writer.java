package by.tms.instaclone.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static by.tms.instaclone.storage.KeeperConstants.ERROR_IO_FILE_TEMPLATE;
import static by.tms.instaclone.storage.KeeperConstants.ERROR_TEMPLATE;
import static by.tms.instaclone.utilites.SiteLogger.getLogger;

public class Writer {

    /**
     * Метод производит запись в nameFile одной(!) строки (свойства Сущности, например)
     *
     * @param nameFile - имя файла (с путём), в который производится сохранение строки
     * @param rowText  - сохраняемая строка (добавляется в хвост файла)
     *                 <p>
     *                 запись в файл производится в отдельном потоке
     */
    public static void writeCsvFile(String nameFile, String rowText) {
        // todo пробую решить проблему с путями файла
        ClassLoader classLoader = Writer.class.getClassLoader();    // todo с таким решением работа идёт с файлами из target
        File csvFile = new File(Objects.requireNonNull(classLoader.getResource(nameFile)).getFile());
        try {
//                Files.write(Paths.get(nameFile), rowText.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(csvFile.toString()), rowText.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
// todo решить: логгер или исключение?
            getLogger().addRecord(ERROR_TEMPLATE.formatted(ERROR_IO_FILE_TEMPLATE.formatted(nameFile)));
        }
    }
}
package by.tms.instaclone.storage;

import by.tms.instaclone.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static by.tms.instaclone.storage.KeeperConstants.*;
import static by.tms.instaclone.storage.Reader.readCsvFile;

public class UsernamesStorage {
    private static UsernamesStorage usernamesStorage;
    private ConcurrentHashMap<String, User> usernames;

    public static synchronized UsernamesStorage getInstance() {
        if (usernamesStorage == null) {
            usernamesStorage = new UsernamesStorage();
        }
        return usernamesStorage;
    }

    public ConcurrentHashMap<String, User> getUsernames() {
        return usernames;
    }

    public void newUser(User user) {
        usernames.put(user.getUsername(), user);
    }

    private UsernamesStorage() {
        usernames = new ConcurrentHashMap<>();
        Optional<String> fileString = readCsvFile(USERS_CSV_FILE);
        if (fileString.isPresent()) {
            String[] arrayRows = fileString.get().split(LF);   // делим csv-файл на строки по LF ("перевод каретки")
            for (String row : arrayRows) {
                String[] arrayWords = row.split(SEPARATOR_CSV);   // делим строку на "слова" по SEPARATOR_CSV
                usernames.put(arrayWords[2], new User(UUID.fromString(arrayWords[0]), arrayWords[1], arrayWords[2], arrayWords[3],
                        LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(arrayWords[4])), ZoneId.systemDefault())));
            }
        }
    }
}

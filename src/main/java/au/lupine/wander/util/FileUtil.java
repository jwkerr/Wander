package au.lupine.wander.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import au.lupine.wander.Wander;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

public class FileUtil {

    public static final Path ROOT_DIRECTORY = Paths.get(Wander.getInstance().getDataFolder().getAbsolutePath());
    public static final Path CONFIG_DIRECTORY = ROOT_DIRECTORY.resolve("config");

    public static JsonElement loadJsonElementFromFile(Path path, String fileName) {
        Path filePath = path.resolve(Path.of(fileName));

        if (!Files.exists(filePath)) return null;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath.toFile()));
        } catch (FileNotFoundException e) {
            return null;
        }

        return JsonParser.parseReader(reader);
    }

    public static void saveBytesToFile(byte[] bytes, Path path, String fileName) {
        createDirectory(path);
        Path filePath = path.resolve(Path.of(fileName));

        try {
            try {
                Files.write(filePath, bytes, StandardOpenOption.CREATE_NEW);
            } catch (FileAlreadyExistsException ignored) {}
        } catch (IOException e) {
            Wander.logSevere("Something went wrong while saving a file named " + fileName + ": " + e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                Wander.logInfo(element.toString());
            }
        }
    }

    public static void createDirectory(Path path) {
        if (Files.exists(path)) return;

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            Wander.logSevere("Something went wrong while creating a directory\n" + e);
        }
    }
}

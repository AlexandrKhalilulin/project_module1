package package1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class PathHandler {
    private static final String ENTER_PATH_SOURCE_FILE = "Введите полный путь к файлу с исходным текстом и его имя:";
    private static final String ENTER_PATH_DEST_DIR = "Введите путь к директории где будет создан файл с результатом работы программы:";
    private static final String ENTER_PATH_DEST_FILE = "Введите имя файла с результатом работы программы:";
    private static final String ENTER_PATH_AUXILIARY_FILE = "Введите полный путь к файлу с вспомогательным текстом и его имя:";
    private static final String PATH_IS_NOT_A_DIRECTORY = "По указанному пути не директории";
    private static final String PATH_NOT_EXIST = "Указанный путь не существует";
    private static final String PATH_IS_NOT_A_FILE = "По указанному пути нет файла";


    public Path readPathToNewFile() {
        System.out.println(ENTER_PATH_DEST_DIR);
        Scanner scanner = new Scanner(System.in);
        String stringDir = scanner.nextLine();
        if (!checkDirectoryExist(Path.of(stringDir))) {
            readPathToNewFile();
        }
        System.out.println(ENTER_PATH_DEST_FILE);
        String stringFileName = scanner.nextLine();
        return Path.of(stringDir + stringFileName);
    }

    public Path readExistFilePath() {
        System.out.println(ENTER_PATH_SOURCE_FILE);
        return checkFileExist();
    }

    public Path checkFileExist() {
        Path resultPath = null;
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        if (Files.exists(Path.of(string))) {
            if (Files.isRegularFile(Path.of(string))) {
                resultPath = Path.of(string);
            } else {
                System.out.println(PATH_IS_NOT_A_FILE);
                checkFileExist();
            }
        } else {
            System.out.println(PATH_NOT_EXIST);
            checkFileExist();
        }
        return resultPath;
    }

    public boolean checkDirectoryExist(Path path) {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) return true;
            else {
                System.out.println(PATH_IS_NOT_A_DIRECTORY);
            }
        } else {
            System.out.println(PATH_NOT_EXIST);
        }
        return false;
    }

    public Path readExistAuxFilePath() {
        System.out.println(ENTER_PATH_AUXILIARY_FILE);
        return checkFileExist();
    }
}

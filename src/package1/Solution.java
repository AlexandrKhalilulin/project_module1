package package1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {
    private static Path pathSource;
    private static Path pathDest;
    private static Path pathAux;
    private static int key;
    private static final String SOURCE_FILE = "Введите полный путь к файлу с исходным текстом и его имя:";
    private static final String DEST_FILE = "Введите полный путь к файлу для записи результата и его имя:";
    private static final String AUXILIARY_FILE = "Введите полный путь к файлу с вспомогательным текстом и его имя:";

    public static void main(String[] args) {
        greetings();
        selectOperatingMode();
    }

    static void greetings() {
        System.out.println("Доброго времени суток. Данная программа выполняет шифрование и расшифровку текстов в русском алфавите по шифру Цезаря.");
    }

    static void selectOperatingMode() {
        System.out.println("Выберите режим работы программы:");
        System.out.println("1. Шифрование файла с помощью ключа.");
        System.out.println("2. Дешифрование файла с помощью ключа.");
        System.out.println("3. Дешифрование файла брут-форсом.");
        System.out.println("4. Дешифрование файла методом статического анализа.");
        int selectResult = readNumberFromConsole();
        Cryptologist cryptologist = new Cryptologist();
        switch (selectResult) {
            case 1 -> {
                System.out.println(SOURCE_FILE);
                pathSource = enterExistFilePath();
                System.out.println(DEST_FILE);
                pathDest = enterNewFilePath();
                System.out.println("Введите ключ:");
                key = readNumberFromConsole();
                cryptologist.encryptFileWithKey(key, pathSource, pathDest);
            }
            case 2 -> {
                System.out.println(SOURCE_FILE);
                pathSource = enterExistFilePath();
                System.out.println(DEST_FILE);
                pathDest = enterNewFilePath();
                System.out.println("Введите ключ:");
                key = readNumberFromConsole();
                key = key - key * 2; //меняем ключ на обратное значение для расшифровки
                cryptologist.encryptFileWithKey(key, pathSource, pathDest);
            }
            case 3 -> {
                System.out.println(SOURCE_FILE);
                pathSource = enterExistFilePath();
                System.out.println(DEST_FILE);
                pathDest = enterNewFilePath();
                cryptologist.encryptFileBrutForce(pathSource, pathDest);
            }
            case 4 -> {
                System.out.println(SOURCE_FILE);
                pathSource = enterExistFilePath();
                System.out.println(AUXILIARY_FILE);
                pathAux = enterExistFilePath();
                System.out.println(DEST_FILE);
                pathDest = enterNewFilePath();
                cryptologist.encryptFileStatic(pathSource, pathAux, pathDest);
            }
            default -> System.out.println("Неверно введено число");
        }
    }

    static int readNumberFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return Integer.parseInt(s);
    }

    static Path enterExistFilePath() {
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        if (checkFileExist(path)) {
            return path;
        }
        return null;
    }

    static Path enterNewFilePath() {
        Scanner scanner = new Scanner(System.in);
        return Path.of(scanner.nextLine());
    }

    public static boolean checkFileExist(Path path) {
        HashSet<Character> characters = new HashSet<>();
        if (!Files.exists(path)) {
            System.out.println("Файл по указанному пути не существует.");
        } else {
            if (Files.isRegularFile(path)) {
                System.out.println("Найден файл.");
                return true;
            } else {
                System.out.println("Найдена директория. Файл отсутствует.");
            }
        }
        return false;
    }
}


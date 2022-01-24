package package1;

import java.nio.file.Path;
import java.util.Scanner;

public class Solution {
    private static Path pathSource;
    private static Path pathDest;
    private static Path pathAux;
    private static int key;
    private static final String SOURCE_FILE = "Введите полный путь к файлу с исходным текстом и его имя:";
    private static final String DEST_FILE = "Введите полный путь к файлу для записи результата и его имя:";
    private static final String AUXILIARY_FILE = "Введите полный путь к файлу с вспомогательным текстом и его имя:";
    private static final String ENTER_KEY = "Введите ключ шифрования:";
    private static final String GOODBAY = "Спасибо за работу с программой. До встречи!";

    public static void main(String[] args) {
        outputGreetings();
        outputOperatingModes();
        selectOperatingModes();
    }

    static void outputGreetings() {
        System.out.println("Доброго времени суток. Данная программа выполняет шифрование и расшифровку текстов в русском алфавите по шифру Цезаря.");
    }

    static void outputOperatingModes() {
        System.out.println("Выберите режим работы программы:");
        System.out.println("1. Шифрование файла с помощью ключа.");
        System.out.println("2. Дешифрование файла с помощью ключа.");
        System.out.println("3. Дешифрование файла брут-форсом.");
        System.out.println("4. Дешифрование файла методом статического анализа.");
        System.out.println("0. Выход из программы.");
    }

    static void selectOperatingModes() {
        Cryptologist cryptologist = new Cryptologist();
        PathHandler pathHandler = new PathHandler();
        int selectResult = readNumberFromConsole();
        switch (selectResult) {
            case 1 -> {
                workOnFirstSelection(cryptologist, pathHandler);
            }
            case 2 -> {
                workOnSecondSelection(cryptologist, pathHandler);
            }
            case 3 -> {
                workOnThirdSelection(cryptologist, pathHandler);
            }
            case 4 -> {
                workOnFourSelection(cryptologist, pathHandler);
            }
            case 0 -> {
                System.out.println(GOODBAY);
            }
            default -> {
                System.out.println("Введите число в диапазоне от 0 до 5");
                selectOperatingModes();
            }
        }
    }

    private static void workOnFourSelection(Cryptologist cryptologist, PathHandler pathHandler) {
        System.out.println(SOURCE_FILE);
        pathSource = pathHandler.readExistFilePath();
        System.out.println(AUXILIARY_FILE);
        pathAux = pathHandler.readExistFilePath();
        System.out.println(DEST_FILE);
        pathDest = pathHandler.readPathToNewFile();
        cryptologist.encryptFileStatic(pathSource, pathAux, pathDest);
    }

    private static void workOnThirdSelection(Cryptologist cryptologist, PathHandler pathHandler) {
        System.out.println(SOURCE_FILE);
        pathSource = pathHandler.readExistFilePath();
        System.out.println(DEST_FILE);
        pathDest = pathHandler.readPathToNewFile();
        cryptologist.encryptFileBrutForce(pathSource, pathDest);
    }

    private static void workOnSecondSelection(Cryptologist cryptologist, PathHandler pathHandler) {
        System.out.println(SOURCE_FILE);
        pathSource = pathHandler.readExistFilePath();
        System.out.println(DEST_FILE);
        pathDest = pathHandler.readPathToNewFile();
        System.out.println(ENTER_KEY);
        key = readNumberFromConsole();
        cryptologist.decryptFileWithKey(key, pathSource, pathDest);
    }

    private static void workOnFirstSelection(Cryptologist cryptologist, PathHandler pathHandler) {
        System.out.println(SOURCE_FILE);
        pathSource = pathHandler.readExistFilePath();
        System.out.println(DEST_FILE);
        pathDest = pathHandler.readPathToNewFile();
        System.out.println(ENTER_KEY);
        key = readNumberFromConsole();
        cryptologist.encryptFileWithKey(key, pathSource, pathDest);
    }

    static int readNumberFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        int number = Integer.MIN_VALUE;
        if (new Scanner(string).hasNextInt()) {
            number = Integer.parseInt(string);
        }
        return number;
    }



}


package package1;

import java.nio.file.Path;
import java.util.Scanner;

public class Solution {
    private static Path pathSource;
    private static Path pathDest;
    private static Path pathAux;
    private static int key;
    private static final String ENTER_KEY = "Введите ключ шифрования  в диапазоне от 1 до";
    private static final String GOODBYE = "Выбранный вами пункт выполнил свою работу. Спасибо за работу с программой. До встречи!";

    public static void main(String[] args) {
        outputGreetings();
        outputOperatingModes();
        selectOperatingModes();
        System.out.println(GOODBYE);
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
                pathSource = pathHandler.readExistFilePath();
                pathDest = pathHandler.readPathToNewFile();
                System.out.println(ENTER_KEY + " " + cryptologist.allCharsRu.length() + " :");
                key = readNumberFromConsole();
                cryptologist.encryptFileWithKey(key, pathSource, pathDest);
            }
            case 2 -> {
                pathSource = pathHandler.readExistFilePath();
                pathDest = pathHandler.readPathToNewFile();
                System.out.println(ENTER_KEY + " " + cryptologist.allCharsRu.length() + " :");
                key = readNumberFromConsole();
                cryptologist.decryptFileWithKey(key, pathSource, pathDest);
            }
            case 3 -> {
                pathSource = pathHandler.readExistFilePath();
                pathDest = pathHandler.readPathToNewFile();
                cryptologist.encryptFileBrutForce(pathSource, pathDest);
            }
            case 4 -> {
                pathSource = pathHandler.readExistFilePath();
                pathAux = pathHandler.readExistAuxFilePath();
                pathDest = pathHandler.readPathToNewFile();
                cryptologist.encryptFileStatic(pathSource, pathAux, pathDest);
            }
            case 0 -> {

            }
            default -> {
                System.out.println("Введите число в диапазоне от 0 до 5");
                selectOperatingModes();
            }
        }
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


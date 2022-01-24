package package1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;

public class PathHandler {
    public Path readPathToNewFile() {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();
        new Scanner(string).hasNextLine();

        return Path.of(scanner.nextLine());
    }

    public Path readExistFilePath() {
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        if (checkFileExistAlongPath(path)) {
            return path;
        }
        return null;
    }

    public boolean checkFileExistAlongPath(Path path) {
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

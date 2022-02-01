package package1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Cryptologist {
    //напрашивается пропертис
    String alphabetLowerCaseRu = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    String alphabetUpperCaseRu = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    String punctuationMarks = " .,:-!—?()\"";
    String digitsRu = "0123456789";
    String allCharsRu = alphabetLowerCaseRu + alphabetUpperCaseRu + digitsRu + punctuationMarks;

    public void encryptFileWithKey(int key, Path sourcePath, Path destPath) {
        // создаем ориг мапу
        Map<Character, Integer> originalLinkedMap = createOriginalLinkedMap();
        // создаем смещенную мапу
        Map<Integer, Character> shiftedByKeyLinkedMap = createShiftedByKeyLinkedMap(key);
        // cчитываем исходный файл в строку
        String stringsSource = readFileContentToString(sourcePath);
        // создаем строку со смещением
        String stringsDest = replaceCharsInString(originalLinkedMap, shiftedByKeyLinkedMap, stringsSource);
        // создаем конечный файл и записываем в него строку со смещением
        createDestFileAndWriteStringInto(destPath, stringsDest);
    }

    private LinkedHashMap<Character, Integer> createOriginalLinkedMap() {
        LinkedHashMap<Character, Integer> originalMap = new LinkedHashMap<>();
        for (int i = 0; i < allCharsRu.length(); i++) {
            originalMap.put(allCharsRu.charAt(i), i);
        }
        return originalMap;
    }

    private LinkedHashMap<Integer, Character> createShiftedByKeyLinkedMap(int key) {
        LinkedHashMap<Integer, Character> shiftedMap = new LinkedHashMap<>();
        int value = 0;
        for (int i = key; i < allCharsRu.length(); i++) {
            shiftedMap.put(value++, allCharsRu.charAt(i));
        }
        for (int i = 0; i < key; i++) {
            shiftedMap.put(value++, allCharsRu.charAt(i));
        }
        return shiftedMap;
    }

    public void decryptFileBrutForce(Path sourcePath, Path destPath) {
        // создаем ориг мапу
        LinkedHashMap<Character, Integer> originalMap = createOriginalLinkedMap();
        // cчитываем исходный файл в строку
        String stringsSource = readFileContentToString(sourcePath);
        String stringsDest = "";
        LinkedHashMap<Integer, Character> shiftedByKeyMap;
        // перебираем ключ от 0 до длины строки символов
        for (int i = 0; i < allCharsRu.length(); i++) {
            // создаем мапу со смещением на i длину
            shiftedByKeyMap = createShiftedByKeyLinkedMap(i);
            // создаем строку со смещением
            stringsDest = replaceCharsInString(originalMap, shiftedByKeyMap, stringsSource);
            //проверяем строку на читаемость
            if (checkStringForReadability(stringsDest)) {
                System.out.println("Ключ шифрования: " + (allCharsRu.length() - i));
                break;
            }
        }
        // создаем конечный файл и записываем в него строки со смещением
        createDestFileAndWriteStringInto(destPath, stringsDest);
    }

    private boolean checkStringForReadability(String stringsDest) {
        char point = '.';
        char comma = ',';
        char space = ' ';
        int pointOrCommaCheckTrue = 0;
        int pointOrCommaChekFalse = 0;
        boolean flag = false;
        char[] chars = stringsDest.toCharArray();
        //набиваем кол-ва пройденых проверок на пробел после точки или запятой и кол-во не пройденных проверок
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == point) {
                if (chars[i + 1] == space) {
                    pointOrCommaCheckTrue++;
                }
                if (chars[i + 1] != space) {
                    pointOrCommaChekFalse++;
                }
            }
        }
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == comma) {
                if (chars[i + 1] == space) {
                    pointOrCommaCheckTrue++;
                }
                if (chars[i + 1] != space) {
                    pointOrCommaChekFalse++;
                }
            }
        }
        // сравниваем суммарное кол-во пройденых и не пройденных
        if (pointOrCommaCheckTrue > pointOrCommaChekFalse) flag = true;
        return flag;
    }

    private void createDestFileAndWriteStringInto(Path destPath, String stringsDest) {
        try {
            if (Files.exists(destPath)) {
                Files.delete(destPath);
            }
            Files.createFile(destPath);
            Files.writeString(destPath, stringsDest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    private Map<Character, Integer> createOriginalMap() {
        char[] chars = allCharsRu.toCharArray();
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            hashMap.put(chars[i], i);
        }
        return hashMap;
    }

    private Map<Integer, Character> createShiftedByKeyMap(int key) {
        //реализовать через линкедхэшмап и смещать по ключу оригинальную мапу или оригинальную стрингу символов
        ArrayList<Character> characterList = new ArrayList<>();
        char[] chars = allCharsRu.toCharArray();
        for (char ch : chars
        ) {
            characterList.add(ch);
        }
        ArrayList<Character> displacedListChars = new ArrayList<>();
        //дальше три строки костыля, необходимо уточнить
        for (int i = 0; i < characterList.size(); i++) {
            displacedListChars.add(i, 'a');
        }
        //костыль закончился
        Collections.copy(displacedListChars, characterList);
        Collections.rotate(displacedListChars, key);
        HashMap<Integer, Character> hashMapDisplaced = new HashMap<>();
        for (int i = 0; i < displacedListChars.size(); i++) {
            hashMapDisplaced.put(i, displacedListChars.get(i));
        }
        return hashMapDisplaced;
    }
    */

    private String readFileContentToString(Path path) {
        String string = "";
        try {
            string = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    private String replaceCharsInString(Map<Character, Integer> originalMap,
                                        Map<Integer, Character> displacedMap,
                                        String string) {
        char[] chMassive = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : chMassive
        ) {
            Integer integer = originalMap.get(ch);
            if (integer != null) {
                stringBuilder.append(displacedMap.get(integer));
            }
        }
        return stringBuilder.toString();
    }

    public void encryptFileStatic(Path pathSource, Path pathAux, Path pathDest) {
        //создаем мапы для двух текстов
        LinkedHashMap<Character, Integer> auxMap = getCharacterLowerCaseIntegerMap();
        LinkedHashMap<Character, Integer> sourceMap = getCharacterLowerCaseIntegerMap();

        //считываем содержимое файлов в стройку
        String sourceContent = getContentFromFile(pathSource).toLowerCase(Locale.ROOT);
        String auxContent = getContentFromFile(pathAux).toLowerCase(Locale.ROOT);

        //наполняем ключи мап по кол-ву вхождения символов в текст
        fillMapValues(sourceMap, sourceContent);
        fillMapValues(auxMap, auxContent);

        //реализовать сортировку мапы пузырьком

        //сортируем мапы
        LinkedHashMap<Character, Integer> sortedSourceMap =
                sourceMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        LinkedHashMap<Character, Integer> sortedAuxMap =
                auxMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));

        LinkedList<Character> arrayListCharsSource = new LinkedList<>(sortedSourceMap.keySet());

        LinkedList<Character> arrayListCharsAux = new LinkedList<>(sortedAuxMap.keySet());

        //создаем строку сравнивая две мапы
        StringBuilder stringBuilder = new StringBuilder();
        char[] stringCharsSource = sourceContent.toLowerCase(Locale.ROOT).toCharArray();
        for (char ch : stringCharsSource
        ) {
            for (int i = 0; i < arrayListCharsSource.size(); i++) {
                if (ch == arrayListCharsSource.get(i)) {
                    stringBuilder.append(arrayListCharsAux.get(i));
                }
            }
        }
        //записываем строку в файл
        createDestFileAndWriteStringInto(pathDest, stringBuilder.toString());
    }

    private void fillMapValues(LinkedHashMap<Character, Integer> map, String string) {
        int count;
        for (int i = 0; i < string.length(); i++) {
            if (map.containsKey(string.charAt(i))) {
                count = map.get(string.charAt(i)) + 1;
                map.put(string.charAt(i), count);
            }
        }
    }

    private String getContentFromFile(Path path) {
        String result = "";
        try {
            result = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private LinkedHashMap<Character, Integer> getCharacterLowerCaseIntegerMap() {
        LinkedHashMap<Character, Integer> sourceMap = new LinkedHashMap<>();
        String allCharToLowerCase = allCharsRu.toLowerCase(Locale.ROOT);
        for (int i = 0; i < allCharToLowerCase.length(); i++) {
            sourceMap.put(allCharToLowerCase.charAt(i), 0);
        }
        return sourceMap;
    }

    //данный метод создан только для удобства чтения программы
    public void decryptFileWithKey(int key, Path pathSource, Path pathDest) {
        key = allCharsRu.length() - key; //меняем ключ на обратное значение для расшифровки
        encryptFileWithKey(key, pathSource, pathDest);
    }

}

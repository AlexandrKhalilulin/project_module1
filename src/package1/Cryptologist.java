package package1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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
        Map<Character, Integer> originalMap = createOriginalMap();
        // создаем смещенную мапу
        Map<Integer, Character> shiftedByKeyMap = createShiftedByKeyMap(key);
        // cчитываем исходный файл в строку
        String stringsSource = getString(sourcePath);
        // создаем строку со смещением
        String stringsDest = replaceCharsInString(originalMap, shiftedByKeyMap, stringsSource);
        // создаем конечный файл и записываем в него строку со смещением
        createDestFile(destPath, stringsDest);
    }

    public void encryptFileBrutForce(Path sourcePath, Path destPath) {
        // создаем ориг мапу
        Map<Character, Integer> originalMap = createOriginalMap();
        // cчитываем исходный файл в строку
        String stringsSource = getString(sourcePath);
        String stringsDest = "";
        Map<Integer, Character> shiftedByKeyMap = new HashMap<>();
        // перебираем ключ от 0 до длины строки символов
        int count = allCharsRu.length();
        for (int i = 0; i < count; i++) {
            // создаем мапу со смещением
            shiftedByKeyMap = createShiftedByKeyMap(i);
            // создаем строку со смещением
            stringsDest = replaceCharsInString(originalMap, shiftedByKeyMap, stringsSource);
            //проверяем строку
            if (checkBrutForce(stringsDest)) {
                System.out.println("Ключ шифрования: " + (allCharsRu.length() - i));
                break;
            }
        }
        // создаем конечный файл и записываем в него строки со смещением
        createDestFile(destPath, stringsDest);
    }

    private boolean checkBrutForce(String stringsDest) {
        char point = '.';
        char comma = ',';
        char space = ' ';
        int pointOrCommaCheckTrue = 0;
        int pointOrCommaChekFalse = 0;
        boolean flag = false;
        char[] chars = stringsDest.toCharArray();
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
        if (pointOrCommaCheckTrue > pointOrCommaChekFalse) flag = true;
        if (flag) return true;
        return false;
    }

    private void createDestFile(Path destPath, String stringsDest) {
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

    private Map<Character, Integer> createOriginalMap() {
        char[] chars = allCharsRu.toCharArray();
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            hashMap.put(chars[i], i);
        }
        return hashMap;
    }

    private Map<Integer, Character> createShiftedByKeyMap(int key) {
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

    private String getString(Path path) {
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
        //создает сет символов и мапы для двух текстов
        HashSet<Character> characterHashSet = new HashSet<>();
        char[] chars = allCharsRu.toLowerCase(Locale.ROOT).toCharArray();
        for (char ch : chars
        ) {
            characterHashSet.add(ch);
        }
        HashMap<Character, Integer> sourceMap = new HashMap<>();
        for (Character ch : characterHashSet
        ) {
            sourceMap.put(ch, 0);
        }
        HashMap<Character, Integer> auxMap = new HashMap<>();
        for (Character ch : characterHashSet
        ) {
            auxMap.put(ch, 0);
        }

        //создаем мапы по кол-ву вхождения символов в текст
        String source = "";
        String aux = "";
        try {
            source = Files.readString(pathSource);
            aux = Files.readString(pathAux);
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] charsSource = source.toLowerCase(Locale.ROOT).toCharArray();
        char[] charsAux = aux.toLowerCase(Locale.ROOT).toCharArray();
        for (char ch : charsSource
        ) {
            if (characterHashSet.contains(ch)) {
                int count = sourceMap.get(ch);
                sourceMap.put(ch, count + 1);
            }
        }
        for (char ch : charsAux
        ) {
            if (characterHashSet.contains(ch)) {
                int count = auxMap.get(ch);
                auxMap.put(ch, count + 1);
            }
        }

        //реализовать сортировку мапы пузырьком
        sortMapsUsingBubbleMethod(sourceMap);

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

        ArrayList<Character> arrayListCharsSource = new ArrayList<>();
        for (Character ch : sortedSourceMap.keySet()
        ) {
            arrayListCharsSource.add(ch);
        }

        ArrayList<Character> arrayListCharsAux = new ArrayList<>();
        for (Character ch : sortedAuxMap.keySet()
        ) {
            arrayListCharsAux.add(ch);
        }

        //создаем строку сравнивая две мапы
        StringBuilder stringBuilder = new StringBuilder();
        char[] stringCharsSource = source.toLowerCase(Locale.ROOT).toCharArray();
        for (char ch : stringCharsSource
        ) {
            for (int i = 0; i < arrayListCharsSource.size(); i++) {
                if (ch == arrayListCharsSource.get(i)) {
                    stringBuilder.append(arrayListCharsAux.get(i));
                }
            }
        }
        //записываем строку в файл
        createDestFile(pathDest, stringBuilder.toString());
    }

    //данный метод создан чисто для удобства чтения программы
    public void decryptFileWithKey(int key, Path pathSource, Path pathDest) {
        key = key - key * 2; //меняем ключ на обратное значение для расшифровки
        encryptFileWithKey(key, pathSource, pathDest);
    }

    public void sortMapsUsingBubbleMethod(HashMap sourceMap) {
    }

}

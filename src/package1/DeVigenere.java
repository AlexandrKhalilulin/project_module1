package package1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;

public class DeVigenere {
    static final String ALPHABET_RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    static ArrayList<LinkedHashMap> Table_Vigenere = new ArrayList<>();

    public void initializeVigenereTable() {
        ArrayList<LinkedHashMap> mapArrayList = new ArrayList<>();
        LinkedHashMap<Character, String> characterStringLinkedHashMap = new LinkedHashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ALPHABET_RU);
        for (int i = 0; i < ALPHABET_RU.length(); i++) {
            char first = stringBuilder.charAt(0);
            stringBuilder.deleteCharAt(0);
            stringBuilder.append(first);
            characterStringLinkedHashMap.put(ALPHABET_RU.charAt(i), stringBuilder.toString());
            mapArrayList.add(characterStringLinkedHashMap);
        }
        for (LinkedHashMap map : mapArrayList
        ) {
            Table_Vigenere.add(map);
        }
    }

    public void encryptFileWithVigenere() {
        String keyPhrase = "Фраза";
        String originalString = "Мелеховский двор - на самом краю хутора. Воротца со скотиньего база ведут на север к Дону.";

        // создаем две сплошные строки без разделителей с оригинальным текстом и ключевой фразой одинаковой длины
        String solidOriginalString = createSolidStringCharacters(originalString);
        String solidPhraseStringWithLengthOriginal = createSolidPhraseStringCharacters(solidOriginalString, keyPhrase);

        //шифруем строку
        System.out.println(solidOriginalString);
        System.out.println(solidPhraseStringWithLengthOriginal);
        System.out.println(encryptString(solidOriginalString, solidPhraseStringWithLengthOriginal));

    }

    private String encryptString(String solidOriginalString, String solidPhraseStringWithLengthOriginal) {
        ArrayList<Character> alphabetList = new ArrayList<>(ALPHABET_RU.length());
        for (int i = 0; i < ALPHABET_RU.length(); i++) {
            alphabetList.add(ALPHABET_RU.charAt(i));
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < solidOriginalString.length(); i++) {
            int numberCharInValueStringMap = alphabetList.indexOf(solidOriginalString.charAt(i));
            int numberValueOnArrayList = alphabetList.indexOf(solidPhraseStringWithLengthOriginal.charAt(i));
            LinkedHashMap<Character, String> linkedHashMap = Table_Vigenere.get(numberValueOnArrayList);
            String string = linkedHashMap.get(solidPhraseStringWithLengthOriginal.charAt(i));
            char charAt = string.charAt(numberCharInValueStringMap);
            result.append(charAt);
        }
        return result.toString();
    }

    private String createSolidPhraseStringCharacters(String solidOriginalString, String keyPhrase) {
        StringBuilder stringBuilder = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < solidOriginalString.length(); i++) {
            stringBuilder.append(keyPhrase.toLowerCase(Locale.ROOT).charAt(flag++));
            if (flag > keyPhrase.length() - 1) {
                flag = 0;
            }
        }

        return stringBuilder.toString();
    }

    private String createSolidStringCharacters(String originalString) {
        char[] chars = originalString.toLowerCase(Locale.ROOT).toCharArray();
        HashSet<Character> hashSet = new HashSet<>();
        for (char charTemp : ALPHABET_RU.toCharArray()
        ) {
            hashSet.add(charTemp);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            if (hashSet.contains(chars[i])) {
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }


}

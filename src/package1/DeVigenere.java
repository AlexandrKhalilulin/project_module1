package package1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeVigenere {
    static final String ALPHABET_RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    static ArrayList<LinkedHashMap> Table_Vigenere;

    public void initializeVigenereTable(){
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
        System.out.println(mapArrayList);
    }

}

import java.util.*;

public class Solution0890 {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        String numPattern = toNumberPattern(pattern);
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (numPattern.equals(toNumberPattern(word))) {
                result.add(word);
            }
        }
        return result;
    }
    
    private String toNumberPattern(String word) {
        Map<Character, Integer> charToNum = new HashMap<>();
        StringBuilder pattern = new StringBuilder();
        int id = 0;
        for (char c : word.toCharArray()) {
            if (!charToNum.containsKey(c)) {
                charToNum.put(c, id);
                id++;
            }
            pattern.append(charToNum.get(c));
        }
        return pattern.toString();
    }
}

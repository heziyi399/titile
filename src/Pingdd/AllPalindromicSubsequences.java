package Pingdd;

/**
 * @author hzy
 * @date 2023-08-26
 */
import java.util.ArrayList;
import java.util.List;

public class AllPalindromicSubsequences {
    public static void main(String[] args) {
        String s = "ooppkkoo";
        List<String> palindromicSubsequences = findPalindromicSubsequences(s);

        for (String subsequence : palindromicSubsequences) {
            System.out.println(subsequence);
        }
    }

    public static List<String> findPalindromicSubsequences(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, new StringBuilder(), result);
        return result;
    }

    private static void backtrack(String s, int index, StringBuilder current, List<String> result) {
        if (index == s.length()) {
            String sequence = current.toString();
            if (isPalindrome(sequence) && !sequence.isEmpty()) {
                result.add(sequence);
            }
            return;
        }

        // Include current character
        current.append(s.charAt(index));
        backtrack(s, index + 1, current, result);
        current.deleteCharAt(current.length() - 1);

        // Exclude current character
        backtrack(s, index + 1, current, result);
    }

    private static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

package Pingdd;

/**
 * @author hzy
 * @date 2023-08-26
 */
import java.util.ArrayList;
import java.util.List;

public class AllPalindromicSubsequences {
    public static void main(String[] args) {
        int num = 6;
        int k = 13;

        int[] operations = calculateOperations(num, k);
        System.out.println("Multiply by 2: " + operations[0]);
        System.out.println("Divide by 2: " + operations[1]);
    }

    public static int[] calculateOperations(int num, int k) {
        int[] primeFactors = new int[31];
        int count = 0;

        // Count the occurrences of 2 as a prime factor
        while (num % 2 == 0) {
            primeFactors[count++] = 2;
            num /= 2;
        }

        // Count the other prime factors
        for (int i = 3; i * i <= num; i += 2) {
            while (num % i == 0) {
                primeFactors[count++] = i;
                num /= i;
            }
        }

        // If num is still greater than 2, it's a prime factor
        if (num > 2) {
            primeFactors[count++] = num;
        }

        int multiplyOperations = 0;
        int divideOperations = 0;

        for (int i = 0; i < count; i++) {
            int factorCount = 1;
            while (i + 1 < count && primeFactors[i + 1] == primeFactors[i]) {
                factorCount++;
                i++;
            }

            if (factorCount < k) {
                multiplyOperations += k - factorCount;
            } else if (factorCount > k) {
                divideOperations += factorCount - k;
            }
        }

        return new int[]{multiplyOperations, divideOperations};
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

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

class Main {
    public static String calc(String input) throws Exception {
        String[] tokens = input.trim().split(" ");
        if (tokens.length != 3) {
            throw new Exception("Введите выражение (например, 2 + 2)");
        }
        boolean isRoman = isRomanNumeral(tokens[0]) && isRomanNumeral(tokens[2]);
        int a = isRoman ? romanToArabic(tokens[0]) : parseNumber(tokens[0]);
        int b = isRoman ? romanToArabic(tokens[2]) : parseNumber(tokens[2]);
        char op = tokens[1].charAt(0);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно");
        }
        int result = 0;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                throw new Exception("Введите корректный оператор (+, -, *, /)");
        }
        return isRoman ? toRomanNumeral(result) : String.valueOf(result);
    }
    private static int parseNumber(String s) throws Exception {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new Exception("Введите корректное число от 1 до 10 включительно");
        }
    }

    private static int romanToArabic(String s) throws Exception {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!romanToArabicMap.containsKey(c)) {
                throw new Exception("Введите корректное римское число");
            }
            int currentValue = romanToArabicMap.get(c);
            int nextValue = (i + 1 < s.length()) ? romanToArabicMap.get(s.charAt(i + 1)) : 0;
            if (currentValue < nextValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
        }
        return result;

    }

    private static final String[] ROMAN_NUMERALS = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV",
            "I" };

    private static final int[] ROMAN_VALUES = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

    private static boolean isRomanNumeral(String s) {
        for (char c : s.toCharArray()) {
            if (c != 'I' && c != 'V' && c != 'X' && c != 'L' && c != 'C' && c != 'D' && c != 'M') {
                return false;
            }
        }
        return true;
    }

    private static String toRomanNumeral(int number) throws Exception {
        if (number < 1) {
            throw new Exception("Римские цифры не могут представлять нулевые или отрицательные числа");
        }
        StringBuilder sb = new StringBuilder();
        int remaining = number;
        for (int i = 0; i < ROMAN_VALUES.length; i++) {
            while (remaining >= ROMAN_VALUES[i]) {
                sb.append(ROMAN_NUMERALS[i]);
                remaining -= ROMAN_VALUES[i];
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Введите математическое выражение для вычисления (например, 2 + 2) или введите \"выход\", чтобы выйти:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("выход")) {
                System.out.println("Программа завершена");
                break;
            }
            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println("Введите следующее выражение для вычисления или введите \"выход\", чтобы выйти:");
        }
        scanner.close();
    }
}
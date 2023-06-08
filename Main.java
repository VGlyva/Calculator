
//импортирует три класса из стандартной библиотеки языка
import java.util.HashMap; //HashMap - класс, представляющий собой хеш-таблицу, используемую для хранения пар "ключ-значение";
import java.util.Map; //Map - интерфейс, определяющий основные методы работы с коллекцией, хранящей пары "ключ-значение";
import java.util.Scanner; //Scanner - класс, позволяющий считывать ввод с клавиатуры и других источников данных.

class Main { // Данный код объявляет класс Main. Обычно точка входа в программу объявляется
             // метод main, который является статическим.
    // Метод, вычисляющий результат математического выражения, заданного в строковом
    // формате
    public static String calc(String input) throws Exception {
        // Разбиваем строку на токены (число, оператор, число) и проверяем корректность
        // ввода
        String[] tokens = input.trim().split(" ");
        if (tokens.length != 3) {
            throw new Exception("Введите выражение в формате: число оператор число (например, 2 + 2)");
        }

        // Определяем, заданы ли числа в римской системе счисления
        boolean isRoman = isRomanNumeral(tokens[0]) && isRomanNumeral(tokens[2]);
        // Преобразуем числа в целочисленный формат, если они не заданы в римской
        // системе
        int a = isRoman ? romanToArabic(tokens[0]) : parseNumber(tokens[0]);
        int b = isRoman ? romanToArabic(tokens[2]) : parseNumber(tokens[2]);
        // Получаем оператор, заданный в виде символа
        char op = tokens[1].charAt(0);

        // Проверяем корректность ввода чисел
        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно");
        }

        // Вычисляем результат в зависимости от оператора
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
                if (b == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = a / b;
                break;
            default:
                throw new Exception("Введите корректный оператор (+, -, *, /)");
        }

        // Преобразуем результат обратно в строку в зависимости от системы счисления
        return isRoman ? toRomanNumeral(result) : String.valueOf(result);
    }

    // Метод, преобразующий строку с числом в целочисленный формат
    private static int parseNumber(String s) throws Exception {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new Exception("Введите корректное число (от 1 до 10 включительно)");
        }
    }

    // Метод, преобразующий строку с римским числом в целочисленный формат
    private static int romanToArabic(String s) throws Exception {
        // Создаем словарь, связывающий символ римской цифры с ее числовым значением
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        // Проходим по символам римского числа и вычисляем его числовое значение
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Проверяем, что символ является корректной римской цифрой
            if (!romanToArabicMap.containsKey(c)) {
                throw new Exception("Введите корректное римское число");
            }
            // Получаем числовое значение текущей цифры и следующей цифры (если она есть)
            int currentValue = romanToArabicMap.get(c);
            int nextValue = (i + 1 < s.length()) ? romanToArabicMap.get(s.charAt(i + 1)) : 0;
            // Вычисляем числовое значение римской цифры с учетом ее положения в числе
            // (вычитание или сложение)
            if (currentValue < nextValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
        }
        return result;

    }

    // Массив римских цифр и их числовых значений, используется для преобразования
    // целочисленного значения в римскую форму
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

    // Метод, преобразующий целочисленное значение в римскую форму
    private static String toRomanNumeral(int number) throws Exception {
        if (number < 1) {
            throw new Exception("Римские цифры не могут представлять нулевые или отрицательные числа");
        }
        // Создаем пустой StringBuilder, который будем заполнять римскими цифрами
        StringBuilder sb = new StringBuilder();
        int remaining = number;
        // Проходим по массиву римских цифр и их числовых значений, начиная с
        // наибольшего значения
        for (int i = 0; i < ROMAN_VALUES.length; i++) {
            // Пока число больше или равно числовому значению текущей римской цифры,
            // добавляем эту цифру в StringBuilder
            while (remaining >= ROMAN_VALUES[i]) {
                sb.append(ROMAN_NUMERALS[i]);
                remaining -= ROMAN_VALUES[i];
            }
        }
        // Возвращаем результат в виде строки
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Введите математическое выражение для вычисления (например, 2 + 2) или введите \"выход\", чтобы выйти:");
        // Цикл для чтения пользовательского ввода и вычисления результатов
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            // Если пользователь ввел "выход", завершаем программу
            if (input.equalsIgnoreCase("выход")) {
                System.out.println("Программа завершена");
                break;
            }
            // Вычисляем результат введенного математического выражения и выводим его на
            // экран
            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                // Если произошла ошибка при вычислении, выводим сообщение об ошибке
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println("Введите следующее выражение для вычисления или введите \"выход\", чтобы выйти:");
        }
        // Закрываем сканнер
        scanner.close();
    }
}

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static final String[] AvailableArab = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    static final String[] AvailableRoman = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static final String[] AvailableOperation = {"+", "-", "*", "/"};
    static boolean isArab = false;

    public static void main(String[] args) {
        System.out.println("Калькулятор. " + "Условия: операнды - целые, натуральные, только арабские или только римские числа, разделяемые пробелами от 0 до 10." + " Операции - (+,-,*,/)." + "Пример ввода: 1 + 2");
        calc();
    }
    public static void calc() {
        try {
            String[] inputArr = getCorrectInput();
            System.out.println(eval(inputArr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String input() {
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();
        return input;
    }

    public static String[] getCorrectInput() throws Exception {

        String input = input();
        String[] inputArr = inputToArray(input);
        if (!checkExpression(inputArr)) {
            throw new Exception("Введено не корректное выражение");
        }

        return inputArr;
    }

    public static boolean checkExpression(String[] input) {

        if (input.length > 3) {
            return false;
        }

        String operandLeft = input[0].trim();
        String operation = input[1].trim();
        String operandRight = input[2].trim();

        if (Arrays.asList(AvailableOperation).contains(operation)) {

            if (Arrays.asList(AvailableArab).contains(operandLeft) && Arrays.asList(AvailableArab).contains(operandRight)) {
                isArab = true;
                return true;
            } else if ((Arrays.asList(AvailableRoman).contains(operandLeft) && Arrays.asList(AvailableRoman).contains(operandRight))) {
                isArab = false;
                return true;
            }

        }
        return false;
    }

    public static String[] inputToArray(String input) {
        return input.trim().split(" ");
    }

    public static int getResult(int numLeft, int numRight, String operator) {
        int result = 0;
        if (operator.equals("-")) {
            result = numLeft - numRight;
        }
        if (operator.equals("+")) {
            result = numLeft + numRight;
        }
        if (operator.equals("*")) {
            result = numLeft * numRight;
        }
        if (operator.equals("/")) {
            result = numLeft / numRight;
        }
        return result;
    }

    public static String convertToArab(String num) {
        List<String> RomanList = Arrays.asList(AvailableRoman);
        int i = RomanList.indexOf(num);
        return AvailableArab[i];
    }

    public static String eval(String[] inputArr) {
        int result;
        int numLeft;
        int numRight;

        String operator = inputArr[1];

        if (!isArab) {
            numLeft = Integer.parseInt(convertToArab(inputArr[0]));
            numRight = Integer.parseInt(convertToArab(inputArr[2]));
            result = getResult(numLeft, numRight, operator);

            return arabicToRoman(result);
        }
        numLeft = Integer.parseInt(inputArr[0]);
        numRight = Integer.parseInt(inputArr[2]);

        result = getResult(numLeft, numRight, operator);
        return Integer.toString(result);
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range");
        }

        List romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = (RomanNumeral) romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100),
    CD(400), D(500), CM(900), M(1000);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                .collect(Collectors.toList());
    }
}
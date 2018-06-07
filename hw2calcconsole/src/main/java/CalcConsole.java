import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.exit;

public class CalcConsole {
    private static Logger log = LoggerFactory.getLogger(CalcConsole.class);
    private static Scanner scanner = new Scanner(System.in);
    private static final Pattern expression =
            Pattern.compile("(([0-9]*\\u002E[0-9]*)|([0-9]*))([-+*/])(([0-9]*\\u002E[0-9]*)|([0-9]*))");

    public static void main(String[] args) {
        System.out.println("Welcome to calculator of double numbers!\nType your expression:");
        while (true) {
            String input = scanner.nextLine();
            log.info("Read user's input string.");
            String command = input.replaceAll(" ", "");
            if (command.equalsIgnoreCase("quit")) {
                log.info("User quits the program.");
                exit(0);
            }
            if (!checkInput(command)) {
                System.out.println("Invalid signature! Try again.");
                log.info("User typed incorrect value.");
            } else {
                NumberFormat formatter = new DecimalFormat("#0.00");
                System.out.println(formatter.format(calculate(command)));
            }
        }
    }

    static boolean checkInput(String word) {
        log.info("Checking input...");
        Matcher matcher = expression.matcher(word);
        log.info("Result of checking = " + matcher.matches());
        return matcher.matches();
    }

    static double calculate(String command) {
        log.info("Calculate method start.");
        CalcCoreClass calcCoreC = new CalcCoreClass();
        String[] strings = parserForString(command);
        double result = 0;
        double first_argument = Double.parseDouble(strings[0]);
        double second_argument = Double.parseDouble(strings[2]);
        switch (strings[1].charAt(0)) {
            case '+':
                log.info("Addition of two numbers " + first_argument + " and " + second_argument);
                result = calcCoreC.plus(first_argument, second_argument);
                log.info("Addition result = " + result);
                break;
            case '-':
                log.info("Subtraction of two numbers " + first_argument + " and " + second_argument);
                result = calcCoreC.minus(first_argument, second_argument);
                log.info("Subtraction result = " + result);
                break;
            case '*':
                log.info("Multiplication of two numbers " + first_argument + " and " + second_argument);
                result = calcCoreC.multiply(first_argument, second_argument);
                log.info("Multiplication result = " + result);
                break;
            case '/':
                if (second_argument == 0.0) {
                    System.out.println("Can't divide by zero! Try again.");
                    log.info("User tried divide by zero.");
                    break;
                } else {
                    log.info("Division of two numbers " + first_argument + " and " + second_argument);
                    result = calcCoreC.divide(first_argument, second_argument);
                    log.info("Division result = " + result);
                    break;
                }
        }
        return result;
    }


    static String[] parserForString(String command) {
        log.info("String parsing.");
        StringTokenizer str = new StringTokenizer(command, "+-*/", true);
        String[] strings = new String[3];
        int i = 0;
        while (str.hasMoreTokens()) {
            String word = str.nextToken();
            strings[i] = word;
            i++;
        }
        log.info("Result of string parsing: first_argument=" + strings[0] +
                " sign=" + strings[1] +
                " second_argument=" + strings[2]);
        return strings;
    }
}

package Recurs;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.util.*;

/* 
Рекурсия для мат. выражения
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse("sin(2*(-5+1.5*4) +  28)", 0); //expected output 0.5 6
        solution.recurse("tan(2025 ^ 0.5)", 0); //expected output 1 2
        solution.recurse("10 * sin(90)", 0);     // 10 2
        solution.recurse("tan(45)",0); // expected output 1 1 actually 1 1
        solution.recurse("tan(-35 - 10)",0); // expected output -1 3 actually -1 3
        solution.recurse(" 0.305 " , 0 ); //expected output 0.3 0 actually 0.3 0
        solution.recurse("1+(1+(1+1)*(1+1))*(1+1)+1 ", 0 ); // expected output 12 8 actually 12 8
        solution.recurse("0.3051", 0); //  expected output 0.31 0 actually 0.31 0
        solution.recurse("tan(44+sin(89-cos(180)^2))",0); // expected output 1 6 actually 1 6
        solution.recurse("-2+(-2+(-2)-2*(2+2)) ", 0); // expected output -14 8 actually -14 8
        solution.recurse("sin(80+(2+(1+1))*(1+1)+2)", 0) ; // expected output 1 7 actually 1 7
        solution.recurse("1+4/2/2+2^2+2*2-2^(2-1+1)", 0) ; // expected output 6 11 actually 6 11
        solution.recurse("10-2^(2-1+1)", 0); // expected output 6 4 actually 6 4
        solution.recurse("2^10+2^(5+5)",0); // expected output 2048 4 actually 2048 4
        solution.recurse("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", 0); // expected output 72.96 8 actually 72.96 8
        solution.recurse("0.000025+0.000012",0); // expected output 0 1 actually 0 1
        solution.recurse("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)",0) ; // expected output -3 16 actually -3 16
        solution.recurse("cos(3 + 19*3)",0); // expected output 0.5 3 actually 0.5 3
        solution.recurse("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)",0); // expected output 8302231.36 14 actually 8302231.36 14
        solution.recurse("(-1 + (-2)) ",0); // expected output -3 3 actually -3 3
        solution.recurse("-sin(2*(-5+1.5*4)+28 )",0); // expected output -0.5 7 actually -0.5 7
        solution.recurse("1-sin(-90) ", 0); // expected output 2 3 actually 2 3
        solution.recurse("-1-sin(-90) ", 0); // expected output 0 4 actually 0 4
        solution.recurse("sin(100)-sin(100) ", 0); // expected output 0 3 actually 0 3
        solution.recurse("-(-22+22*2)",0); // expected output -22 4 actually -22 4
        solution.recurse("-2^(-2)", 0); // expected output -0.25 3 actually 0.25 3
        solution.recurse("-(-2^(-2))+2+(-(-2^(-2))) ", 0); // expected output 2.5 10 actually 2.5 10
        solution.recurse("(-2)*(-2)",0);  // expected output 4 3 actually 4 3
        solution.recurse("(-2)/(-2)",0); // expected output 1 3 actually 1 3
        solution.recurse("sin(-30)" , 0); // expected output -0.5 2 actually -0.5 2
        solution.recurse("cos(-10 - 20)",0); // xpected output 0.87 3 actually 0.87 3
        solution.recurse("tan(-30)",0); // expected output -0.58 2 actually -0.58 2
        solution.recurse("2+8*(9/4-1.5)^(1+1) ",0) ; // expected output 6.5 6 actually 6.5 6
        solution.recurse("0.005",0); //  expected output 0.01 0 actually 0.01 0
        solution.recurse("0.0049",0); //  expected output 0 0 actually 0 0
        solution.recurse("0+0.304",0); // expected output 0.3 1 actually 0.3 1
        solution.recurse("4^(-2)",0); // expected output 0.06 2 actually 0.06 2
    }

    public void recurse(final String expression, int countOperation) {
        //implement
        // Подсчёт операций в выражении
        String sourse = expression.replaceAll(" ", "");
        int iCountOperation = 0;
        if (countOperation == 0){
            String test = sourse;
            while (test.contains("sin") || test.contains("cos") || test.contains("tan")){
                test = test.replace("sin","s");
                test = test.replace("cos","c");
                test = test.replace("tan","t");
            }
            for (char d : test.toCharArray()){
                switch (d){
                    case 'c':
                        iCountOperation++;
                        break;
                    case 's':
                        iCountOperation++;
                        break;
                    case 't':
                        iCountOperation++;
                        break;
                    case '*':
                        iCountOperation++;
                        break;
                    case '/':
                        iCountOperation++;
                        break;
                    case '^':
                        iCountOperation++;
                        break;
                    case '+':
                        iCountOperation++;
                        break;
                    case '-':
                        iCountOperation++;
                        break;
                }
            }
        } else
            iCountOperation = countOperation;

        List<String> commonArr = new ArrayList<>();
        String sTmp = sourse;
        String[] numbers;

        if (sourse.contains("(") && sourse.contains(")")) {
            sTmp = sourse.substring(sourse.lastIndexOf("(") + 1);
            sTmp = sTmp.substring(0, sTmp.indexOf(")") );  // такая конструкция нужна из-за возможных вложенных скобок
        }
        // Удалим сдвоенные знаки
        String sInternalString = sTmp;   //Получили выражение в скобках

        sTmp =  DelSign (sTmp);

        // Заменим cos, sin, tan на одну первую букву
        while(sTmp.contains("sin") || sTmp.contains("cos") || sTmp.contains("tan")) {
            // Операцию делаю в цикле т.к. есть опасения, что может быть нксколько синусов ...
            sTmp = sTmp.replace("sin","s");
            sTmp = sTmp.replace("cos", "c");
            sTmp = sTmp.replace("tan", "t");
        }

        sTmp = "0+" + sTmp;
        // Обрабатывать будем начиная с внутренних скобок
        //"разбиение" строки на элементы (числа и мат. действия) и помещение их в commonArr
         numbers = sTmp.split("[s,c,t/^,/*,//,+,/-]");  // Разделим нашу строку на массив строк, содержащих числа, указанных в разделителях по всем операциям
        int counter = 0;

        List <Double> lNumbers= new ArrayList<Double>(numbers.length);
        for (int i = 0; i< numbers.length; i++)
            if (!numbers[i].equals(""))
                lNumbers.add(Double.parseDouble(numbers[i]));

       char ch[] = (sTmp).toCharArray(); // Для простоты алгоритма добвалю 0+, на финальный результат не влияет :)

        boolean bDigit = false;   // Следующий знак минус

        for (int i=0; i< ch.length; i++) {
            if (ch[i] == '^' || ch[i] == '*' || ch[i] == '/' || ch[i] == '+' || ch[i] == '-') {
                // у нас есть оператор и нам нужно понять, какой именно будет следущий знак
                if (ch[i + 1] == '-') {  // В данном случае такая проверка допустима т.к. после знака всегда должно идти число или выражение
                    i++;
                    switch (ch[i-1]) {
                        case '^':
                            commonArr.add("^");
                            commonArr.add("-");  // Если нужно возвести в отрицательную степень, то тут я могу только показать, что степент будет отрицательна, а обработка будет позже :(
                            break;
                        case '*':
                        case '/':
                            commonArr.add("*");         // Тут я "обработаю" минус очень просто - я умножу на -1, а потом запишу основную операцию
                            commonArr.add("-1");
                            commonArr.add(String.valueOf(ch[i-1]));
                            break;
                        case '+':               // для + и - просто поменяю знак
                            commonArr.add("-");
                            break;
                        case '-':
                            commonArr.add("+");
                            break;
                    }
                } else {
                    // если после прочитанного знака идёт не "-", то задача сильно упрощается.
                    commonArr.add(String.valueOf(ch[i])); // Мы просто запишем знак и пойдём обрабатывать следующий символ
                }
                bDigit = false; // Тут не число
                continue;
            } else if (ch[i] == 's' || ch[i] == 'c' || ch[i] == 't') { // У нас тут тригонометрия
                commonArr.add(String.valueOf(ch[i]));  // Запишем нашу функцию
                if (ch[i + 1] == '-') {  // В данном случае такая проверка допустима т.к. после знака всегда должно идти число или выражение
                    commonArr.add("-");
                    i++;        // Следующий символ обработан!
                }
                bDigit = false; // Тут не число
                continue;
            } else {
                if (!bDigit){
                    bDigit = true;
                    commonArr.add(lNumbers.get(counter).toString());  // положим следущее число и увеличим счётчик чисел на 1
                    counter++;
                }
            }

        }



        // вычисление sin, cos, tan. Так мы задаём последовательнось операция. Вначале синусы
        if (commonArr.contains("s") || commonArr.contains("c") || commonArr.contains("t")){
            double x;
            int sign;
            for (int i = 0; i < commonArr.size(); i++) {
                sign = 1;
                switch (commonArr.get(i)){
                    case "s":
                        if (commonArr.get(i + 1).equals("-")) {
                            sign = -1;
                            commonArr.remove(i + 1);
                        }
                        if (i > 0 && commonArr.get(i-1).equals("-")) {
                            x = -Math.sin(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        }else x = Math.sin(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        commonArr.set(i, String.valueOf(x));
                        commonArr.remove(i+1);
                        commonArr.set(i, String.valueOf(x));
                        if (i > 0 && commonArr.get(i - 1).equals("-")) commonArr.remove(i - 1);
                        break;
                    case "c":
                        if (commonArr.get(i + 1).equals("-")) {
                            sign = -1;
                            commonArr.remove(i + 1);
                        }
                        if (i > 0 && commonArr.get(i - 1).equals("-")) {
                            x = -Math.cos(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        }else x = Math.cos(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        commonArr.set(i, String.valueOf(x));
                        commonArr.remove(i + 1);
                        commonArr.set(i, String.valueOf(x));
                        if (i > 0 && commonArr.get(i - 1).equals("-")) commonArr.remove(i - 1);
                        break;
                    case "t":
                        if (commonArr.get(i + 1).equals("-")) {
                            sign = -1;
                            commonArr.remove(i + 1);
                        }
                        if (i > 0 && commonArr.get(i - 1).equals("-")) {
                            x = -Math.tan(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        }else x = Math.tan(Math.toRadians(Double.valueOf(commonArr.get(i + 1))*sign));
                        commonArr.set(i, String.valueOf(x));
                        commonArr.remove(i + 1);
                        commonArr.set(i, String.valueOf(x));
                        if (i > 0 && commonArr.get(i - 1).equals("-")) commonArr.remove(i - 1);
                        break;
                }
            }
        }

        // возведение в степень После косинусов степень
        if (commonArr.contains("^")){
            for (int i = 0; i < commonArr.size(); i++) {
                if (commonArr.get(i).equals("^")) {
                    double power = 1;
                    if (commonArr.get(i + 1).equals("-")) {
                        power = (-1) ;
                        commonArr.remove(i + 1);
                    }
                    power =  power * Double.valueOf(commonArr.get(i + 1));
                    commonArr.set(i - 1, String.valueOf(Math.pow(Double.valueOf(commonArr.get(i - 1)),power)));
                    commonArr.remove(i + 1);
                    commonArr.remove(i);
                    i = 0;
                }
            }
        }

        // перемножение и деление элементов commonArr после степени умножение
        if (commonArr.contains("*") || commonArr.contains("/")) {
            for (int i = 0; i < commonArr.size(); i++) {
                double z;
                switch (commonArr.get(i)) {
                    case "*":
                        z = Double.valueOf(commonArr.get(i - 1)) * Double.valueOf(commonArr.get(i + 1)); // Умножим то, что обрамляет знак умножения
                        commonArr.set(i - 1, String.valueOf(z));
                        commonArr.remove(i + 1);
                        commonArr.remove(i);
                        i = 0;      // Так, после удаления двух элементов, запустим песь процесс с самого начала
                        break;
                    case "/":
                        z = Double.valueOf(commonArr.get(i - 1)) / Double.valueOf(commonArr.get(i + 1));
                        commonArr.set(i - 1, String.valueOf(z));
                        commonArr.remove(i + 1);
                        commonArr.remove(i);
                        i = 0;    // Так, после удаления двух элементов, запустим песь процесс с самого начала
                        break;
                }
            }
        }

        // Расстановка "минусов" и на последней итерации возьмём уже минус и плюс
        if (commonArr.contains("-") || commonArr.contains("+")) {
            for (int i = 0; i < commonArr.size(); i++) {
                double x;
                switch (commonArr.get(i)) {
                    case "-":
                        x = Double.valueOf(commonArr.get(i + 1)) * (-1);
                        commonArr.set(i + 1, String.valueOf(x));
                        commonArr.remove(i);
                        i = 0;
                        break;
                    case "+":
                        x = Double.valueOf(commonArr.get(i + 1));
                        commonArr.set(i + 1, String.valueOf(x));
                        commonArr.remove(i);
                        i = 0;
                        break;
                }
            }
        }

        double res = 0.0;
        // После всех манипуляций у нас в массиве остались только числа, которые нужно сложить между собой
        // Сложение всех элементов commonArr
        for (String numb : commonArr) res += Double.valueOf(numb);

        if (sourse.contains("(") && sourse.contains(")")) {  // если в исходном выражении были скобки, то мы ещё не всё посчитали
            sTmp = sourse.replace("(" + sInternalString + ")", String.valueOf(res));  // Заменим первое выражение в скобках, на рассчитанное
            recurse (sTmp, iCountOperation);  // и запустим ещё один цикл вычислений
        } else {
            sTmp = new BigDecimal(res).setScale(2, RoundingMode.HALF_UP).toString();  // Сделаем рекомендованный вариант округления
            sTmp = new DecimalFormat("#.##").format(Double.valueOf(sTmp));
            System.out.println(sTmp.replace(",", ".") + " " + iCountOperation);
            return;
        }
    }

    private String DelSign (String line) {
        String result = "";
        // Функцию ещё нужно разработать.
        String tmp = line.replace("\\+","M");
        int len = line.length();
        while (true) {
            tmp = tmp.replaceAll("--", "M");
            tmp = tmp.replaceAll("MM", "M");
            tmp = tmp.replaceAll("\\(M", "\\(");
            tmp = tmp.replaceAll("\\^M", "^");
            tmp = tmp.replaceAll("M-", "-");
            tmp = tmp.replaceAll("-M", "-");
            if (tmp.length() < len) {
                len = tmp.length();
            } else
                break;
        }
        if (tmp.startsWith("M"))
            tmp = tmp.substring(1);
        result = tmp.replaceAll("M", "\\+");

        return result;
    }

    public Solution() {
        //don't delete
    }
}

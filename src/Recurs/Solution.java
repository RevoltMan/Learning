package com.javarush.task.task34.task3404;

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
/*        solution.recurse("sin(2*(-5+1.5*4) +  28)", 0); //expected output 0.5 6
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
        solution.recurse("-1-sin(-90) ", 0); // expected output 0 4 actually 0 4  */
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
        String sInternalString = sTmp;   //Получили выражение в скобках

        // Заменим cos, sin, tan на одну первую букву
        while(sTmp.contains("sin") || sTmp.contains("cos") || sTmp.contains("tan")) {
            // Операцию делаю в цикле т.к. есть опасения, что может быть нксколько синусов ...
            sTmp = sTmp.replace("sin","s");
            sTmp = sTmp.replace("cos", "c");
            sTmp = sTmp.replace("tan", "t");
        }

        // Обрабатывать будем начиная с внутренних скобок
        //"разбиение" строки на элементы (числа и мат. действия) и помещение их в commonArr
 //       sTmp = sTmp.startsWith("-") ? sTmp.substring(1,sTmp.length()) : sTmp;  // Удалим лидирующий минус
        numbers = sTmp.split("[s,c,t/^,/*,//,+,/-]");  // Разделим нашу строку на массив строк, содержащих числа, указанных в разделителях по всем операциям
        int counter = 0;
        boolean isStartMinus = false;  // т.к. у нас может быть случай с отрицательным значением градуса, то просто проверить первый символ не достаточно
        if (sTmp.startsWith("-"))
            isStartMinus = true;
        else
            if (sTmp.startsWith("t") || sTmp.startsWith("s") || sTmp.startsWith("c"))
                if (sTmp.substring(1,2).equals("-")) {
                    isStartMinus = true;
                }

        List <Double> lNumbers= new ArrayList<Double>(numbers.length);
        for (int i = 0; i< numbers.length; i++)
            if (!numbers[i].equals(""))
                lNumbers.add(Double.parseDouble(numbers[i]));

        boolean lastOperand = false;
        boolean numericOperand = false;
        boolean thirdOperand = false;

        for (Character ch : sTmp.toCharArray()) {
            if (lastOperand) {
                if (  ch == '-' && thirdOperand ) {
                    // Рассмотрим только один случай, когда полседний оператор это минус, и его нужно поставить под тригономерт функцию
                    Double dTmp = Double.parseDouble(commonArr.get(commonArr.size() - 1));  // Тут всегда -1 не зависимо от первого знака
                    dTmp = dTmp * (-1);
                    commonArr.set(commonArr.size() - 1, dTmp.toString());
                    thirdOperand = false;
                } else {
                    if (ch == 's' || ch == 'c' || ch == 't') {  // На предыдущем шаге мы определили, что операню последний, но мо может быть у нас ещё есть синус ...
                        commonArr.add(String.valueOf(ch));  //Тут есть проблема, что под выраженем может быть отрицательное число!
                        if (numericOperand)
                            thirdOperand = true;   // Это одначает, что перед обработкрй последнего числа, мы ВОЗСОЖНО не обработали ещё один знак!
                    }
                    Double dTmp = lNumbers.get(counter);
                    commonArr.add(dTmp.toString());
                }
                if (!thirdOperand)
                    break;
                else
                    continue;
            }
            if (ch == '^' || ch == '*' || ch == '/' || ch == '+' || ch == '-') {
                if (numericOperand) {// Ситуация не самая приятная, у нас два оператора идут один за другим (второй видимо минус)
                    Double dTmp;
                    int delta;
                    if (isStartMinus) {
                        delta = 1;
                    } else {
                        delta = 2;
                    }
                    dTmp = Double.parseDouble(commonArr.get(commonArr.size() - delta));
                    dTmp = dTmp * (-1);
                    commonArr.set(commonArr.size() - delta, dTmp.toString());
                } else {
                    if (isStartMinus) {
                        commonArr.add(String.valueOf(ch));          // В исходной строке запись начинается с "-" и мы вначале пишем знак, а потом число
                        commonArr.add(lNumbers.get(counter).toString());
                    } else {
                        commonArr.add(lNumbers.get(counter).toString());      // В исходной строке запись НЕ начинается с "-" и мы вначале пишем число, а потом знак
                        commonArr.add(String.valueOf(ch));
                    }
                    counter++;
                }
                numericOperand = true;
            } else {
                if (ch == 's' || ch == 'c' || ch == 't') {
                    if (numericOperand) {// Если перед функцией стоял знак, то на надо проделать операцию подобно тому, что мы делали для знаков, когда шел минус за плюсом
                        int delta;
                        if (isStartMinus) {
                            delta = 1;
                        } else {
                            delta = 2;
                        }
                        commonArr.add( commonArr.size() - delta, String.valueOf(ch));
                    } else {
                        commonArr.add(String.valueOf(ch));  //Тут есть проблема, что под выраженем может быть отрицательное число!
                        Double dTmp = lNumbers.get(counter) * (isStartMinus ? (int) -1 : (int) 1);
                        commonArr.add(dTmp.toString());
                        counter++;
                    }
                    numericOperand = true;
                } else
                numericOperand = false;
            }

            if (counter == lNumbers.size() - 1 && !isStartMinus) { //Если мы дошли до последнего числа, то его нужно отдельно записать
                lastOperand = true;
//                commonArr.add(lNumbers.get(counter).toString());
//                break;
            }

            if (counter >= lNumbers.size() && !numericOperand)
                break;
        }

        // для случая, когда подряю идут два знака
        if (commonArr.contains("")) {
            for (int i = 0; i < commonArr.size(); i++) {
                if (commonArr.get(i).equals("")) {
                    if (commonArr.get(i + 1).equals("-")){
                        double r = - Double.valueOf(commonArr.get(i + 2));
                        commonArr.set(i + 1,String.valueOf(r));
                        commonArr.remove(i + 2);
                    }
                    commonArr.remove(i);
                    i = 0;
                }
            }
        }


        // вычисление sin, cos, tan. Так мы задаём последовательнось операция. Вначале синусы
        if (commonArr.contains("s") || commonArr.contains("c") || commonArr.contains("t")){
            double x;
            for (int i = 0; i < commonArr.size(); i++) {
                switch (commonArr.get(i)){
                    case "s":
                        if (i > 0 && commonArr.get(i-1).equals("-")) {
                            x = -Math.sin(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
                        }else x = Math.sin(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
                        commonArr.set(i, String.valueOf(x));
                        commonArr.remove(i+1);
                        commonArr.set(i, String.valueOf(x));
                        if (i > 0 && commonArr.get(i - 1).equals("-")) commonArr.remove(i - 1);
                        break;
                    case "c":
                        if (i > 0 && commonArr.get(i - 1).equals("-")) {
                            x = -Math.cos(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
                        }else x = Math.cos(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
                        commonArr.set(i, String.valueOf(x));
                        commonArr.remove(i + 1);
                        commonArr.set(i, String.valueOf(x));
                        if (i > 0 && commonArr.get(i - 1).equals("-")) commonArr.remove(i - 1);
                        break;
                    case "t":
                        if (i > 0 && commonArr.get(i - 1).equals("-")) {
                            x = -Math.tan(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
                        }else x = Math.tan(Math.toRadians(Double.valueOf(commonArr.get(i + 1))));
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
                    commonArr.set(i - 1, String.valueOf(Math.pow(Double.valueOf(commonArr.get(i - 1)),Double.valueOf(commonArr.get(i + 1)))));
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
            sTmp = new BigDecimal(res).setScale(2, RoundingMode.HALF_UP).toString();
            sTmp = new DecimalFormat("#.##").format(Double.valueOf(sTmp));
            System.out.println(sTmp.replace(",", ".") + " " + iCountOperation);
            return;
        }
    }

    public Solution() {
        //don't delete
    }
}

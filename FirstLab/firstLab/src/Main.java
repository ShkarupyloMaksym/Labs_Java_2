import java.util.*;

class StringCalculator{
    List<String> Separators;

    public StringCalculator(){
        SeparatorsInit();
    }
    public int add(String numbers) throws Exception {
        List<Integer> number = new ArrayList<>();

        if (ShouldSeparatorsAdd(numbers)){
            SeparatorsAdd(numbers);
            numbers = returnStringWithoutSeparators(numbers);
        }
        SeparateString(numbers,number);

        return NumbersListSum(number);
    }

    private int NumbersListSum(List<Integer> numberList) throws Exception {
        int sum = 0;
        for (int num: numberList) {
            if (num>1000) continue;
            if (num<0) throw NegativeNumberException(numberList);
            sum+=num;
        }
        return sum;
    }
    private Exception NegativeNumberException(List<Integer> numberList){
        StringBuilder ExceptionString = new StringBuilder("Negative numbers are: ");
        for (int num: numberList) {
            if (num<0) ExceptionString.append(num).append(" ");
        }
        return new Exception(String.valueOf(ExceptionString));
    }

    private void SeparateString(String numbers, List<Integer> numberList) throws Exception {
        while (!numbers.isEmpty()){
            numbers = addNumberToNumberList(numbers, numberList);
            if (numbers.isEmpty()) return;
            numbers = CheckSeparators(numbers);
        }
    }

    private boolean IsNumber(char chr){
        return Character.isDigit(chr) || chr == '-' || chr == '\0';
    }

    private String CheckSeparators(String numbers) throws Exception {
        for (String str: Separators){
            if (numbers.length()>str.length() && str.equals(numbers.substring(0,str.length()))  && IsNumber(numbers.charAt(str.length()))){
                return numbers.substring(str.length());
            }
        }

        throw new Exception("Incorrect numbers format (incorrect delimiter)");
    }
    private String addNumberToNumberList(String numbers, List<Integer> numberList) throws Exception {
        int position = 0;
        while (IsNumber(numbers.charAt(position++))) {
            if(position==numbers.length()) {
                position+=1;
                break;
            }
        }
        if (position<2){
            throw new Exception("Incorrect numbers format (maybe more than one delimiter)");
        }
        numberList.add(Integer.parseInt(numbers.substring(0,position-1)));

        return numbers.substring(position-1);
    }

    private void SeparatorsInit(){
        Separators = new ArrayList<>();
        Separators.add(",");
        Separators.add("\n");
    }

    private boolean ShouldSeparatorsAdd(String numbers){
        return numbers.charAt(0) == '/' && numbers.charAt(1) == '/';
    }

    boolean isSeparatorEnd(String numbers, int position){
        return numbers.charAt(position) == '\n';
    }
    private void SeparatorsAdd(String numbers) throws Exception {

        int position = 0;
        // first // (2 positions)
        position+=2;
        if (isSeparatorEnd(numbers, position+1)){
            Separators.add(String.valueOf(numbers.charAt(position)));
            return;
        }

        while (!isSeparatorEnd(numbers,position)){
            if (numbers.charAt(position) == '['){
                position+=1;
                StringBuilder newSeparator = new StringBuilder();
                while (numbers.charAt(position++)!=']')
                    newSeparator.append(numbers.charAt(position-1));
                Separators.add(String.valueOf(newSeparator));
            }
            else throw new Exception("Incorrect initialisation delimiters format");
        }
    }

    private String returnStringWithoutSeparators(String numbers){
        int position = 2;
        while (!isSeparatorEnd(numbers,++position));
        position+=1;
        return numbers.substring(position);
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(new StringCalculator().add("//[*][%]\n1*2%3"));
        System.out.println(new StringCalculator().add("//[**][***]\n1**2***3"));
        try {
            System.out.println(new StringCalculator().add("1000,-999,-1001"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(new StringCalculator().add("1000,999,"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
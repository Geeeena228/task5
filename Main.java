import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println(encrypt("Hello"));
        System.out.println(decrypt(new int[]{72, 33, -73, 84, -12, -3, 13, -13, -68 }));
        System.out.println(canMove("Rook", "A8", "H8"));
        System.out.println(canComplete("butl", "beautiful"));
        System.out.println(sumDigProd(new int[]{16, 28}));
        System.out.println(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"}));
        System.out.println(validateCard(1234567890123452L));
        System.out.println(numToEng(18));

        try {
            System.out.println(getSha256Hash("password123")); // -> ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println(hexLattice(7));
    }
    public static String encrypt(String s){ // зашифровать
        int[] result = new int[s.length()];
        char[] chars = s.toCharArray();

        for (int i =0; i< chars.length; i++){
            if (i==0){
                result[i]=chars[i];
            }else{
                result[i] = chars[i]-chars[i-1];
            }
        }
        return Arrays.toString(result);
    }

    public static String decrypt(int[] a){
        String result ="";
        char first = (char) a[0];
        result += first;

        for (int i=1; i<a.length; i++){
            first=(char)(first+a[i]);
            result+=first;
        }
        return result;
    }
    public static boolean canMove(String s, String from, String where){
        char [] fromArr = from.toCharArray(); // A8
        char [] whereArr = where.toCharArray(); // H8

        int x1=fromArr[0]; // A
        int x2=whereArr[0]; // H
        int y1=fromArr[1]; // 8
        int y2=whereArr[1]; // 8

        if (Objects.equals(s, "Rook")){ // ладья - равны буквы и цифры
            return(x1==x2 || y1==y2);
        }
        if (Objects.equals(s, "King")){ // король - на 1 в любую сторону
            return ((x2 - x1>=-1 && x2 - x1<=1) && (y2 - y1 >= -1 && y2 - y1<=1));
        }
        if (Objects.equals(s, "Bishop")){ // слон - по диагонали
            return (x1-y1==x2-y2 || x1+y1==x2+y2);
        }
        if(Objects.equals(s, "Queen")){ // королева - в любые стороны (условия ладьи или слона)
            return (x1-y1==x2-y2 || x1+y1==x2+y2 || x1==x2||y1==y2);
        }
        if(Objects.equals(s, "Pawn")){ // пешка - вперед на одну клетку
            return (y2==y1+1);
        }
        if(Objects.equals(s, "Knight")){ // слон - буквой "Г"
            return ((Math.abs(x1-x2)==2 && Math.abs(y1-y2)==1) || (Math.abs(x1-x2)==1 && Math.abs(y1-y2)==2));
        }
        return false;
    }
    public static boolean canComplete(String s1, String s2){ // butl   "beautiful
        int result=0;
        for (int i=0; i<s2.length();i++){
            if(s2.charAt(i)==s1.charAt(result)){ // проверяем иожно ли составить из первого слова второе
                result++;
            }
        }
        if (result==s1.length())
            return true;
        return false;
    }

    public static int sumDigProd(int [] a){ // 16 28
        int sum = 0;
        for (int i=0; i<a.length;i++){
            sum += a[i]; // 43
        }
        if (sum <= 9) {
            return sum;
        }
        int result = 1;
        while(sum>9){ // 43> 9
            int temp = sum;
            while (temp>0){
                result = result * temp%10; // 1 * 3 = 3
                temp /= 10; // 4
            }
            sum = result; // 3
        }
        return result;
    }
    public static ArrayList<String> sameVowelGroup(String []arr){
        ArrayList<String> result = new ArrayList<>();
        String firstWord = arr[0];
        result.add(firstWord);
        boolean a=false; // для первого слова
        boolean e=false;
        boolean u=false;
        boolean i=false;
        boolean o=false;
        boolean y=false;
        boolean A=false; // для всех последующих
        boolean E=false;
        boolean U=false;
        boolean I=false;
        boolean O=false;
        boolean Y=false;
        for (int n=0; n<firstWord.length();n++){
            if(firstWord.charAt(n)=='a')
                a=true;
            if(firstWord.charAt(n)=='e')
                e=true;
            if(firstWord.charAt(n)=='u')
                u=true;
            if(firstWord.charAt(n)=='i')
                i=true;
            if(firstWord.charAt(n)=='o')
                o=true;
            if(firstWord.charAt(n)=='y')
                y=true;
        }
        for (int n=1; n<arr.length;n++){
            String[] word = arr[n].split("");
            for (int j = 0; j < word.length; j++) {
                if (Objects.equals(word[j], "a")) {
                    A = true;
                }
                if (Objects.equals(word[j], "e")) {
                    E = true;
                }
                if (Objects.equals(word[j], "u")) {
                    U = true;
                }
                if (Objects.equals(word[j], "i")) {
                    I = true;
                }
                if (Objects.equals(word[j], "o")) {
                    O = true;
                }
                if (Objects.equals(word[j], "y")) {
                    Y = true;
                }
            }
            if (a==A && e==E && u==U && i==I && o==O && y==Y) {
                result.add(String.join("", word));
            }
            A=false;
            E=false;
            U=false;
            I=false;
            O=false;
            Y=false;
        }
        return result;
    }

    public static boolean validateCard(Long x){
        String number = String.valueOf(x);
        String[] numberArr = number.split("");
        if (number.length() >= 14 && number.length() <= 19) { // проверка на длину от 14 до 19
            String[] numberArr2 = Arrays.copyOfRange(numberArr, 0, numberArr.length-1);// удаляем последнюю цифру
            int[] reversed = new int[numberArr2.length];
            int k = numberArr2.length-1; // индекс последенго элемента
            for (int i = 0; i <= numberArr2.length - 1; i++) {
                reversed[i] = Integer.parseInt(numberArr2[k]);
                k--;
            }
            for (int i=0; i< reversed.length;i++){
                if (i%2==0){ // удвоить значение в нечетных позициях
                    reversed[i]=reversed[i]*2;
                    if (reversed[i]>9){
                        reversed[i]= (reversed[i]/10)+(reversed[i]%10);
                    }
                }
            }
            int sum = 0;
            for (int i = 0; i < reversed.length; i++) { // добавляем все цифры
                sum = sum + reversed[i];
            }
            int result = 10 - sum%10; //вычитаем последнюю цифру суммы из 10
            return (result == Integer.parseInt(numberArr[numberArr.length-1])); // результат должен быть равен контрольной цифре (последней)
        }
        return false;
    }
    public static String numToEng(int a) { //999  55
        String result ="";
        String [] units =new String[]{"one","two","three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        String [] from11to19 = new String[]{"eleven", "twelve", "thirteen","fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String [] integer = new String[]{"twenty", "thirty", "forty","fifty", "sixty", "seventy", "eighty", "ninety"};

        if (a==0)
            return "zero";

        if (a>0 && a<10) // единицы
            result = units[a-1];

        if(a>10 && a<20) // числа от 10 до 20
            result = from11to19[a%10-1];

        if (a>19 && a<100){
            if(a%10==0){
                result=integer[a%10-2];
            }else{
                result=integer[a/10-2]+" "+units[a%10-1];
            }
        }
        if(a>99 && a<1000){
            if (a%100==0){ // 2 числа
                result = units[a/100-1]+ " "+ "hundred";
            }else if(a%100/10==0){ // если десятки = 0
                result = units[a/100-1]+ " "+ "hundred"+ " "+ units[a%10-1];
            }else {
                result = units[a/100-1]+ " "+ "hundred"+ " "+ integer[a%100/10-2]+" "+ units[a%10-1];
            }
        }
        return result;
    }
    public static String getSha256Hash(String str) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] bytes = algorithm.digest(str.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, bytes);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        return hexString.toString();
    }
    public static String correctTitle(String s) {
        StringBuilder result = new StringBuilder();
        String[] words = s.toLowerCase().split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("the") || words[i].equals("and") || words[i].equals("of") || words[i].equals("in")) {
                result.append(" ");
                result.append(words[i]);
            } else {
                result.append(" ");
                result.append(words[i].substring(0,1).toUpperCase() + words[i].substring(1).toLowerCase());
            }
        }
        return result.toString().substring(1, result.length());
    }

    public static String hexLattice(int n){
        StringBuilder result = new StringBuilder();
        if (n==1) {
            return result.append(" o ").toString();
        } else {
            n--;
            int multiplier = 1;  // множитель
            while (n > 0) {
                n = n - multiplier * 6;
                multiplier++;
            }
            if (n < 0) {
                return "Invalid";
            } else {
                int centreLine = multiplier * 2 - 1;
                int lines = centreLine/2;
                int spaces = lines;
                int circles = spaces+1;
                for (int i = 0; i < lines; i++) {
                    result.append(" ".repeat(spaces));
                    result.append("o ".repeat(circles));
                    result.append(" ".repeat(spaces));
                    result.append("\n");
                    spaces--;
                    circles++;
                }
                result.append("o ".repeat(centreLine));
                result.append("\n");
                spaces = 1;
                while (spaces!=circles) {
                    result.append(" ".repeat(spaces));
                    result.append("o ".repeat(circles-1));
                    result.append(" ".repeat(spaces));
                    result.append("\n");
                    spaces++;
                    circles--;
                }
                return result.toString();
            }
        }
    }
}


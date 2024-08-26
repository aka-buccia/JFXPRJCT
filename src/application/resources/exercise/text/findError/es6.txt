import java.util.Scanner;

public class es6{
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        System.out.print("inserisci un numero maggiore di zero: ");
        double n = scan.nextDouble();

        double radice = Math.sqrt(n);

        if (radice < 0)
            System.out.println("hai inserito un numero minore di zero");
        else
            System.out.println("la radice di " + n + " = " + radice);


    }
}

import java.util.Scanner;

public class es4{
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        
        System.out.print("inserisci un primo numero intero maggiore di 0: ");
        int n1 = scan.nextInt();
        System.out.print("inserisci un secondo numero intero maggiore di 0: ");
        int n2 = scan.nextInt();
        int risultato = 1;

        for (int i = 0; i == n2; i++){
            risultato = risultato * n1;
        }

        System.out.println(n1 + " elevato alla " + n2 + " = " + risultato);


    }
}

import java.util.Scanner;

public class es5{
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        System.out.print("inserisci il numero di cui vuoi calcolare la tabellina: ");
        int n = scan.nextInt();

        for (int i = 0; i < 9; i++){
            System.out.println(n + "*" + i + "=" + n*i );
        }
        

    }
}

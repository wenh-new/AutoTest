import java.util.Scanner;

public class primeNumber {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        boolean[] isPrime = new boolean[100];
        for(int i=0;i<isPrime.length;i++)
        {
            isPrime[i]=true;
//            System.out.print(isPrime[i]);
        }
        for(int i=2;i<isPrime.length;i++){
            if(isPrime[i]) {
                for (int k = 2; i * k < isPrime.length; k++) {
                    isPrime[i * k] = false;
                }
            }
        }
        for(int i = 2;i<isPrime.length;i++){
            if(isPrime[i]){
                System.out.print(i+" ");

            }

        }
    }
}

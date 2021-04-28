
public class Hash_Multi {
    static double A;
    static int m;
    static int k;

    public static void main(String[] args) {
        // modify A, m, k according to specifications
        A = Math.sqrt(5) / 2;
        m = 16384;
        k = 654321;

        System.out.println("\nhash value: " + Math.floor(m * (k * A % 1)) + "\n");
    }
}
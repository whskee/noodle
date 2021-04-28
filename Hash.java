
// hashing with open addressing
public class Hash {
    static String T[];
    static int m;

    public static void main(String[] args) {
        /*  
        IMPORTANT -->
            1. to enter null into the hash table, simply enter null, not "null"
            2. modify both hash functions according to specifications
        */
        T = new String[] { "10", "del", "9", "del", "del", "8", "7", "6", "5", "4", "3", "2", "1" };
        m = T.length;
        System.out.println();
        printLoadFactor();

        // write code here
        hashInsert(25);
        
        printHashTable();
    }

    // modify h (hash function) according to specification
    static int h(int k) {
        return k % 13;
    }

    // modify h prime according to specification
    static int h(int k, int index) {
        return (h(k) + index) % 13;
    }

    static int hashSearch(int k, boolean print) {
        int i = 0;
        int j;
        if (print) {
            System.out.print("The cells probed ---> ");
        }
        do {
            j = h(k, i);
            if (print) {
                System.out.print(j + " ");
            }
            if (T[j] == "del" || Integer.parseInt(T[j]) == k) {

                System.out.println();
                return j;
            }

            i++;
        } while (T[j] != null || i != m);
        return -1;
    }

    static int hashInsert(int k) {
        int i = 0;

        System.out.print("The cells probed ---> ");
        int j;
        do {
            j = h(k, i);
            if (T[j] == null || T[j] == "del") {
                T[j] = String.valueOf(k);
                System.out.print(j + "\n");
                return j;
            }
            System.out.print(j + " ");
            i++;
        } while (i != m);

        System.out.println("error: hash table overflow");
        return -1;
    }

    static void hashDelete(int k) {
        if (hashSearch(k, false) != -1) {
            int j = hashSearch(k, true);
            T[j] = "del";
        }
    }

    static void printLoadFactor() {
        int n = 0;
        for (int i = 0; i < T.length; i++) {
            if (T[i] == "del" || T == null) {
                n++;
            }
        }
        System.out.println("Load Factor: " + (T.length - n) + "/" + T.length);
    }

    static void printHashTable() {
        System.out.print("j\t\t");
        for (int i = 0; i < T.length; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.print("T[j]\t\t");
        for (String i : T) {
            System.out.print(i + "\t");
        }
        System.out.println("\n");
    }
}
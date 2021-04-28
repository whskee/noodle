
public class DisjointSets {
    static int[] A;
    static boolean findWithPathCompression;

    public static void main(String[] args) {

        // change to false if you want to find without path compression
        findWithPathCompression = true;

        A = new int[] { 0,
                // input array contents here, this is an example from HW_3: Q3
                -3, 1, 1, 3, 1, 5, 5, 7, -3, 9, 9, 11, 9, 13, 13, 15
        };

        // input commands here
        findSet(7);
        findSet(15);
        union(16, 4);

        printSet();
    }

    public static void makeSet(int x) {
        A[x] = 0;
    }

    public static void union(int x, int y) {
        link(findSet(x), findSet(y));
    }

    public static void link(int x, int y) {
        if (-A[x] > -A[y]) {
            A[y] = x;
        } else {
            if (-A[x] == -A[y]) {
                A[y] = A[y] - 1;
            }
            A[x] = y;
        }
    }

    public static int findSet(int x) {
        if (A[x] <= 0) {
            return x;
        } else {
            // find with path compression
            if (findWithPathCompression) {
                A[x] = findSet(A[x]);
                return A[x];
            }

            // find without path compression
            return findSet(A[x]);
        }
    }

    public static void printSet() {
        System.out.print("i:\t");
        for (int x = 1; x < A.length; x++) {
            System.out.print("\t" + x);
        }
        System.out.print("\nA[i]:\t");
        for (int x = 1; x < A.length; x++) {
            System.out.print("\t" + A[x]);
        }
        System.out.println();
    }

}
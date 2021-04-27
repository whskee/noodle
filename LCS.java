public class LCS {

    static int length;

    public static void main(String[] args) {
        String x = "CBBA";
        String y = "DCACBA";
        
        lcsLength(x, y);
    }

    static void lcsLength(String stringX, String stringY) {
        char[] X = stringX.toCharArray();
        char[] Y = stringY.toCharArray();

        int m = X.length;
        int n = Y.length;

        String[][] b = new String[m + 1][n + 1];
        int[][] c = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X[i - 1] == Y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "up_left";
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = "up";
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = "left";
                }
            }
        }

        System.out.print("\nLongest Common Subsequence ---> ");
        printLCS(b, X, X.length, Y.length);
        System.out.println(" (" + length + ")");
        //printTable(c);
        //printArrows(b);
        printArr(c);
        System.out.println();
    }

    static void printLCS(String[][] b, char[] X, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (b[i][j] == "up_left") {
            printLCS(b, X, i - 1, j - 1);
            length += 1;
            System.out.print(X[i - 1]);
        } else if (b[i][j] == "up") {
            printLCS(b, X, i - 1, j);
        } else {
            printLCS(b, X, i, j - 1);
        }
    }

    static void printTable(int[][] m) {
        System.out.print("\t");
        for (int j = 0; j < m[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < m.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < m[i].length; j++) {
                System.out.printf("%d ", m[i][j]);
            }
            System.out.println();
        }
    }

    static void printArr(int[][] m) {
        for (int i = 1; i < m.length; i++) {
            for (int j = 1; j < m[i].length; j++) {
                System.out.printf("m[%d, %d] = %d\t", i, j, m[i][j]);
            }
            System.out.println();
        }
    }

    static void printArrows(String[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.printf("%s\t", m[i][j]);
            }
            System.out.println();
        }
    }
}
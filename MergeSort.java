public class MergeSort {
    public static void main(String[] args) {
        int[] A = { 11, 15, 14, 13, 12 };
        
        printInitialArr(A);
        MergeSort mergeSort = new MergeSort(A);
        System.out.println("\nTotal calls ---> " + mergeSort.mergeSortAR + " mergeSort calls + " + mergeSort.mergeAR
                + " merge calls = " + mergeSort.activationRecord + "\n");
    }

    private int counter = 1;
    private int[] A;
    public int activationRecord = 0, mergeSortAR = 0, mergeAR = 0;

    public MergeSort(int[] arr) {
        A = new int[arr.length + 1];
        // iteration to put everything in an array for it to be sorted
        for (int x = 1; x < A.length; x++) {
            A[x] = arr[x - 1];
        }
        mergeSort(A, 1, A.length - 1);
        activationRecord = mergeSortAR + mergeAR;
    }

    private void mergeSort(int[] A, int p, int r) {
        mergeSortAR++;
        System.out.println("mergeSort(A, " + p + ", " + r + ")");
        if (p < r) {
            int q = (int) Math.floor((p + r) / 2);
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
            printArr();
        }
    }

    private void merge(int[] A, int p, int q, int r) {
        mergeAR++;
        System.out.println("merge(A, " + p + ", " + q + ", " + r + ")");

        // make a new array and copy array A to B
        int[] B = A.clone();
        int i = p;
        int j = q + 1;
        int z = p;

        while (i <= q && j <= r) {
            if (B[i] <= B[j]) {
                A[z] = B[i];
                i++;
                z++;
            } else {
                A[z] = B[j];
                j++;
                z++;
            }
        }

        if (j > r) {
            while (z <= r && i <= q) {
                A[z] = B[i];
                z++;
                i++;
            }
        } // else i > z
        else {
            while (z <= r && j <= r) {
                A[z] = B[j];
                z++;
                j++;
            }
        }
    }

    public void printArr() {
        System.out.print(counter + " ---> ");
        counter++;
        for (int x = 1; x < A.length; x++) {
            System.out.print(A[x]);
            if (x < A.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }

    public static void printInitialArr(int[] A) {
        System.out.print("\nInitial: ");
        for (int x = 0; x < A.length; x++) {
            System.out.print(A[x]);
            if (x < A.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }
}
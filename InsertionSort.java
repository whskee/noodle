public class InsertionSort {
    public static void main(String[] args) {
        int[] A = { 11, 15, 14, 13, 12 };
        printInitialArr(A);
        
        InsertionSort insertionSort = new InsertionSort(A);
        
        System.out.println(insertionSort.elementWiseCounter);
    }

    public static void printInitialArr(int[] A) {
        System.out.print("Initial: ");
        for (int x = 0; x < A.length; x++) {
            System.out.print(A[x]);
            if (x < A.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }

    private int counter = 1;
    private int[] arr;
    private int elementWiseCounter = 1;

    public InsertionSort(int[] inputArr) {
        arr = new int[inputArr.length + 1];

        // iteration to put everything in an array for it to be sorted
        for (int x = 1; x < arr.length; x++) {
            arr[x] = inputArr[x - 1];
        }

        insertionSort(arr);
    }

    private void insertionSort(int[] arr) {
        for (int j = 2; j < arr.length; j++) {
            int key = arr[j];
            // code below inserts A[j] into a sorted sublist
            int i = j - 1;

            // this just updates the comparison values in case the while loop does not
            // satisfy
            if (!(i > 0) || !(arr[i] > key)) {
                System.out.println("Comparison " + elementWiseCounter + ": " + arr[i] + " > " + arr[j] + "?");
                elementWiseCounter++;
            }

            // this while loop shifts everything to the right
            while (i > 0 && arr[i] > key) {
                System.out.println("Comparison " + elementWiseCounter + ": " + arr[i] + " > " + arr[j] + "?");
                elementWiseCounter++;
                arr[i + 1] = arr[i];
                i -= 1;
                printArr();
            }

            // after shifting to the right, insert
            arr[i + 1] = key;
            printArr();
        }
    }

    public void printArr() {
        System.out.print(counter + " = ");
        counter++;
        for (int x = 1; x < arr.length; x++) {
            System.out.print(arr[x]);
            if (x < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }
}
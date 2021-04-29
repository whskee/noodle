public class QuickSort {
    public static void main(String[] args) {
        // int[] arr = { 5, 1, 4, 7, 6, 3, 9, 2 };
        int[] arr = { 22, 21, 20, 18, 17, 19, 13, 12, 14, 15, 11, 16 };

        printInitialArr(arr);
        QuickSort qSort = new QuickSort(arr);
        System.out.println("\nTotal calls ---> " + qSort.quickSortAR + " quickSort calls + " + qSort.partitionAR
                + " partition calls = " + qSort.activationRecord + "\n");

    }

    private int counter = 1;
    private int[] arr;
    private int elementWiseCounter = 1;
    public int activationRecord = 0, partitionAR = 0, quickSortAR = 0;

    public QuickSort(int[] inputArr) {
        arr = new int[inputArr.length + 1];
        // iteration to put everything in an array for it to be sorted
        for (int x = 1; x < arr.length; x++) {
            arr[x] = inputArr[x - 1];
        }
        quickSort(arr, 1, arr.length - 1);
        activationRecord = partitionAR + quickSortAR;
    }

    // recursive functions use the call stack (LIFO)
    private void quickSort(int[] arr, int p, int r) {
        quickSortAR++;
        System.out.println("quickSort(A, " + p + ", " + r + ")");
        if (p < r) {
            int q = partition(arr, p, r);
            quickSort(arr, p, q - 1);
            quickSort(arr, q + 1, r);
        }
    }

    private int partition(int[] arr, int p, int r) {
        partitionAR++;
        System.out.println("partition(" + p + ", " + r + ")");
        int pivot = arr[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            elementWiseComparison(j, pivot);
            if (arr[j] <= pivot) {
                i += 1;
                exchange(arr, i, j);
                printArr();
            }
        }
        exchange(arr, r, i + 1);
        return i + 1;
    }

    private void exchange(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void elementWiseComparison(int j, int pivot) {
        System.out.println("Comparison " + elementWiseCounter + ": " + arr[j] + " <= " + pivot + "?");
        elementWiseCounter++;
    }

    public void printArr() {
        System.out.print(counter + " ---> ");
        counter++;
        for (int x = 1; x < arr.length; x++) {
            System.out.print(arr[x]);
            if (x < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }

    public static void printInitialArr(int[] A) {
        System.out.print("Initial: ");
        String s = "";
        for (int x : A) {
            System.out.print(s + x);
            s = ", ";
        }
        System.out.println("\n");
    }
}
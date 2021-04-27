public class Max_Heap {

    public static void main(String[] args) {
        int[] arr = new int[] {
                // insert array contents here
                88, 89, 93, 91, 90, 94, 99, 92, 96, 98, 95, 97
        };

        MaxHeap heap = new MaxHeap();
        heap.buildMaxHeap(arr);

        // insert commands here
        heap.insert(80);

        heap.printArr();
    }

}

class MaxHeap {
    private int[] A;
    private int size;
    private int heapifyCalls;

    public void buildMaxHeap(int[] arr) {
        heapifyCalls = 0;
        size = arr.length;
        this.A = new int[size + 1];

        for (int i = 1; i <= size; i++) {
            this.A[i] = arr[i - 1];
        }
        for (int i = (int) Math.floor(size / 2); i >= 1; i--) {
            maxHeapify(i);
        }
        System.out.println("\nbuildMaxHeap heapifyCalls:\t---> " + heapifyCalls);
    }

    private void maxHeapify(int i) {
        heapifyCalls++;
        int left = getLeft(i);
        int right = getRight(i);
        int largest;

        if (left <= size && A[left] > A[i]) {
            largest = left;
        } else {
            largest = i;
        }

        if (right <= size && A[right] > A[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    public void extractMax() {
        heapifyCalls = 0;
        if (size < 1) {
            System.out.print("Error: heap empty\n");
        } else {
            A[1] = A[size];
            size--;
            maxHeapify(1);
        }
        System.out.println("extractMax heapifyCalls:\t---> " + heapifyCalls);
    }

    public void insert(int key) {
        size++;
        int[] temp = new int[size + 1];
        for (int i = 1; i <= size - 1; i++) {
            temp[i] = A[i];
        }
        A = temp;
        increaseKey(size, key);
    }

    public void increaseKey(int i, int key) {
        if (!(i > size)) {
            if (key < A[i] && A[i] != 0) {
                System.out.println("Error: new key is smaller than current key");
            } else {
                A[i] = key;
                while (i > 1 && A[getParent(i)] < A[i]) {
                    swap(i, getParent(i));
                    i = getParent(i);
                }
            }
        } else {
            System.out.println("Error: index is greater than heap size");
        }
    }

    public void heapSort(/** int[] A */
    ) {
        // buildMaxHeap(A);
        int temp = size;
        for (int i = temp; i >= 2; i--) {
            swap(1, i);
            size--;
            heapifyCalls = 0;
            maxHeapify(1);
        }
        size = temp;
    }

    private void swap(int a, int b) {
        int temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }

    private int getParent(int index) {
        return (int) Math.floor(index / 2);
    }

    private int getLeft(int index) {
        return (2 * index);
    }

    private int getRight(int index) {
        return (2 * index + 1);
    }

    public void printArr() {
        System.out.print("i:\t\t");
        for (int x = 1; x <= size; x++) {
            System.out.print("\t" + x);
        }
        System.out.print("\nA[i]:\t");
        for (int x = 1; x <= size; x++) {
            System.out.print("\t" + A[x]);
        }
        System.out.print("\n");
    }
}
/*
- HeapSort which prints every valid Heap structure during the process.

- Heap is printed by levels, starting by the root of the tree and printing
    child elements left to right.

- The program expects input of:
    n elements ; where n is the size of the array

- Example: 5 1 2 3 4 5
*/

import java.util.Scanner;

public class HeapSort {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Array size
        int n = s.nextInt();
        int[] a = new int[n];
        int i = 0;

        // Array input
        while(i != n) {
            a[i] = s.nextInt();
            i++;
        }
        
        heapsort(a, n);
        s.close();
    }

    /*
        Heapify a subtree with root index i
        length is heap size
    */
    public static void heapify(int a[], int i, int length) {
        if(i < (length/2)) {
            int s = 2*i+1;

            if(s+1 < length) {
                if(a[s] < a[s+1]) {
                    s++;
                }
            }

            if(a[i] < a[s]) {
                int x = a[i];
                a[i] = a[s];
                a[s] = x;
                // Heapifying subtree
                heapify(a, s, length);
            }
        }
    }

    // Function to rearrange the initial array into a valid heap
    public static void buildHeap(int[] a) {
        for(int i = (a.length/2); i >= 0; i--) {
            heapify(a, i, a.length);
        }
    }

    public static void heapsort(int[] a, int n) {
        buildHeap(a);
        // Printing initial valid heap
        printHeap(a, a.length);

        for(int i = n-1; i > 0; i--) {
            int x = a[0];
            a[0] = a[i];
            a[i] = x;

            heapify(a, 0, i);
            printHeap(a, i);
        }
    }

    /* 
        A function to print the unprocessed part of 
        a valid heap every time we have a valid heap

        Tree levels separated by '|'
    */
    public static void printHeap(int a[], int end) {
        int i = 0;
        int separator = 2;

        while(i != end) {
            if(i == separator-1) {
                System.out.print("| ");
                separator *= 2;
            }
            System.out.print(a[i] + " ");
            i++;
        }
        System.out.println();
    }
}

/*
- This program sorts 32-bit integers based on the number of bits equal
    to 1 in the binary representation of that number.
- Example: array 6 5 4 1 3 would be arranged 4 1 6 5 3 because 4 = 100, 1 = 1, 6 = 110, 5 = 101 and 3 = 11. 
    Numbers having the same number of bits 1 remain in the same order as before editing.
- Input: length of table n, followed by n integers.
- Output: the process of entering elements in the final (arranged table) is printed. 
    For each record, (el, pos) is written to stdout, where el is the element being rearranged, 
    and pos is the index in the table where the element is positioned. 
    In the last line, the arranged table is printed.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class CountingSortByBits {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ArrayList<int[]> actions = new ArrayList<int[]>();
        int n = s.nextInt();
        int[] array = new int[n];
        int[] bits = new int[n];

        for(int i = 0; i < n; i++) {
            int num = s.nextInt();
            array[i] = num;
            bits[i] = bitCounter(num);
        }

        int[] sorted = countSort(bits, array, actions);

        for(int i = 0; i < actions.size(); i++) {
            System.out.printf("(%d,%d)%n", actions.get(i)[0], actions.get(i)[1]);
        }

        for(int i = 0; i < n; i++) {
            System.out.print(sorted[i] + " ");
        }
        System.out.println();

        s.close();
    }

    public static int bitCounter(int n) {
        int counter = 0;
        while(n > 0) {
            if((n & 1) > 0 ) {
                counter++;    
            }
            n = n >> 1;
        }
        return counter;
    }

    public static int[] countSort(int[] bits, int[] array, ArrayList<int[]> actions) {
        int[] count = new int[33];
        int[] sorted = new int[bits.length];

        for(int i = 0; i <= 32; i++) {
            count[i] = 0;
        }
        for(int i = 0; i < bits.length; i++) {
            count[bits[i]]++;
        }
        for(int i = 1; i <= 32; i++) {
            count[i] += count[i-1];
        }
        for(int i = bits.length-1; i >= 0; i--) {
            int[] elToPos = new int[2];
            int x = bits[i];
            elToPos[0] = array[i];
            elToPos[1] = count[x]-1;
            actions.add(elToPos);
            count[x]--;
            sorted[count[x]] = array[i];
        }
        return sorted;
    }
}

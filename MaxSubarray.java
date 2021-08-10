/*
    Maximum sum of integers in a subarray using divide and conquer algorithm.
*/

import java.util.Scanner;

public class MaxSubarray {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[] in = s.nextLine().split(" ");
        int[] a = new int[in.length];

        for(int i = 0; i < in.length; i++) {
            a[i] = Integer.parseInt(in[i]);
        }

        maxSum(a, 0, a.length-1);

        s.close();
    }

    private static int maxSum(int[] a, int l, int r) {
        if(l == r) {
            print(a, l, r, a[l]);
            return a[l];
        }

        int d = (l+r)/2;
        int tmp = 0;
        int left = Integer.MIN_VALUE/2;
        int right = Integer.MIN_VALUE/2;
        for(int i = d; i >= l; i--) {
            tmp += a[i];
            if(left < tmp) {
                left = tmp;
            }
        }

        tmp = 0;
        for(int i = d+1; i <= r; i++) {
            tmp += a[i];
            if(right < tmp) {
                right = tmp;
            }
        }

        int sum = Math.max(Math.max(maxSum(a, l, d), maxSum(a, d+1, r)), left+right);
        print(a, l, r, sum);
        return sum;
    }

    private static void print(int[] a, int l, int r, int sum) {
        System.out.print("[" + a[l]);
            for(int i = l+1; i <= r; i++) {
                System.out.print(", " + a[i]);
            }
        System.out.println("]: " + sum);
    }
}
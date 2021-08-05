/*
- This program compares time complexity of linear and binary search.

- Another interesting thing is that first few results are kind of incorrect
	because JVM uses Just-In-Time (JIT) compiling.

- Measurements are not incorrect, but they are an anomaly in comparison to other measurements.

- The virtual machine interprets the commands given in the java class, but when 
	it finds that certain commands are repeated many times, it translates them 
	into machine code and executes them directly on the processor. 
*/

import java.util.Random;

public class LinearVsBinarySearch {
    public static void main(String[] args) {
		System.out.println("    n:   |  linear  |  binary  |");
		System.out.println("---------+----------+-----------");

        for(int i = 1000; i <= 100000; i = i + 1000) {
        	System.out.printf("%7d  |%7d   |%7d   |\n", i, timeLinear(i), timeBinary(i));
        }
    }

    // for given n, returns arranged table of 1:n numbers
    public static int[] generateTable(int n) {
    	int[] t = new int[n];

    	for(int i = 1; i <= n; i++)
    		t[i-1] = i;
    	return t;
    }

    // linear search
    public static int findLinear(int[] a, int v) {
    	for(int i = 0; i < a.length; i++)
    		if(a[i] == v)
    			return i;
    	return -1;
    }

    // binary search
    public static int findBinary(int[] a, int l, int r, int v) {
    	int ix = l+(r-l)/2;

    	while(l <= r) {
    		if(a[ix] == v)
    			return ix;
    		if(v > a[ix])
    			l = ix+1;
    		else
    			r = ix-1;
    		ix = l+(r-l)/2;
    	}
    	return -1;
    }

    // linear time
    public static long timeLinear(int n) {
    	int[] table = generateTable(n);
    	Random r = new Random();

    	long startTime = System.nanoTime();
    	for(int i = 0; i < 1000; i++) {
    		// random number 1:n
    		int randomNum = r.nextInt(n) + 1;
    		findLinear(table, randomNum);
    	}
    	return (System.nanoTime() - startTime) / 1000;
    }

    // binary time
    public static long timeBinary(int n) {
    	int[] table = generateTable(n);
    	Random r = new Random();

    	long startTime = System.nanoTime();
    	for(int i = 0; i < 1000; i++) {
    		// random number 1:n
    		int randomNum = r.nextInt(n) + 1;
    		findBinary(table, 0, n-1, randomNum);
    	}
    	return (System.nanoTime() - startTime) / 1000;
    }
}

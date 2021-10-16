import java.util.Scanner;

class Matrix {
	private int[][] m;
	public int n; // only square matrices

	public Matrix(int n) {
		this.n = n;
		m = new int[n][n];
	}

    // set value at i,j
	public void setV(int i, int j, int val) {
        m[i][j] = val;
	}

    // get value at index i,j
	public int v(int i, int j) {
        return m[i][j];
	}

    // return a square submatrix from this
	public Matrix getSubmatrix(int startRow, int startCol, int dim) {
		Matrix subM = new Matrix(dim);
        
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				subM.setV(i, j, m[startRow+i][startCol+j]);
            }
        }

		return subM;
	}

    // write this matrix as a submatrix from b (useful for the resultult of multiplication)
	public void putSubmatrix(int startRow, int startCol, Matrix b) {
		for(int i = 0; i < b.n; i++){
			for(int j=0; j < b.n; j++){
				setV(startRow+i, startCol+j, b.v(i,j));
            }
        }
	}

    // matrix addition
	public Matrix sum(Matrix b) {
		Matrix c = new Matrix(n);

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				c.setV(i, j, m[i][j] + b.v(i, j));
			}
        }

		return c;
	}

    // matrix subtraction
	public Matrix sub(Matrix b) {
		Matrix c = new Matrix(n);

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				c.setV(i, j, m[i][j] - b.v(i, j));
			}
		}
        
		return c;
	}

    public int sumOfEl() {
        int val = 0;

        for(int i = 0; i < this.n; i++) {
			for(int j = 0; j < this.n; j++) {
                val += this.v(i, j);
			}
		}
        
        return val;
    }

	// simple multiplication
	public Matrix mult(Matrix b, int n) {
        Matrix c = new Matrix(n);
        int i, j, k;

        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                int x = 0;
                c.setV(i, j, 0);
                for(k = 0; k < n; k++) {
                    x += this.v(i, k) * b.v(k, j);
                }
                c.setV(i, j, x);
            }
        }

        return c;
	}


    // Strassen multiplication
	public Matrix multStrassen(Matrix b, int cutoff) {
        Matrix result = new Matrix(this.n);

        if(this.n == cutoff) {
            result = this.mult(b, this.n);
        } else {
            int newDim = this.n / 2;

            Matrix A11 = this.getSubmatrix(0, 0, newDim);
            Matrix A12 = this.getSubmatrix(0, newDim, newDim);
            Matrix A21 = this.getSubmatrix(newDim, 0, newDim);
            Matrix A22 = this.getSubmatrix(newDim, newDim, newDim);

            Matrix B11 = b.getSubmatrix(0, 0, newDim);
            Matrix B12 = b.getSubmatrix(0, newDim, newDim);
            Matrix B21 = b.getSubmatrix(newDim, 0, newDim);
            Matrix B22 = b.getSubmatrix(newDim, newDim, newDim);

            Matrix M1 = (A11.sum(A22)).multStrassen(B11.sum(B22), cutoff);
            Matrix M2 = (A21.sum(A22)).multStrassen(B11, cutoff);
            Matrix M3 = (A11).multStrassen(B12.sub(B22), cutoff);
            Matrix M4 = (A22).multStrassen(B21.sub(B11), cutoff);
            Matrix M5 = (A11.sum(A12)).multStrassen(B22, cutoff);
            Matrix M6 = (A21.sub(A11)).multStrassen(B11.sum(B12), cutoff);
            Matrix M7 = (A12.sub(A22)).multStrassen(B21.sum(B22), cutoff);

            System.out.println("m1: " + M1.sumOfEl());
            System.out.println("m2: " + M2.sumOfEl());
            System.out.println("m3: " + M3.sumOfEl());
            System.out.println("m4: " + M4.sumOfEl());
            System.out.println("m5: " + M5.sumOfEl());
            System.out.println("m6: " + M6.sumOfEl());
            System.out.println("m7: " + M7.sumOfEl());

            Matrix C11 = (M1.sum(M4)).sub(M5).sum(M7);
            Matrix C12 = M3.sum(M5);
            Matrix C21 = M2.sum(M4);
            Matrix C22 = M1.sub(M2).sum(M3.sum(M6));

            result.putSubmatrix(0, 0, C11);
            result.putSubmatrix(0, newDim, C12);
            result.putSubmatrix(newDim, 0, C21);
            result.putSubmatrix(newDim, newDim, C22);
        }

        return result;
	}
}

public class Strassen {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int limit = s.nextInt();
        Matrix m1 = new Matrix(n);
        Matrix m2 = new Matrix(n);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int val = s.nextInt();
                m1.setV(i, j, val);
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int val = s.nextInt();
                m2.setV(i, j, val);
            }
        }
        
        Matrix result = m1.multStrassen(m2, limit);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(result.v(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        s.close();
	}
}
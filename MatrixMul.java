/**
 * An example to compare the differences between mutltithreaded application vs single threaded.
 *
 * The application computes the multiplication of 2 matrix.
 */


package com.example.xs;

import javax.imageio.ImageTranscoder;
import java.util.concurrent.*;

public class MatrixMul implements Runnable{

    //Properties
    private static int[][] a, b;
    private static int numRowA, numColA, numRowB, numColB;
    private static boolean canMul;

    private int i;

    public static int[][] result;

    //Constructors
    public MatrixMul(int[][] a, int[][] b)
    {
        this.a = a;
        this.b = b;

        //find the size of matrix
        numRowA = a.length;
        numRowB = b.length;
        //find the size of matrix
        numColA = a[0].length;
        numColB = b[0].length;

        //create matrix for result
        result = new int[numRowA][numColB];

        canMul = (numColA == numRowB);
        System.out.println(canMul);
    }
    public MatrixMul(int i)
    {
        this.i = i;
    }


    public void sequencalMul ()
    {

        if(canMul){
            //algorithm.
            for(int i=0; i<numRowA; i++)
                for(int j=0;j<numColB;j++)
                {
                    int val=0;
                    //compute value for each element in matrix
                    for(int q =0;q<numColA;q++)
                    {

                        val += (a[i][q]*b[q][j]);
                    }
                    result[i][j]=val;
                }
        }
        else
        {
            System.out.println("Matrix cannot multiply");
        }

    }

    @Override
    public void run() {

        if(canMul)
        {
            int val=0;
            //compute value for each element in matrix
            int j;
            for(j=0;j<numColB;j++)
            {
                for(int q =0;q<numColA;q++)
                {

                    val += (a[i][q]*b[q][j]);
                }
                result[i][j]=val;
            }
        }
        else
        {
            System.out.println("Matrix cannot multiply");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        //Create constant matrix
        int[][] A  = {{1,2,3,5},{4,5,6,5},{7,8,9,5},{1,2,3,7}};
        int[][] B  = {{1,2,3,5},{4,5,6,5},{7,8,9,5},{1,2,3,6}};

        //Initialise matrix values
        MatrixMul matrix = new MatrixMul(A,B);

        //threadpool method to computer solution
            ExecutorService pool = Executors.newFixedThreadPool(4);
            Long parStartTime = System.nanoTime();
            for(int i=0; i<numRowA; i++) {
                pool.submit(new MatrixMul(i));
            }
            pool.shutdown();
            pool.awaitTermination(10000, TimeUnit.SECONDS);
            Long parEndTime = System.nanoTime()-parStartTime;
            System.out.printf("The total time parallel took was %.3f ms %n", parEndTime/1e6);


        //sequential way of computing multiplication matrix
            Long seqStartTime = System.nanoTime();
            matrix.sequencalMul();
            Long seqEndTime = System.nanoTime()-parStartTime;
            System.out.printf("The total time Sequential took was %.3f ms %n", seqEndTime/1e6);

        /**
         * Print matrix solution
         */
//        for(int[] test : matrix.result)
//        {
//            for(int test1: test)
//            {
//                System.out.print(test1+ "\t");
//            }
//            System.out.println();
//        }



//       int[][] Ans = matrix.sequencalMul();

//        for(int[] qwerty : Ans)
//        {
//            for(int qwert: qwerty)
//            {
//                System.out.print(qwert+ "\t");
//            }
//            System.out.println();
//        }


    }


}

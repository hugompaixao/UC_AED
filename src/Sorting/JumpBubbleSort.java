package Sorting;
//package aed.sorting;

import java.util.concurrent.TimeUnit;

/*class Stopwatch {

    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime(TimeUnit milliseconds) {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
}*/

public class JumpBubbleSort extends Sort {

    public static <T extends Comparable<T>> void sort(T[] a) {
        int d = a.length - 1;
        boolean swapped;
        for(int i = 0; i < a.length; i++) {
            swapped = false;
            for(int j = 0; j < a.length-d; j++) {
                if(less(a[j+d], a[j])) {
                    exchange(a, j, j+d);
                    swapped = true;
                }
            }
            if(d == 1 && !swapped)
                break;
            else if(d != 1)
                d = (int) (d * 0.77);
        }
    }



    public static void main(String[] args) {
        /* Media de tempo de execução

         * random = 0.000860
         * ordenado = 0.00102
         * inverso = 0.00098
         * parcial = 0.03660
         */

        /* Razao dobrada
         * random = 0.022620
         * ordenado = 0.00944
         * inverso = 0.019640
         * parcial = 0.0008454
         */

        /*double[] time1 = new double[50], time2 = new double[50];
        for (int i = 0; i < 50; i++) {
            Integer[] var1 = new Integer[100000], var2 = new Integer[50000];
            int x = 100000, y = 50000;
            for (int j = 0; j < 100000; j++) {
                var1[j] = (int) (Math.random() * 100 + 1) + 1;
            }
            for (int j = 0; j < 50000; j++) {
                var2[j] = (int) (Math.random() * 100 + 1) + 1;
            }

            Stopwatch timer1 = new Stopwatch();
            sort(var1);
            time1[i] = timer1.elapsedTime(TimeUnit.MILLISECONDS);
            Stopwatch timer2 = new Stopwatch();
            sort(var2);
            time2[i] = timer2.elapsedTime(TimeUnit.MILLISECONDS);
        }
        double sum1 = 0, sum2 = 0;
        for(int i = 0; i < 50; i++){
            sum1 += time1[i];
            sum2 += time2[i];
        }
        System.out.println(sum1/50);
        System.out.println(sum2/50);
        System.out.println(sum1/50 / sum2/50);*/
    }
}
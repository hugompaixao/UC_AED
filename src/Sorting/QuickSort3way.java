package Sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort3way extends Sort {
	
	private static final int INSERTION_SORT_CUTOFF = 10;
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static void randomPivot(Comparable[] a, int low, int high) {
		Random r = new Random();
		int v = r.nextInt(high - low) + low;
		exchange(a, low, v);
	}
	
	@SuppressWarnings("rawtypes") 
	public static Comparable[] ShuffleArray(Comparable[] a) {
			Random r = new Random();
			
			for (int i = 0; i < a.length; i++) {
				int randomIndexToSwap = r.nextInt(a.length);
				int temp = (int) a[randomIndexToSwap];
				a[randomIndexToSwap] = a[i];
				a[i] = temp;
			}
			return a;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean equal(Comparable v, Comparable w) {
		if (v == w)
			return true;
		return v.compareTo(w) == 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void insertionSort(Comparable[] a, int lo, int hi) {
		for (int i = lo; i <= hi; i++)
			for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
				exchange(a, j, j - 1);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static int median3(Comparable[] a, int i, int j, int k) {
        if(less(a[i], a[j])) {
        	if(less(a[j], a[k]))
        		return j;
        	else if(less(a[i], a[k]))
        		return k;
        	else
        		return k;
        }
        else {
        	if(less(a[k], a[j]))
        		return j;
        	else if(less(a[k], a[i]))
        		return k;
        	else
        		return i;
        }
    }
	
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void sort(Comparable[] a, int low, int high) { 
        int n = high - low + 1;

        if (n <= INSERTION_SORT_CUTOFF) {
            insertionSort(a, low, high);
            return;
        }
        else  { //John Tukey's median of medians
            int eps = n/8;
            int mid = low + n/2;
            int m1 = median3(a, low, low + eps, low + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, high - eps - eps, high - eps, high); 
            int ninther = median3(a, m1, m2, m3);
            exchange(a, ninther, low);
        }
        //randomPivot(a,low,high);
        int i = low, j = high+1;
        int lt = low, gt = high+1;
        Comparable v = a[low];
        while (true) {
            while (less(a[++i], v)) //para quando o a[i] > pivot
                if (i == high)
                	break;
            while (less(v, a[--j])) //para quando o pivot > a[j]
                if (j == low)
                	break;

            if (i >= j)
            	break;

            exchange(a, i, j);
            if (equal(a[i], v))
            	exchange(a, ++lt, i);
            if (equal(a[j], v))
            	exchange(a, --gt, j);
        }

        for (int k = low; k <= lt; k++)
        	exchange(a, k, j--);
        for (int k = high; k >= gt; k--)
        	exchange(a, k, i++);

        sort(a, low, j);
        sort(a, i, high);
        assert isSorted(a);
    }
	
    @SuppressWarnings("rawtypes")
	public static Comparable[] generateOrderedExample(int n)
    {
	    Comparable [] examples = new Comparable[n];
	    for(int i = 0; i < n; i++)
	    {
	    	examples[i] = i;
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
	public static Comparable[] generateInvertedExample(int n)
    {
	    Comparable [] examples = new Comparable[n];
	    for(int i = 0; i < n; i++)
	    {
	    	examples[i] = n-i;
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
	public static Comparable[] generatePartialOrderedExample(int n)
    {
    	Random r = new Random();
	    Comparable [] examples = new Comparable[n];
	    examples = generateOrderedExample(n);
	    for(int i = 0; i < n/8; i++)
	    {
	    	examples[i] = r.nextInt(100000);
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
    public static Comparable[] generateRandomExample(int n)
    {
	    Random r = new Random();
	    Comparable[] examples = new Comparable[n];
	    for(int i = 0; i < n; i++)
	    {
	    	examples[i] = r.nextInt(100000);
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
    public static Comparable[] generateRandomExampleWithLowNumberOfDuplicates(int n)
    {
	    Random r = new Random();
	    Comparable[] examples = new Comparable[n];
	    for(int i = 0; i < n; i++)
	    {
	    	examples[i] = r.nextInt(100000);
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
    public static Comparable[] generateRandomExampleWithHighNumberOfDuplicates(int n)
    {
	    Random r = new Random();
	    Comparable[] examples = new Comparable[n];
	    for(int i = 0; i < n; i++)
	    {
	    	examples[i] = r.nextInt(100);
	    }
	    return examples;
    }
    
    @SuppressWarnings("rawtypes")
	public static double calculateAverageExecutionTimeSort(int n)
    {
	    int trials = 30;
	    double totalTime = 0;
	    
	    for(int i = 0; i < trials; i++)
	    {
	    	//Comparable[] example = generateOrderedExample(n);
	    	//Comparable[] example = generateInvertedExample(n);
	    	//Comparable[] example = generatePartialOrderedExample(n);
	    	//Comparable[] example = generateRandomExample(n);
	    	//Comparable[] example = generateRandomExampleWithLowNumberOfDuplicates(n);
	    	Comparable[] example = generateRandomExampleWithHighNumberOfDuplicates(n);
	    	long time = System.currentTimeMillis();
	    	sort(example);
	    	totalTime += System.currentTimeMillis() - time;
	    }
	    return totalTime/trials;
    }
    
    //razao dobrada
	@SuppressWarnings("rawtypes")
	public static double calculateAverageExecutionTime(int n) {
		int trials = 30;
		double totalTime = 0;
		for (int i = 0; i < trials; i++) {
			//Comparable[] example = generateOrderedExample(n);
	    	//Comparable[] example = generateInvertedExample(n);
	    	//Comparable[] example = generatePartialOrderedExample(n);
	    	//Comparable[] example = generateRandomExample(n);
	    	//Comparable[] example = generateRandomExampleWithLowNumberOfDuplicates(n);
	    	Comparable[] example = generateRandomExampleWithHighNumberOfDuplicates(n);
			long time = System.currentTimeMillis();
			sort(example);
			totalTime += System.currentTimeMillis() - time;
		}
		return totalTime / trials;
	}
	
    public static void main(String[] args) {
    	System.out.println("Teste ao funcionamento do algoritmo:");
		Integer[] example = new Integer[] { 32, 43, 33, 26, 13, 40, 33, 14, 20, 24, 34, 40, 35, 13, 4 };
		System.out.println(Arrays.toString(example));
		System.out.println(isSorted(example));
		sort(example);
		System.out.println(Arrays.toString(example));
		System.out.println(isSorted(example));
		System.out.println();
		
		System.out.println("Testes de eficiencia do QuickSort3Way:");
		double resultx = 0;
    	for(int i = 1; i < 51; i++) {
    		double x = calculateAverageExecutionTimeSort(50000);
    		resultx += x;
    	}
		double testx = resultx/50;
		System.out.println(testx);
		System.out.println();
		
		System.out.println("Testes da razao dobrada:");
		int n = 125;
		double previousTime = calculateAverageExecutionTime(n);
		double newTime;
		double doublingRatio;
		for (int i = 250; true; i *= 2) {
			newTime = calculateAverageExecutionTime(i);
			if (previousTime > 0) {
				doublingRatio = newTime / previousTime;
			} else
				doublingRatio = 0;
			previousTime = newTime;
			System.out.println(i + "\t" + newTime + "\t" + doublingRatio);
		}
	}
}
/*
Foram sempre feitos 50 testes para cada tipo de array de tamanho 50000.

array ordenado: 1.992666666666667
array invertido: 4.346666666666668
array parcialmente ordenado: 7.594666666666668
array random: 10.971333333333332
array random com poucos repetidos: 11.478666666666664
array random com muitos repetidos: 4.382666666666666

Pelos testes podemos perceber que o caso em que o QuickSort3Way e mais eficente quando o array tem muitos elementos iguais, e o mais ineficiente e quando o array se encontra ordenado de maneira random.

Testes da razao dobrada para um array ordenado:
250	0.0	0.0
500	0.0	0.0
1000	0.0	0.0
2000	0.06666666666666667	0.0
4000	0.13333333333333333	2.0
8000	0.26666666666666666	2.0
16000	0.5333333333333333	2.0
32000	0.9666666666666667	1.8125
64000	2.2666666666666666	2.3448275862068964
128000	4.633333333333334	2.044117647058824
256000	11.766666666666667	2.5395683453237408

Testes da razao dobrada para um array invertido:
250	0.03333333333333333	0.0
500	0.03333333333333333	1.0
1000	0.06666666666666667	2.0
2000	0.16666666666666666	2.5
4000	0.3333333333333333	2.0
8000	0.6666666666666666	2.0
16000	1.2666666666666666	1.9
32000	2.7333333333333334	2.1578947368421053
64000	5.666666666666667	2.073170731707317
128000	11.166666666666666	1.9705882352941175
256000	30.3	2.7134328358208957

Testes da razao dobrada para um array parcialmente ordenado:
250	0.03333333333333333	0.0
500	0.06666666666666667	2.0
1000	0.06666666666666667	1.0
2000	0.16666666666666666	2.5
4000	0.36666666666666664	2.2
8000	0.8666666666666667	2.3636363636363638
16000	2.1	2.423076923076923
32000	4.5	2.142857142857143
64000	9.133333333333333	2.0296296296296297
128000	18.166666666666668	1.9890510948905111
256000	34.333333333333336	1.889908256880734

Testes da razao dobrada para um array random:
250	0.0	0.0
500	0.0	0.0
1000	0.1	0.0
2000	0.26666666666666666	2.6666666666666665
4000	0.5666666666666667	2.125
8000	1.3	2.294117647058824
16000	2.566666666666667	1.9743589743589745
32000	6.666666666666667	2.5974025974025974
64000	14.6	2.19
128000	30.933333333333334	2.1187214611872145
256000	71.43333333333334	2.3092672413793105

Testes da razao dobrada para um array random com poucos repetidos:
250	0.03333333333333333	0.0
500	0.03333333333333333	1.0
1000	0.1	3.0
2000	0.23333333333333334	2.333333333333333
4000	0.6	2.571428571428571
8000	1.2666666666666666	2.111111111111111
16000	2.8666666666666667	2.263157894736842
32000	6.266666666666667	2.186046511627907
64000	15.066666666666666	2.404255319148936
128000	32.53333333333333	2.15929203539823
256000	73.13333333333334	2.2479508196721314

Testes da razao dobrada para um array random com muitos repetidos:
250	0.06666666666666667	0.6666666666666666
500	0.16666666666666666	2.5
1000	0.23333333333333334	1.4000000000000001
2000	0.6333333333333333	2.714285714285714
4000	1.4666666666666666	2.3157894736842106
8000	2.966666666666667	2.022727272727273
16000	1.5666666666666667	0.5280898876404494
32000	2.6333333333333333	1.6808510638297873
64000	5.6	2.1265822784810124
128000	11.133333333333333	1.9880952380952381
256000	28.733333333333334	2.5808383233532934

r = 2(elevado)1 -> ordem de crescimento assimptotica aproximadamente O(n).
*/
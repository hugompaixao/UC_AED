package Sorting;

import Collections.UnrolledLinkedList;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

public class MinPriorityQueue<T extends Comparable<T>> {

	UnrolledLinkedList<T> queueData;

	public MinPriorityQueue() {
		queueData = new UnrolledLinkedList<>(550); 
	}

	public void insert(T element) {
		int low = 0;
		int high = size() - 1;

		while (high - low >= 0) {
			//step 2
			int midIndex = (high + low) / 2;

			//step 3
			int compareRes = element.compareTo(queueData.get(midIndex));

			//step 4
			if (compareRes < 0) {
				high = midIndex - 1;
			}

			//step 5
			else if (compareRes > 0) {
				low = midIndex + 1;
			}

			//Step 6
			else {
				low = midIndex;
				break;
			}
		}
		queueData.addAt(low, element);
	}

	public T removeMin() {
		return queueData.remove(0);
	}

	public T peekMin() {
		return queueData.get(0);
	}

	public boolean isEmpty() {
		return queueData.isEmpty();
	}

	public int size() {
		return queueData.size();
	}

	public MinPriorityQueue<T> shallowCopy() {
		MinPriorityQueue<T> copyObject = new MinPriorityQueue<>();
		copyObject.queueData = (UnrolledLinkedList<T>)this.queueData.shallowCopy();
		return copyObject;
	}

	@SuppressWarnings("unchecked")
	public T[] getElements() {
		if (size() == 0) {
			return null;
		}
		T[] result = (T[]) Array.newInstance(this.peekMin().getClass(),size()) ;
		//T[] result = (T[]) new Comparable[size()];
		Iterator<T> it = this.queueData.iterator();
		for (int i = 0; i < size(); i++) {
			result[i] = it.next();
		}
		return result;
	}
	
	public static int[] generateExample(int n) {
		Random r = new Random();
		int[] examples = new int[n];
		for (int i = 0; i < n; i++) {
			examples[i] = r.nextInt();
		}
		return examples;
	}
	
	public static double calculateAverageExecutionTime(int n) {
		int trials = 30;
		double totalTime = 0;
		for (int i = 0; i < trials; i++) {
			int[] example = generateExample(n);
			long time = System.currentTimeMillis();
			MinPriorityQueue<Integer> list = new MinPriorityQueue<>();
			for(int j = 0; j < example.length; j++) {
				list.insert(j);
			}
			totalTime += System.currentTimeMillis() - time;
		}
		return totalTime / trials;
	}
	
	public static double calculateAverageExecutionTimeRemove(int n) {
		int trials = 30;
		double totalTime = 0;
		for (int i = 0; i < trials; i++) {
			int[] example = generateExample(n);
			long time = System.currentTimeMillis();
			MinPriorityQueue<Integer> list = new MinPriorityQueue<>();
			for(int j = 0; j < example.length; j++) {
				list.removeMin();
			}
			totalTime += System.currentTimeMillis() - time;
		}
		return totalTime / trials;
	}
	
    public static void main(String[] args)
    {
    	int n = 125;
    	//double previousTime = calculateAverageExecutionTime(n);
    	double previousTime = calculateAverageExecutionTimeRemove(n);
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
Insert com blockSize 550:

250	0.06666666666666667	0.5
500	0.06666666666666667	1.0
1000	0.3	4.5
2000	0.4666666666666667	1.5555555555555556
4000	0.6	1.2857142857142856
8000	1.7666666666666666	2.9444444444444446
16000	9.1	5.150943396226415
32000	73.66666666666667	8.095238095238097
64000	405.8	5.508597285067873

RemoveMin com blockSize 550:

250	0.1	1.5
500	0.13333333333333333	1.3333333333333333
1000	0.26666666666666666	2.0
2000	0.43333333333333335	1.625
4000	0.5	1.1538461538461537
8000	1.7666666666666666	3.533333333333333
16000	9.2	5.2075471698113205
32000	73.1	7.945652173913044
64000	386.8666666666667	5.2922936616507075

Como os ensaios da razao dobrada estabilizaram em aproximadamente 4 entao a ordem de grandeza do insert e 2(elevado)2, logo O(n(elevado)2)
Como os ensaios da razao dobrada estabilizaram em aproximadamente 4 entao a ordem de grandeza do removeMin e 2(elevado)2, logo O(n(elevado)2)
*/
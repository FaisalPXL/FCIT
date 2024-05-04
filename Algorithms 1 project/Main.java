/*
 	CPCS 223 Group Project
	Faisal Hussain 2136580
	Faisal Othman 2137119
	Yousef Bakhsh 2139911
	AbdulAziz Al-Malki 2135085
*/

import java.util.Scanner;
import java.util.Date;
public class Main {

	public static void main(String [] args) {
		
		// Take input size from user
		System.out.println("Enter number of Elements:");
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		
		// Generate an array of size n with 
		// random integers
		int[] array = new int[n];
		for(int i = 0; i < n; i++) 
			array[i] = (int) (Math.random() * 9999);
		
		// Make copies of the same array
		// for the 3 sorting methods
		int[] insertion = new int[n];
		int[] selection = new int[n];
		int[] quick = new int[n];
		
		// Copy elements of the array 
		// into the new arrays
		for(int i  = 0; i < n; i++) {
			insertion[i] = array[i];
			selection[i] = array[i];
			quick[i] = array[i];
		}
		
		// Sort array using insertion
		// sort
		insertionSort(insertion);
		
		// Sort array using insertion
		// sort
		selectionSort(selection);

		// Sort array using insertion
		// sort
		quickSort1(quick);
		
		
		
		
	}
	public static void insertionSort(int[] array) {
		// Start measuring execution time
		long startTime = System.nanoTime();
		
		
		// Print content of array
		// before sorting
		System.out.println("Array elements before sorting (Insertion Sort)");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		
		for (int i = 1; i < array.length; i++) {
	        int current = array[i];
	        int j = i-1;
	        while ((j > -1) && (array[j] > current)) {
	            array[j+1] = array[j];
	            j--;
	        }
	        array[j+1] = current;
	    }
		
		// Print content of array
		// after sorting
		System.out.println("Array elements after sorting");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		
		
		// End measuring execution time
		long stopTime = System.nanoTime();
		long executionTime = stopTime - startTime;
		System.out.println("\nExecution Time (Insertion Sort): " + executionTime + " ns");
	}
	public static void selectionSort(int[] array) {
		// Start measuring execution time
		long startTime = System.nanoTime();
				
		
		// Print content of array
		// before sorting
		System.out.println("\nArray elements before sorting (Selection Sort)");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		
		// traverse unsorted array
		for (int i = 0; i < array.length-1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i+1; j < array.length; j++)
				if (array[j] < array[min_idx])
					min_idx = j;
			// swap minimum element with compared element
			int temp = array[min_idx];
			array[min_idx] = array[i];
			array[i] = temp;

		}
		
		// Print content of array
		// after sorting
		System.out.println("Array elements after sorting");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		
		
		// End measuring execution time
		long stopTime = System.nanoTime();
		long executionTime = stopTime - startTime;
		System.out.println("\nExecution Time (Selection Sort): " + executionTime + " ns");
	}
	
	
	public static void quickSort1(int[] array) {
		// Start measuring execution time
		long startTime = System.nanoTime();
						
				
		// Print content of array
		// before sorting
		System.out.println("\nArray elements before sorting (Quick Sort)");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		
		// call quick sort method
		quickSort(array, 0, array.length - 1);
		
		// Print content of array
		// after sorting
		System.out.println("Array elements after sorting");
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
				
				
		// End measuring execution time
		long stopTime = System.nanoTime();
		long executionTime = stopTime - startTime;
		System.out.println("\nExecution Time (Quick Sort): " + executionTime + " ns");
	}
	
	
	public static void quickSort(int[] array, int begin, int end) {
		if (begin < end) {
			int partitionIndex = partition(array, begin, end);

			quickSort(array, begin, partitionIndex-1);
			quickSort(array, partitionIndex+1, end);
			}
	}
	public static int partition(int array[], int begin, int end) {
		int pivot = array[end];
		int i = (begin-1);

		for (int j = begin; j < end; j++) {
			if (array[j] <= pivot) {
				i++;
				int Temp = array[i];
				array[i] = array[j];
				array[j] = Temp;
		}
		}

		int Temp = array[i+1];
		array[i+1] = array[end];
		array[end] = Temp;

		return i+1;
		}
}

import java.util.Scanner;

public class Task1_SwapArrayElements {
    
    /**
     * Swaps the first and last elements of an array
     */
    public static void swapFirstAndLast(int[] array) {
        if (array.length > 1) {
            int temp = array[0];
            array[0] = array[array.length - 1];
            array[array.length - 1] = temp;
        }
    }
    
    /**
     * Prints the array elements
     */
    public static void printArray(int[] array) {
        System.out.print("Array: ");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Enter " + size + " integers:");
        for (int i = 0; i < size; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }
        System.out.println("\nBefore swapping:");
        printArray(array);
        swapFirstAndLast(array);
        System.out.println("After swapping:");
        printArray(array);
        
        scanner.close();
    }
}

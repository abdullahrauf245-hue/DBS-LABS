import java.util.Scanner;
import java.util.Arrays;
public class Task3_ArrayOperations { 
     //Initializes an array of size N with random values from user input
    public static void populateArray(int[] array, int N, Scanner scanner) {
        System.out.println("Enter " + N + " integers for the array:");
        for (int i = 0; i < N; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }}
    public static void printArray(int[] array, int N) {
        System.out.print("Array: ");
        for (int i = 0; i < N; i++) {
            System.out.print(array[i] + " ");}
        System.out.println();}
     // Sorts the elements of the array in ascending order
    public static void sortArray(int[] array, int N) {
        Arrays.sort(array, 0, N);
    }
    // Finds if an element exists in the array
    public static boolean findElement(int[] array, int x) {
        for (int num : array) {
            if (num == x) {
                return true;}}
        return false;}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = 10;
        int[] array = new int[N];
        // Populate the array
        System.out.println("=== Array Operations ===\n");
        populateArray(array, N, scanner);
        // Print original array
        System.out.println("\nOriginal Array:");
        printArray(array, N);
        // Sort the array
        sortArray(array, N);
        System.out.println("\nSorted Array:");
        printArray(array, N);
        // Find an element
        System.out.print("\nEnter an element to search: ");
        int searchElement = scanner.nextInt();
        if (findElement(array, searchElement)) {
            System.out.println("Element " + searchElement + " found in the array.");
        } else {
            System.out.println("Element " + searchElement + " not found in the array.");
        }
        scanner.close();
    }
}

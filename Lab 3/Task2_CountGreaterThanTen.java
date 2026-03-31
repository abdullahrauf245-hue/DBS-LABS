import java.util.Scanner;

public class Task2_CountGreaterThanTen {
    /**
     * Counts how many integers in the array are greater than or equal to 10
     */
    public static int countGreaterOrEqual(int[] array) {
        int count = 0;
        for (int num : array) {
            if (num >= 10) {
                count++;
            }
        }
        return count;
    }
     //Prints the array elements
    
    public static void printArray(int[] array) {
        System.out.print("Array: ");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[10];
        
        System.out.println("Enter 10 integers:");
        for (int i = 0; i < 10; i++) {
            System.out.print("Integer " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }
        printArray(array);
        int count = countGreaterOrEqual(array);
        System.out.println("\nNumber of integers >= 10: " + count); 
        scanner.close();
    }
}

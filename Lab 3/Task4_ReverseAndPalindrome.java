import java.util.Scanner;
public class Task4_ReverseAndPalindrome {
// Returns a new array containing elements in reverse order
    public static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];}
        return reversed;}
// Returns true if the array is a palindrome, otherwise false 
    public static boolean isPalindrome(int[] array) {
        int start = 0;
        int end = array.length - 1;  
        while (start < end) {
            if (array[start] != array[end]) {
                return false;}
            start++;
            end--;}
        return true;}
    //Prints the elements of the array
    public static void printArray(int[] array) {
        System.out.print("Array: ");
        for (int num : array) {
            System.out.print(num + " ");}
        System.out.println();}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of integers (N): ");
        int N = scanner.nextInt();
        int[] array = new int[N];
        System.out.println("Enter " + N + " integers:");
        for (int i = 0; i < N; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            array[i] = scanner.nextInt();}
        System.out.println("\n=== Original Array ===");
        printArray(array);
        // Check if palindrome
        boolean palindrome = isPalindrome(array);
        System.out.println("Is the array a palindrome? " + palindrome);
        // Reverse the array
        int[] reversed = reverseArray(array);
        System.out.println("\n=== Reversed Array ===");
        printArray(reversed);
        System.out.println("Is the reversed array a palindrome? " + isPalindrome(reversed));
        scanner.close();}}

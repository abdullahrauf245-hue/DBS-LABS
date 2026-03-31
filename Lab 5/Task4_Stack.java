// Task#4: Stack Class
// File: Task4_Stack.java

import java.util.Scanner;

public class Task4_Stack {
    private int[] stck;
    private int tos;
    private final int MAX_SIZE; // Use a constant for better readability

    // Constructor with default size
    public Task4_Stack() {
        this(10); // Calls the other constructor with size 10
    }

    // Constructor with custom size
    public Task4_Stack(int size) {
        MAX_SIZE = size;
        stck = new int[MAX_SIZE];
        tos = -1;
    }

    // Push operation
    public void push(int value) {
        if (isFull()) {
            System.out.println("Stack Overflow! Cannot push " + value);
        } else {
            stck[++tos] = value;
            System.out.println("Pushed: " + value);
        }
    }

    // Pop operation
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow! Cannot pop.");
            return -1;
        } else {
            int value = stck[tos--];
            System.out.println("Popped: " + value);
            return value;
        }
    }

    // Peek operation - Standard stack feature to see top without removing
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Nothing to peek.");
            return -1;
        }
        return stck[tos];
    }

    public boolean isEmpty() {
        return tos < 0;
    }

    public boolean isFull() {
        return tos >= MAX_SIZE - 1;
    }

    public int size() {
        return tos + 1;
    }

    public void displayStack() {
        System.out.print("Stack (top to bottom): ");
        if (isEmpty()) {
            System.out.println("[Empty]");
        } else {
            for (int i = tos; i >= 0; i--) {
                System.out.print(stck[i] + (i > 0 ? " -> " : ""));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Initialize stack with size 5 for testing
        Task4_Stack myStack = new Task4_Stack(5);
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- Stack Demonstration ---");
        
        // Push some values
        myStack.push(10);
        myStack.push(20);
        myStack.push(30);
        
        myStack.displayStack();
        System.out.println("Current Top: " + myStack.peek());
        
        // Perform a pop
        myStack.pop();
        
        myStack.displayStack();
        System.out.println("Final Size: " + myStack.size());
        
        // Testing Overflow
        System.out.println("\nFilling stack to test overflow:");
        myStack.push(40);
        myStack.push(50);
        myStack.push(60);
        myStack.push(70); // This should trigger overflow
        
        sc.close();
    }
}
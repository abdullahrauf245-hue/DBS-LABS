// Task#4: Stack Test Application
// File: Task4_StackTest.java

public class Task4_StackTest {
    public static void main(String[] args) {
        // Create a Stack object
        Task4_Stack stack = new Task4_Stack();
        
        System.out.println("=== Stack Test Application ===\n");
        
        // Test isEmpty on new stack
        System.out.println("1. Testing new stack:");
        stack.displayStatus();
        System.out.println();
        
        // Test pushing elements onto the stack
        System.out.println("2. Pushing elements onto the stack:");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        System.out.println();
        
        // Display stack contents
        System.out.println("3. Stack contents after pushing 5 elements:");
        stack.displayStack();
        System.out.println();
        
        // Display stack status
        System.out.println("4. Stack status:");
        stack.displayStatus();
        System.out.println();
        
        // Test popping elements
        System.out.println("5. Popping elements from the stack:");
        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());
        System.out.println();
        
        // Display stack contents after popping
        System.out.println("6. Stack contents after popping 2 elements:");
        stack.displayStack();
        System.out.println();
        
        // Display stack status
        System.out.println("7. Stack status after popping:");
        stack.displayStatus();
        System.out.println();
        
        // Test pushing more elements (to fill the stack)
        System.out.println("8. Pushing more elements:");
        stack.push(60);
        stack.push(70);
        stack.push(80);
        stack.push(90);
        stack.push(100);
        stack.push(110); // This should fail - stack is full
        System.out.println();
        
        // Display final stack contents
        System.out.println("9. Final stack contents:");
        stack.displayStack();
        System.out.println();
        
        // Display final stack status
        System.out.println("10. Final stack status:");
        stack.displayStatus();
        System.out.println();
        
        // Pop all remaining elements
        System.out.println("11. Popping all remaining elements:");
        while (!stack.isEmpty()) {
            System.out.println("Popped: " + stack.pop());
        }
        System.out.println();
        
        // Display stack status after emptying
        System.out.println("12. Stack status after emptying:");
        stack.displayStatus();
        System.out.println();
        
        // Test popping from empty stack
        System.out.println("13. Attempting to pop from empty stack:");
        stack.pop();
    }
}

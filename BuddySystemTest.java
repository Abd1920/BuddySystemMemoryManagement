import java.util.Scanner;

public class BuddySystemTest {
    public static void main(String[] args) {
        // Initialize the Buddy System with 1024 KB memory
        BuddySystem buddySystem = new BuddySystem(1024);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMemory Management System");
            System.out.println("1. Allocate Memory");
            System.out.println("2. Free Memory");
            System.out.println("3. Display Memory State");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Allocate Memory
                    System.out.print("Enter the size of memory to allocate (in KB): ");
                    int size = scanner.nextInt();
                    BuddyNode allocatedBlock = buddySystem.allocate(size);
                    if (allocatedBlock != null) {
                        System.out.println("Memory block of size " + size + " KB allocated successfully.");
                    } else {
                        System.out.println("Failed to allocate memory of size " + size + " KB. Not enough space.");
                    }
                    break;

                case 2: // Free Memory
                    System.out.print("Enter the size of memory block to free (in KB): ");
                    int requestedBlockSize = scanner.nextInt();
                    int actualBlockSize = nearestPowerOfTwo(requestedBlockSize); // Convert to actual block size
                    BuddyNode blockToFree = findBlockBySize(buddySystem, actualBlockSize); // Search by actual size
                    if (blockToFree != null && !blockToFree.isFree) {
                        buddySystem.free(blockToFree);
                        System.out.println("Memory block of size " + requestedBlockSize + " KB freed successfully.");
                    } else {
                        System.out.println("No allocated memory block of size " + requestedBlockSize + " KB found.");
                    }
                    break;

                case 3: // Display Memory State
                    buddySystem.printMemory();
                    break;

                case 4: // Exit
                    System.out.println("Exiting the Memory Management System.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Helper method to find a block by size
    private static BuddyNode findBlockBySize(BuddySystem buddySystem, int size) {
        return findBlockHelper(buddySystem.getRoot(), size);
    }

    private static BuddyNode findBlockHelper(BuddyNode node, int size) {
        if (node == null) return null;

        // Match the actual block size (power of 2) and check if it's allocated
        if (!node.isFree && node.size == size) {
            return node;
        }

        BuddyNode leftResult = findBlockHelper(node.left, size);
        if (leftResult != null) return leftResult;

        return findBlockHelper(node.right, size);
    }

    // Helper method to calculate the nearest power of 2
    private static int nearestPowerOfTwo(int num) {
        int power = 1;
        while (power < num) {
            power *= 2;
        }
        return power;
    }
}

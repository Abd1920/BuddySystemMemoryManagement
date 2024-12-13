class BuddySystem {
    private BuddyNode root; // Root of the binary tree
    private int totalSize;  // Total memory size

    // Constructor
    public BuddySystem(int totalSize) {
        this.totalSize = totalSize;
        this.root = new BuddyNode(totalSize); // Start with one large block
    }

    // Getter for root
    public BuddyNode getRoot() {
        return root;
    }

    // Allocate memory
    public BuddyNode allocate(int requestSize) {
        int size = nearestPowerOfTwo(requestSize);
        return allocateHelper(root, size);
    }

    private BuddyNode allocateHelper(BuddyNode node, int size) {
        if (node == null || !node.isFree || node.size < size) {
            return null; // Cannot allocate
        }

        if (node.size == size) {
            node.isFree = false; // Mark as allocated
            node.requestedSize = size; // Store the requested size
            return node;
        }

        // Split the node into two buddies
        if (node.left == null && node.right == null) {
            node.left = new BuddyNode(node.size / 2);
            node.right = new BuddyNode(node.size / 2);
        }

        // Try to allocate in the left or right child
        BuddyNode allocatedNode = allocateHelper(node.left, size);
        if (allocatedNode == null) {
            allocatedNode = allocateHelper(node.right, size);
        }
        return allocatedNode;
    }

    // Free memory
    public void free(BuddyNode node) {
        if (node == null) return;

        node.isFree = true;

        // Check and merge buddies
        merge(root);
    }

    private boolean merge(BuddyNode node) {
        if (node == null || node.left == null || node.right == null) {
            return false; // Cannot merge
        }

        // If both children are free and of the same size
        if (node.left.isFree && node.right.isFree && node.left.size == node.right.size) {
            node.left = null;
            node.right = null;
            node.isFree = true; // Merge into a single block
            return true;
        }

        // Continue merging in subtrees
        boolean mergedLeft = merge(node.left);
        boolean mergedRight = merge(node.right);
        return mergedLeft || mergedRight;
    }

    // Helper: Find the nearest power of 2
    private int nearestPowerOfTwo(int num) {
        int power = 1;
        while (power < num) {
            power *= 2;
        }
        return power;
    }

    // Display the memory tree
    public void printMemory() {
        printMemoryHelper(root, "");
    }

    private void printMemoryHelper(BuddyNode node, String indent) {
        if (node == null) return;

        System.out.println(indent + "Block Size: " + node.size + ", Free: " + node.isFree);
        printMemoryHelper(node.left, indent + "  ");
        printMemoryHelper(node.right, indent + "  ");
    }
}

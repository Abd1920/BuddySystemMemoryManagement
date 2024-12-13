class BuddyNode {
    int size;              // Actual block size (power of 2)
    int requestedSize;     // User-requested size
    boolean isFree;        // Whether the block is free or allocated
    BuddyNode left;        // Left child (smaller block)
    BuddyNode right;       // Right child (smaller block)

    // Constructor
    public BuddyNode(int size) {
        this.size = size;
        this.requestedSize = 0; // Default to 0
        this.isFree = true;
        this.left = null;
        this.right = null;
    }
}

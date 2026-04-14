public class HuffmanTree {
    HuffmanNode root;

    public HuffmanTree(HuffmanNode huff) {
        this.root = huff;
    }

    public void printLegend() {
        printLegend(root, "");
    }

    private void printLegend(HuffmanNode t, String s) {
        if (t.letter.length() > 1) {
            printLegend(t.left, s + "0");
            printLegend(t.right, s + "1");
        }
        if (t.letter.length() == 1) {
            System.out.println(t.letter + "=" + s);
        }
    }

    public static BinaryHeap legendToHeap(String legend) {
        String[] parts = legend.split(" ");
        BinaryHeap heap = new BinaryHeap();

        for (int i = 0; i < parts.length; i += 2) {
            String letter = parts[i];
            Double frequency = Double.parseDouble(parts[i + 1]);
            HuffmanNode node = new HuffmanNode(letter, frequency);
            heap.insert(node);
        }
        return heap;
    }

    public static HuffmanTree createFromHeap(BinaryHeap b) {
        while (b.getSize() > 1) {
            HuffmanNode left = (HuffmanNode) b.deleteMin();
            HuffmanNode right = (HuffmanNode) b.deleteMin();
            HuffmanNode parent = new HuffmanNode(left, right);
            b.insert(parent);
        }
        return new HuffmanTree((HuffmanNode) b.deleteMin());
    }

    public HuffmanNode getRoot() {
        return root;
    }
}
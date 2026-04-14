import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Arrays;
import java.util.Scanner;// Import the Scanner class to read text files

//constructor
public class HuffmanConverter {

    // ASCII number of characters
    public static final int NUMBER_OF_CHARACTERS = 256;

    private String contents;
    private HuffmanTree huffmanTree;
    private int count[];
    private String code[];

    // Construct using an input string.
    // Initialize count and code.
    public HuffmanConverter(String input) {
        this.contents = input;
        this.count = new int[NUMBER_OF_CHARACTERS];
        this.code = new String[NUMBER_OF_CHARACTERS];
    }

    // Record how often each character occurs and store in count.
    public void recordFrequencies() {
        Arrays.fill(count, 0);
        for (char c : contents.toCharArray()) {
            count[(int) c]++;
        }

        for (int i = 0; i < 256; i++) {
            if (count[i] > 0) {
                char character = (char) i;
                String displayChar;
                
                if (character == '\n') {
                    displayChar = "\\n";
                } else if (character == '\t') {
                    displayChar = "\\t";
                } else if (character == '\r') {
                    displayChar = "\\r";
                } else if (character == ' ') {
                    displayChar = " ";
                } else {
                    displayChar = String.valueOf(character);
                }
                
                System.out.print("<" + displayChar + ", " + count[i] + "> ");
            }
        }
        System.out.println("\n");
    }

    // Construct a Huffman tree from count and
    // store the tree in huffmanTree.
    public void frequenciesToTree() {
        StringBuilder legend = new StringBuilder();
        
        for (int i = 0; i < 256; i++) {
            if (count[i] > 0) {
                char character = (char) i;
                String displayChar;
                
                if (character == '\n') {
                    displayChar = "NEWLINE"; 
                } else if (character == ' ') {
                    displayChar = "SPACE"; 
                } else if (character == '\t') {
                    displayChar = "TAB";  
                } else if (character == '\r') {
                    displayChar = "RETURN"; 
                } else {
                    displayChar = String.valueOf(character);
                }
                
                legend.append(displayChar).append(" ").append(count[i]).append(" ");
            }
        }
        
        if (legend.length() > 0) {
            legend.setLength(legend.length() - 1);
        }

        BinaryHeap heap = HuffmanTree.legendToHeap(legend.toString());
        huffmanTree = HuffmanTree.createFromHeap(heap);
    }


    // Construct code from huffmanTree.
   public void treeToCode() {
    Arrays.fill(code, "");
    treeToCode(huffmanTree.root, "");

    for (int i = 0; i < 256; i++) {
        if (count[i] > 0) {
            char character = (char) i;
            String displayChar;
            
            if (character == '\n') {
                displayChar = "\\n";
            } else if (character == ' ') {
                displayChar = " ";
            } else if (character == '\t') {
                displayChar = "\\t";
            } else if (character == '\r') {
                displayChar = "\\r";
            } else {
                displayChar = String.valueOf(character);
            }
            
            System.out.println("'" + displayChar + "'=" + code[i]);
        }
    }
    System.out.println();
}

    
   
    private void treeToCode(HuffmanNode t, String encoding) {
    if (t == null)
        return;
        
    if (t.left == null && t.right == null) {
        char actualChar;
        if (t.letter.equals("NEWLINE")) {
            actualChar = '\n';
        } else if (t.letter.equals("SPACE")) {
            actualChar = ' ';
        } else if (t.letter.equals("TAB")) {
            actualChar = '\t';
        } else if (t.letter.equals("RETURN")) {
            actualChar = '\r';
        } else if (t.letter.length() == 1) {
            actualChar = t.letter.charAt(0);
        } else {
            return;
        }
        
        code[(int) actualChar] = encoding;
        } else {
        treeToCode(t.left, encoding + "0");
        treeToCode(t.right, encoding + "1");
    }
}


    // Encode content using code.
    public String encodeMessage() {
        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < contents.length(); i++) {
            char c = contents.charAt(i);
            String charCode = code[(int) c];
            encoded.append(charCode);
        }

        System.out.println("Huffman Encoding:");
        return encoded.toString();
    }

    // Decode a Huffman encoding.
    public String decodeMessage(String encodedStr) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode current = huffmanTree.root;

        for (int i = 0; i < encodedStr.length(); i++) {
            char bit = encodedStr.charAt(i);
            
            // move to the next node
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }
            
            if (current.left == null && current.right == null) {
                char actualChar;
                if (current.letter.equals("NEWLINE")) {
                    actualChar = '\n';
                } else if (current.letter.equals("SPACE")) {
                    actualChar = ' ';
                } else if (current.letter.equals("TAB")) {
                    actualChar = '\t';
                } else if (current.letter.equals("RETURN")) {
                    actualChar = '\r';
                } else {
                    actualChar = current.letter.charAt(0);
                }
                decoded.append(actualChar);
                current = huffmanTree.root;
            }
        }
        
        return decoded.toString();
    }

    // Read an input file.
    public static String readContents(String filename) {
        String temp = "";
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                temp += sc.nextLine();
                temp += "\n";
            }
            sc.close();
            return temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String args[]) {
        String input = HuffmanConverter.readContents(args[0]);
        HuffmanConverter h = new HuffmanConverter(input);
        h.recordFrequencies();
        // Print a list of characters and frequencies here!
        h.frequenciesToTree();
        h.treeToCode();
        // Print the Huffman encoding here!
        String encoded = h.encodeMessage();
        System.out.println(encoded + "\n");
        System.out.println("Message size in ASCII encoding: " + h.contents.length() * 8);
        System.out.println("Message size in Huffman coding: " + encoded.length() + "\n");
        System.out.println(h.decodeMessage(encoded));
    }

}

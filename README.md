# Huffman Converter

A Java implementation of Huffman coding for lossless text compression. This program reads a text file, builds a Huffman tree based on character frequencies, encodes the content into a compact binary string, and decodes it back to verify correctness.

## Files

- **HuffmanConverter.java** — Main driver class. Handles file reading, frequency counting, encoding, and decoding.
- **HuffmanTree.java** — Builds and manages the Huffman tree structure.
- **HuffmanNode.java** — Represents a single node in the Huffman tree.
- **BinaryHeap.java** — Min-heap used to construct the Huffman tree efficiently (dependency, not shown).

## How It Works

1. **Frequency Analysis** — Scans the input text and counts how often each character appears.
2. **Tree Construction** — Inserts all characters into a min-heap, then repeatedly merges the two lowest-frequency nodes until one tree remains.
3. **Code Generation** — Traverses the tree to assign each character a unique binary code (shorter codes for more frequent characters).
4. **Encoding** — Replaces each character in the original text with its binary code.
5. **Decoding** — Walks the Huffman tree bit-by-bit to reconstruct the original message.

## Usage

### Compile

```bash
javac HuffmanConverter.java HuffmanTree.java HuffmanNode.java BinaryHeap.java
```

### Run

```bash
java HuffmanConverter <input_file>
```

Replace `<input_file>` with the path to any plain text file.

### Example

```bash
java HuffmanConverter sample.txt
```

## Output

The program prints the following to standard output:

- Character frequency table (e.g., `<a, 42> <e, 31> ...`)
- Huffman codes for each character (e.g., `'e'=01`, `'a'=110`)
- The full Huffman-encoded binary string
- Size comparison between ASCII and Huffman encoding (in bits)
- The decoded message (should match the original input)

## Requirements

- Java 8 or later
- A `BinaryHeap` class implementing `insert()`, `deleteMin()`, and `getSize()` methods

## Notes

- Supports all 256 ASCII characters.
- Special characters (`\n`, `\t`, `\r`, space) are handled and displayed clearly in output.
- Compression savings will vary based on input — files with skewed character distributions benefit most.

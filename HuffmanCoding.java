package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Nathan Roh
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

        ArrayList<CharFreq> charFreq = new ArrayList<CharFreq>();
        int[] charCount = new int[128];
        int counter = 0;
        while ( StdIn.hasNextChar() == true ){   
            char character = StdIn.readChar();
            charCount[character]++;
            counter++;
        }

        for (int i = 0; i < charCount.length; i++){
            if ( charCount[i] != 0 ){
                charFreq.add(new CharFreq((char) i, (double) charCount[i] / (double) counter));
            }
        }
        
        if ( charFreq.size() == 1 ){   
            if ( charCount[127] ==  0 ){
                char placeHolderChar = charFreq.get(0).getCharacter();
                placeHolderChar++;
                charFreq.add(new CharFreq(placeHolderChar, 0));
            }
            if ( charCount[127] != 0 ){
                charFreq.add(new CharFreq('a', 0));
            }
        }

        Collections.sort(charFreq);
        sortedCharFreqList = charFreq;
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();

        for ( CharFreq character : sortedCharFreqList )
            source.enqueue(new TreeNode(character, null, null));

        while ( !source.isEmpty() || target.size() != 1 ){
            TreeNode node1 = new TreeNode();
            TreeNode node2 = new TreeNode();
            if ( target.isEmpty() )
                node1 = source.dequeue();
            if ( target.isEmpty() && !source.isEmpty() )
                node2 = source.dequeue();
            
            if ( !source.isEmpty() && !target.isEmpty() && source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc() )
                node1 = source.dequeue();
            else if ( !target.isEmpty() ) node1 = target.dequeue();
            if ( !source.isEmpty() && !target.isEmpty() && source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc() )
                 node2 = source.dequeue();
            else {
                    if ( !target.isEmpty() ) node2 = target.dequeue();
                    else if ( target.isEmpty() && source.size() == 1) node2 = source.dequeue();
                }
            
            TreeNode sumProb = new TreeNode(new CharFreq(null, node1.getData().getProbOcc() + node2.getData().getProbOcc()) , node1, node2);
            target.enqueue(sumProb);
        }
        huffmanRoot = target.dequeue();
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
        String[] temporaryEncodings = new String[128];
        String encodeString = new String();
        traverse(huffmanRoot, encodeString, temporaryEncodings);
        encodings = temporaryEncodings;

    }

    private void traverse(TreeNode node, String bitString, String[] array) {

    if ( node.getData().getCharacter() != null ){   
        array[(int) node.getData().getCharacter()] = bitString;  
        return;
    }
    traverse(node.getLeft(), bitString + "0", array);
    traverse(node.getRight(), bitString + "1", array);

    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String bitText = new String();
        while ( StdIn.hasNextChar() ){
            char character = StdIn.readChar();
            if ( encodings[(int) character] != null ){
                bitText += encodings[(int) character];
            }
        }
        writeBitString(encodedFile, bitText);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String bitString = new String(readBitString(encodedFile));
        TreeNode treeNode = huffmanRoot;
        for ( int i = 0; i < bitString.length(); i++){
            if ( treeNode.getData().getCharacter() != null ){
                StdOut.print( treeNode.getData().getCharacter() );
                treeNode = huffmanRoot;
            }
            if ( bitString.charAt(i) == '0' ){
                treeNode = treeNode.getLeft(); 
            }
            if ( bitString.charAt(i) == '1' ){
                treeNode = treeNode.getRight();
            }
            if ( i == bitString.length() - 1){
                StdOut.print(treeNode.getData().getCharacter());
            }
        }
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}

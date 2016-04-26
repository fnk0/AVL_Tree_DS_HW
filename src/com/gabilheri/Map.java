package com.gabilheri;

import java.io.*;
import java.util.*;

public class Map {

    /**
     * Allowed commands
     */
    private static final String EXIT = "exit";
    private static final String HEIGHT = "height";
    private static final String FIND = "find";
    private static final String OCCURS = "occurs";
    private static final String COUNT = "count";
    private static final String REPLACE = "replace";

    // The tree holding the data from the file
    private static AvlTreeMap<String, NodeData<String>> tree;

    // The OutputStream with the handle to the log file
    private static PrintWriter out = null;

    public static void main(String[] args) {

        // If length of arguments is less than 2 we exit the program with a Usage message
        if (args.length < 2) {
            exit("Usage: java Map <filename> <output>");
        }

        String inputFile = args[0];
        String outFile = args[1];

        String fileText = readFile(inputFile);
        if (fileText == null) {
            // If file is not found then the program exits with a error message
            exit("Error: Could not find file input file with name: " + inputFile);
        } else {
            System.out.println("Welcome to AVL Tree Map program by Marcus Gabilheri");
            System.out.println("Input file: " + inputFile + " -- Output file: " + outFile);
            System.out.println("Usage: <command> <args (separated by space)>");
            System.out.println("Available commands: find, occurs, count, replace");
            System.out.println();

            out = openOutputStream(outFile); // Opens the outputStream

            // Replaces all the "_" in the file and converts everything to lowercase
            fileText = fileText.replaceAll("_", " ").toLowerCase();

            // Split the input into words using a Regex
            String[] words = fileText.split("\\W+");

            // Create a new empty tree
            tree = new AvlTreeMap<>();

            // Insert all the words in the file into the Tree
            // Since we are reading the file top to bottom the position of the word in the array
            // Is also the place where the word has its occurrence
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                tree.insert(word, new NodeData<>(word), i);
            }

            int uniqueCount = getUniqueWordsCount(words);
            System.out.println("Finished processing file with " + uniqueCount + " words.");
            System.out.println("The log base 2 of 314 is: " + log2(uniqueCount));
            System.out.println("The height of the tree is: " + tree.getHeight());
            System.out.println();

//            tree.printTree();

            // Open a scanner to the keyboard input and wait for a commend
            Scanner scanner = new Scanner(System.in);

            // Keep executing commands until the exit command is typed in
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                executeCommand(input);
            }
        }
    }

    /**
     * Opens an Output Stream that will write to a file
     *
     * @param fileName The name of the file. If the file does not exist the file will be creates otherwise it will be overriden
     * @return An open OutputStream ready to write to a file.
     */
    public static PrintWriter openOutputStream(String fileName) {
        try {
            File file = new File(fileName);
            PrintWriter writer = new PrintWriter(file);

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            return writer;

        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Reads a file into a String
     *
     * @param filename The name of the file to be read
     * @return String containing contents of the file or null if file can not be found
     */
    public static String readFile(String filename) {
        try {
            InputStream in = new FileInputStream(new File(filename));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
            reader.close();

            return out.toString();
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Gets the number of unique words inserted into the tree
     *
     * @param words
     *      The words containing in the file
     * @return
     *      The number of unique words in the file
     */
    public static int getUniqueWordsCount(String[] words) {
        Set<String> uniqueWords = new HashSet<>();
        Collections.addAll(uniqueWords, words);
        return uniqueWords.size();
    }

    /**
     * Calculates the log with base 2
     *
     * @param n
     *      The number which we want to calculate the log
     * @return
     *      The log2 value of n
     */
    public static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    /**
     * Convenience method to print a message and exit the program
     *
     * @param message The message to be printed to the console
     */
    public static void exit(String message) {
        System.out.println(message);
        System.exit(0);
    }

    /**
     * Executes a command and outputs its results to the console and the log file.
     *
     * @param input The command to be executed
     */
    public static void executeCommand(String input) {

        StringBuilder output = new StringBuilder();
        String[] inputs = input.toLowerCase().split(" ");

        output.append(new Date()).append(" ~ ");

        if (inputs.length < 2) {
            if (inputs.length == 1) {
                if (inputs[0].equals(EXIT)) {
                    out.write("\n");
                    out.flush();
                    out.close();
                    System.out.println("Now exiting the program...");
                    System.exit(0);
                } else if(inputs[0].equals(HEIGHT)) {
                    output.append("height: The height of three is: ").append(tree.getHeight());
                } else {
                    output.append("Invalid command. Valid commands are: find, occurs, count, replace, height");
                }
            } else {
                output.append("Usage: <command> <args>");
            }
        } else {
            String command = inputs[0];
            String word = inputs[1];
            switch (command) {
                case FIND:
                    NodeComparisonWrapper<String, NodeData<String>> nodeWrapper = tree.find(word);
                    if (nodeWrapper.getNode() == null) {
                        output.append("find: Could not find '").append(word).append("'");
                    } else {
                        output.append("find: Found '").append(word).append("' with ").append(nodeWrapper.getComparison()).append(" comparisons");
                    }
                    break;
                case OCCURS:
                    List<Integer> occurrences = tree.occurs(word);
                    if (occurrences == null) {
                        output.append("occurs: Could not find occurrences for word: ").append(word);
                    } else {
                        output.append("occurs: Occurrences for word ").append(word).append(": ").append(occurrences);
                    }
                    break;
                case COUNT:
                    int count = tree.count(word);
                    if (count == -1) {
                        output.append("count: Could not find he word: ").append(word);
                    } else {
                        output.append("count: The word '").append(word).append("' appears ").append(count).append(" times.");
                    }

                    break;
                case REPLACE:
                    String word2 = inputs.length > 2 ? inputs[2] : null;
                    AvlNode<String, NodeData<String>> node = tree.replace(word, word2);

                    if (node == null) {
                        output.append("replace: could not find word '").append(word).append("'");
                    } else if (node.getKey() == null) {
                        output.append("replace: removed word '").append(word).append("'");
                    } else {
                        output.append("replace: replaced word '").append(word).append("' with '").append(word2).append("'");
                    }

                    break;
                default:
                    output.append("Invalid command. Valid commands are: find, occurs, count, replace, height");
                    break;
            }
        }

        output.append("\n");
        System.out.println(output.toString());
        out.write(output.toString());
        out.flush();
    }
}

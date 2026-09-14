// Simple Hex Dump utility.

import java.io.DataInputStream;
import java.io.FileInputStream;

class HexDump {

    public static void main(final String args[]) {
        String fileName = args[0];

        // try to open the file
        DataInputStream inputStream = null;
        try {
            inputStream = new DataInputStream(new FileInputStream(fileName));
        } catch (Exception e) {
            System.out.println("Error opening file: " + e);
            System.exit(1);
        }

        // initial read
        byte b = 0;
        try {
            b = (byte) inputStream.read();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
            System.exit(1);
        }

        // book-keeping variables
        int j = 0, k = 0;

        // buffer for human-readable text
        StringBuilder text = new StringBuilder();

        // process file
        while (b >= 0) {

            // write out the hex value right away
            System.out.print(HexDump.formatHex(b));

            // buffer the human-readable text
            text.append(HexDump.formatReadable(b));

            // process word
            j++;
            if (j >= 8) {
                System.out.print(" ");
                j = 0;
                k++;
            }

            // process line
            if (k >= 4) {
                System.out.println(" | " + text + " |");
                text = new StringBuilder();
                k = 0;
            }

            // read next byte
            try {
                b = (byte) inputStream.read();
            } catch (Exception e) {
                System.out.println("Error reading file: " + e);
                System.exit(1);
            }
        }

        // close the file
        try {
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Error closing file: " + e);
        }

        // print out the last line if it is not complete
        for (int i = 0; i < ((17 * (4 - k)) - (2 * j)); i++)
            System.out.print(" ");
        for (int i = 0; i < (32 - ((8 * k) + j)); i++)
            text.append(" ");
        System.out.println(" | " + text + " |");

    }

    // convert a byte to a human-readable character
    private static String formatReadable(byte b) {

        // convert control characters to a space
        if (Character.isWhitespace((char) b))
            return " ";

        // convert letters and digits to themselves
        if (Character.isLetterOrDigit((char) b))
            return "" + (char) b;

        // not a human-readable character
        return ".";
    }

    // convert a byte to a hex string
    private static final char lookUps[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };
    private static final int LOOKUP_MASK = 0x0F;
    private static final int SHIFT_NIBBLE = 4;

    private static String formatHex(byte b) {
        byte lowerNibble = (byte) (LOOKUP_MASK & b);
        byte upperNibble = (byte) ((b >>> SHIFT_NIBBLE) & LOOKUP_MASK);
        return "" + lookUps[upperNibble] + lookUps[lowerNibble];
    }

}
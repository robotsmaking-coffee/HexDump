// Simple Hex Dump utility.

import java.io.DataInputStream;
import java.io.FileInputStream;

class HexDump {
    private static final char lookUps[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

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
        byte lowerNibble, upperNibble;
        int j = 0, k = 0;

        // buffer for human-readable text
        StringBuilder text = new StringBuilder();

        // process file
        while (b >= 0) {

            // classify input as human-readable or not
            if (Character.isWhitespace((char) b))
                text.append(" ");
            else if (Character.isLetterOrDigit((char) b))
                text.append((char) b);
            else
                text.append(".");

            // split byte into two nibbles and convert to hex
            lowerNibble = (byte) (0x0F & b);
            upperNibble = (byte) ((b >>> 4) & 0x0F);
            System.out.print("" + lookUps[upperNibble] + lookUps[lowerNibble]);
           
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

}
// Simple Hex Dump utility.
//
// Example output:
// ./HexDump.sh Makefile
// 616C6C3A0A096A61 7661632048657844 756D702E6A617661 0A0A72756E3A0A09  | all.  javac HexDump.java  run.   |
// 2E2F48657844756D 702E736820524541 444D452E6D640A                     | ..HexDump.sh README.md           |

import java.io.DataInputStream;
import java.io.FileInputStream;

class HexDump {
    private static final char lookUps[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    public static void main(final String args[]) {
        String fileName = args[0];

        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName));
            byte lowerNibble, upperNibble, b;
            int j = 0;
            int k = 0;

            StringBuilder text = new StringBuilder();

            b = (byte) inputStream.read();
            while (b >= 0) {
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
                j++;

                // process word
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

                b = (byte) inputStream.read();
            }

            inputStream.close();

            // print out the last line if it is not complete
            for (int i = 0; i < ((17 * (4 - k)) - (2 * j)); i++)
                System.out.print(" ");
            for (int i = 0; i < (32 - ((8 * k) + j)); i++)
                text.append(" ");
            System.out.println(" | " + text + " |");

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}

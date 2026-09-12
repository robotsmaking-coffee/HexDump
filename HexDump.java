import java.io.*;

class HexDump
{
    public static void main (final String args[])
    {
        String sFile = args[0];
        int iIn = 0;
        try
        {
            DataInputStream brIn = new DataInputStream(new FileInputStream(sFile));
            byte bLow = 0;
            byte bHigh = 0;
            byte b = 0;
            int iBytes = brIn.available();
            int j = 0;
            int k = 0;

            long lOffset = 0;

            String sText = "";
            char[] cLookUp = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


            for (int i = 0; i < iBytes; i++)
            {
                b = brIn.readByte();
                bLow = (byte) (0x0F & b);
                bHigh = (byte) ((b >>> 4) & 0x0F);

                if (Character.isWhitespace( (char) b))
                    sText += " ";

                else if (Character.isLetterOrDigit( (char) b))
                    sText += "" + (char) b;

                else
                    sText += ".";
                
					System.out.print("" + cLookUp[bHigh] + cLookUp[bLow]);
                j++;

                if (j >= 8)
                {
                    System.out.print(" ");
                    j = 0;
                    k++;
                }
                if (k >= 4)
                {
                    System.out.println(" | " + sText + " |");
                    sText = "";
                    k = 0;
                }
            } 
            brIn.close();
           
            for (int i = 0; i < ( (17 * (4 - k)) - (2 * j) ); i++)
                System.out.print(" ");
            for (int i = 0; i < ( 32 - ((8 * k) + j)); i++)
                sText += " ";
            System.out.println(" | " + sText + " |");
        } 

        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    } 
}

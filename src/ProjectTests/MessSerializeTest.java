package ProjectTests;
import ChatService.PDUManagment.MessPDU;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class of JUnit tests server the purpose of testing all the features related to the
 * serializing of the PDU. No tests for the DeSerializing of the PDU can be found here.
 */
public class MessSerializeTest {

    private byte[] pduArr;
    private String message = "Welcomeeee";
    private String nickname = "Carleeeeee";
    int messageLength;

    @Before
    public void setup(){
        MessPDU messPDU = new MessPDU();
        pduArr = messPDU.serializePDU(message);
        messageLength = message.length();

    }

    /**
     * Test that the op nr is correct.
     */
    @Test
    public void opTest(){
        assertEquals(10, pduArr[0]);
    }

    /**
     * Test the length of the nickname (identity) of the client.
     *
     * Integer.compareUnsigned is used to make sure that the length of the
     * nickname will be interpreted correctly even if the name is longer
     * than 127 bits.
     */
    @Test
    public void nicknameLengthTest(){
        assertEquals(0, Integer.compareUnsigned(pduArr[2], 0));
    }

    /**
     * TODO: Check calculate the checksum locally here in the test, then make sure the answer is 255.
     */
    @Test
    public void checksumTest(){
        // Make sure that if you calculate a finished MessPDU (including checksum)
        // That the calculated value is equal to 255.
        int totalBytes = 0;
        int checksum;
        int temp;

        for (byte aPdu : pduArr){
            totalBytes += Byte.toUnsignedInt(aPdu);// Kanske räcker med (int)aPdu?
        }

        temp = totalBytes / 256;
        temp *= 256;
        checksum = totalBytes - temp;
        System.out.println("Checksum: " + checksum);



        /*
        short checksum = 0;
        for (byte aPdu : pduArr) {
            checksum += (int)aPdu;
            if (checksum > 255) {
                checksum -= 255;
            }
        }
        int temp = (byte)checksum + pduArr[3];
        System.out.println(Integer.toBinaryString(temp));
        //assertEquals(Integer.compareUnsigned((byte)temp, (byte)255), 0);*/
    }





    /**
     * Combine the two message length bytes into a single short, then
     * compare the expected length to the real length.
     */
    @Test
    public void messageLengthTest(){
        short messLength = (short)((pduArr[4] << 8) | (pduArr[5]));
        assertEquals(0, Integer.compareUnsigned(messLength, (short)messageLength));
    }

    /**
     * Read from the PDU byte Array and make sure it
     */
    @Test
    public void messageReadTest (){
        for (int i  = 0 ; i < messageLength; i++){
            System.out.print((char)pduArr[i+12]);
        }
    }
}

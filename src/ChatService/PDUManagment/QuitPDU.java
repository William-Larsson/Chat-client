package ChatService.PDUManagment;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * A class for creating the QUIT PDU that will be sent to the server when the client wants to quit.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 3 Oct. 2018.
 */

public class QuitPDU implements PDU {
    private final byte op = 11;

    /**
     * Creates a Quit PDU.
     *
     * @return = the finished PDU.
     */
    @Override
    public byte[] serializePDU(){
        byte[] pdu = new byte[4];
        pdu[0] = 11;
        return pdu;
    }

    /**
     * Deserialize the PDU, or in this case:
     * Get rid of unwanted padding.
     *
     * @param dataInputStream = the inputStream to read from.
     * @throws IOException = in case something goes wrong with the inputStream.
     */
    @Override
    public void deSerializePDU(DataInputStream dataInputStream) throws IOException {
        checkPadding(dataInputStream.readByte()); // padding
        checkPadding(dataInputStream.readByte()); // padding
        checkPadding(dataInputStream.readByte()); // padding
    }

    /**
     * Get method for the op number of a PDU.
     *
     * @return = the op number.
     */
    @Override
    public byte getOP() {
        return op;
    }
}

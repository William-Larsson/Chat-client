package ChatService.PDUManagment;

/**
 * A class for creating the GETLIST PDU that will be sent to the server.
 * This PDU will be used when we want the name server to send back a list
 * of all available servers that we can connect to.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public class GetListPDU implements PDU {

    private final byte op = 3;

    /**
     * Create the GetList PDU.
     *
     * @return = PDU as a byte[].
     */
    @Override
    public byte[] serializePDU(){
        byte[] pdu = new byte[4];
        pdu[0] = 3;
        return pdu;
    }

    /**
     * Get method for the op number.
     *
     * @return = the op number.
     */
    @Override
    public byte getOP() {
        return op;
    }
}

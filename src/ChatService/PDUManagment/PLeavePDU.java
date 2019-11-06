package ChatService.PDUManagment;

/**
 * The PLeave class. The only difference to PJoin is
 * the op number.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public class PLeavePDU extends LeaveAndJoin {
    private final byte op = 17;

    /**
     * Get method for the op number of a PDU.
     *
     * @return = the op number.
     */
    @Override
    public byte getOP() {
        return op;
    }

    /**
     * Get method for the IDs total length.
     *
     * @return = ID length as a number
     */
    public byte getIdLength() {
        return idLength;
    }

    /**
     * Get method for the timestamp representing the time
     * the client connected to the chat session.
     *
     * @return = the timestamp.
     */
    public int getTimeStamp() {
        return timeStamp;
    }


}

package ChatService.PDUManagment;
import java.nio.charset.StandardCharsets;

/**
 * A class sent from client to the server asking to connect to an ongoing
 * chat session on given server.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public class JoinPDU implements PDU {

    private final byte op = 16;

    /**
     * Method to create the JOIN PDU as a byte[].
     *
     * @return = PDU as a byte[].
     */
    @Override
    public byte[] serializePDU(String nickname){
        //create a temp byte[] just to find out how long the nickname is in bytes.
        int nicknameLength = nickname.getBytes(StandardCharsets.UTF_8).length;
        byte[] temp = new byte [nicknameLength];
        temp = nickname.getBytes(StandardCharsets.UTF_8);
        int nicknameTotalBytes = temp.length;

        //if the tempLength != (% 4) bytes, the add 1 byte until it does.
        while(nicknameTotalBytes % 4 != 0){
            nicknameTotalBytes++;
        }

        //create the pdu byte[] of correct length.
        int pduTotalBytes = 4 + nicknameTotalBytes;
        byte[] pdu = new byte[pduTotalBytes];
        pdu[0] = 12;
        pdu[1] = (byte)nicknameLength;

        for (int i = 0; i < temp.length; i++){
            pdu[i+4] = temp[i];
        }
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

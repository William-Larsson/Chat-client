package ChatService.PDUManagment;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that deSerializes the PDU the server sends as a response to JOIN.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public class ParticipantsPDU implements PDU {

    private final byte op = 19;
    private byte numIDs;
    public short length;
    private ArrayList<String> participants = new ArrayList<>();

    /**
     * deSerializes the given dataInputStream and saves the information we
     * need for later.
     *
     * @param dataInputStream = the inputStream carrying the PDU data.
     * @throws IOException = in case there is something goes wrong with the inputStream.
     */
    public void deSerializePDU(DataInputStream dataInputStream) throws IOException{

        numIDs = dataInputStream.readByte();
        length = dataInputStream.readShort();

        byte[] temp = new byte[255];
        int lengthToRead = length;
        int j = 0;
        // Read in and save every participant in the ArrayList of participants
        for (int i = 0; i < lengthToRead; i++) {
            temp[j] = dataInputStream.readByte();
            if(temp[j] == '\0') {
                String str = new String(temp, StandardCharsets.UTF_8).trim();
                participants.add(str);
                Arrays.fill(temp, (byte) 0);
                j=0;
            }
            j++;
        }
        // padding
        if (lengthToRead % 4 != 0) {
            int lengthToPad = 4 - (lengthToRead % 4);
            for (int i = 0; i < lengthToPad; i++) {
                checkPadding(dataInputStream.readByte());
            }
        }
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


    /**
     * Get method for the total number of ID's.
     *
     * @return = the number.
     */
    public byte getNumIDs() {
        return numIDs;
    }


    /**
     * Get method for the length of all the clients names, including null-terminating.
     *
     * @return = the length as a number.
     */
    public short getLength() {
        return length;
    }


    /**
     * Prints all current participants on the chat server
     */
    public void printParticipantList() {
        System.out.println(participants);
    }
}

package ChatService.PDUManagment;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Super class to PLeave and PJoin. Implements their equal deSerialize method,
 * as well a print method for when someone has joined of left the chat.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 22 Oct. 2018.
 */
public abstract class LeaveAndJoin implements PDU {
    byte idLength;
    int timeStamp;
    private String str;

    /**
     * deSerializes the given dataInputStream and saves the information
     *
     * @param dataInputStream = the inputStream carrying data to retrieve.
     * @throws IOException    = in case something goes wrong with the inputStream.
     */
    @Override
    public void deSerializePDU(DataInputStream dataInputStream) throws IOException {

        idLength = dataInputStream.readByte();
        checkPadding(dataInputStream.readByte());
        checkPadding(dataInputStream.readByte());
        timeStamp = dataInputStream.readInt();

        // Read in the Name of the client leaving/joining the session
        int lengthToRead = idLength;
        byte[] temp = new byte[lengthToRead];
        for (int i = 0; i < lengthToRead; i++) {
            temp[i] = dataInputStream.readByte();
        }
        // Padding
        if (lengthToRead % 4 != 0) {
            int lengthToPad = 4 - (lengthToRead % 4);
            for (int i = 0; i < lengthToPad; i++) {
                checkPadding(dataInputStream.readByte());
            }
        }

        str = new String(temp, StandardCharsets.UTF_8);
    }


    /**
     * Print method that first calculates the time, then print the
     * time as well as the user who has joined/leaved the chat.
     */
    public void printStr() {
        long unixTime = timeStamp;
        Date date = new java.util.Date(unixTime*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
        System.out.print(sdf.format(date) + ", " + str);
    }
}

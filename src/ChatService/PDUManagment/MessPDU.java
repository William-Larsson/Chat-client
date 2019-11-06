package ChatService.PDUManagment;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is meant to serialize/deserialize an outgoing/incoming
 * Message sent to/from the the server.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 19 Oct. 2018.
 */
public class MessPDU implements PDU {

    private final byte op = 10;
    private byte[] pdu;
    private byte idLength;
    private byte checksum;
    private short messlength;
    private int timeStamp;
    private String message;
    private String clientID;

    /**
     * Serialize a message that is to be sent from client to the server and later
     * to all other receiving client connected to the server.
     * This PDU will carry the written message that the user wrote.
     *
     * @param message  = the input from the user.
     * @return         = a PDU build as a byte[].
     */
    public byte[] serializePDU(String message){
        byte[] messageArr = message.getBytes(StandardCharsets.UTF_8);
        int messageLength = moduloFour(messageArr.length);

        pdu    = new byte[12 + messageLength];
        pdu[0] = 10;
        //pdu[2] = (byte) nickname.length(); // NOTE: Nickname can be more than 127 bits long!
        pdu[4] = (byte) (messageArr.length >> 8);
        pdu[5] = (byte)  messageArr.length;
        // pdu[6 t.o.m 11] should be nothing more than padding.

        // Add the message to the PDU byte[]
        for (int i = 0; i < messageArr.length; i++){
                pdu[i+12] = messageArr[i];
        }
        // Calculate and add checksum to the pdu.
        short checksum = calculateChecksum();
        pdu[3] = (byte) checksum;

        return pdu;
    }


    /**
     *  An incoming message received from the server. This PDU contains a
     *  written message from another client on the same chat server.
     *
     * @param dataIn       = ingoing data stream, used to read all the given data.
     * @throws IOException = in case the DataInputStream fails for some reason.
     */
    public void deserializePDU(DataInputStream dataIn) throws IOException {
        checkPadding(dataIn.readByte()); // padding.
        idLength          = dataIn.readByte();
        checksum          = dataIn.readByte();
        messlength        = dataIn.readShort();
        checkPadding(dataIn.readByte()); // padding
        checkPadding(dataIn.readByte()); // padding
        timeStamp         = dataIn.readInt();

        int lengthToRead = moduloFour(messlength);
        byte[] tempMessage = new byte[lengthToRead];

        // Build the message.
        for (int i = 0 ; i < lengthToRead; i++){
            if(i < messlength){
                tempMessage[i] = dataIn.readByte();
            }
            else {
                checkPadding(dataIn.readByte()); // padding
            }
        }
        message = new String(tempMessage, StandardCharsets.UTF_8);

        // Read correct amount of bytes for the clientID.
        idLength = (byte) moduloFour(idLength);
        byte[] tempID = new byte[idLength];

        // Build the client name.
        for (int i = 0 ; i < idLength; i++){
            tempID[i] = dataIn.readByte();
        }
        clientID = new String(tempID, StandardCharsets.UTF_8).trim();
    }


    /**
     * A method meant to calculate the checksum of the PDU
     * before it is sent.
     *
     * @return = the calculated checksum as a short.
     */
    private short calculateChecksum(){
        int totalBytes = 0;
        int checksum;
        int temp;

        for (byte aPdu : pdu){
            totalBytes += Byte.toUnsignedInt(aPdu); // Is it enough with (int)aPdu?
        }

        // changed
        temp       = totalBytes / 255;
        temp       *= 255;
        checksum   = totalBytes - temp;
        checksum   = 255 - checksum;
        return (short)checksum;
    }


    /**
     * Calculates if quotient of a given number divided by 4
     * leaves a remainder or not. If there is a remainder,
     * add +1 to the given number until there is none.
     *
     * @param number = given number representing the length of something
     * @return = the new number which can evenly be divided by 4.
     */
    private int moduloFour(int number){
        while (number % 4 != 0){
            number++;
        }
        return number;
    }


    /**
     * Method for printing the message
     */
    public void printMessage(){
        System.out.println(message);
    }

    /**
     * Method for printing the clientID
     */
    public void printClientID(){
        System.out.println(convertUnixTimeToClock() + ", " + clientID + ":");
    }


    /**
     * Calculates the given unix time to a readable clock format
     * that shows when the message was sent.
     *
     * @return = string representing the time of the message was sent
     */
    private String convertUnixTimeToClock(){
        long unixTime = timeStamp;
        Date date = new java.util.Date(unixTime*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
        return sdf.format(date);
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

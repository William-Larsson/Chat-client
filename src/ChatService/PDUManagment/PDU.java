package ChatService.PDUManagment;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *  An interface from which all other PDUs will inherit from.
 *  Because of complications where some PDUs will need certain functionality
 *  that others doesn't, this interface implements several default methods
 *  so that the subclasses doesn't have to implement useless methods.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public interface PDU {

    /**
     * Get method for the op number of a PDU.
     *
     * @return = the op number.
     */
    byte getOP();

    /**
     * Common method for serializing a PDU.
     *
     * @return = the finished PDU.
     */
    default byte[] serializePDU() {return null;}

    /**
     * Overloaded version of the common serializing method.
     * Includes a string for the methods that require that.
     *
     * @param string = a given string needed to build the PDU.
     * @return = the finished PDU.
     */
    default byte[] serializePDU(String string) {return null;}

    /**
     * A common deSerializing method used to retrieve data from a
     * given inputStream.
     *
     * @param dataIn = the inputStream to read form.
     * @throws IOException = in case something goes wrong with the inputStream.
     */
    default void deSerializePDU(DataInputStream dataIn) throws IOException {}

    default void checkPadding(byte b) {
        if (b != 0) {
            System.err.println("Incorrect padding from server");
        }
    }
}

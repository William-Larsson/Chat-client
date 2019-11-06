package ChatService.PDUManagment;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import static java.lang.Byte.toUnsignedInt;

/**
 * A class for the SLIST PDU that will be sent from the server.
 * This class is meant to deserialize the PDU and make it useful
 * to the rest of the program.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
 */
public class SListPDU implements PDU {

    private final byte op = 4;
    private short numServers;
    private ArrayList<Server> serversList = new ArrayList<>();

    /**
     * Takes the PDU byte[] and turn it into useful and understandable
     * data types.
     */
    public void deSerializePDU(DataInputStream dataInputStream) throws IOException {

        checkPadding(dataInputStream.readByte()); // padding
        numServers       = dataInputStream.readShort();

        for(int j = 0; j < numServers; j++) {
            StringBuilder address = new StringBuilder();
            // reads the address byte for byte and saves it as a string ex: x.x.x.x
            byte[] readAddress = new byte[4];
            for (int i = 0; i < 4; i++) {
                readAddress[i] = dataInputStream.readByte();
                int ip = toUnsignedInt(readAddress[i]);
                String tempStr = String.valueOf(ip);

                if (i > 0) {
                    address.append('.');
                }
                address.append(tempStr);
            }

            short port            = dataInputStream.readShort();
            byte numClients       = dataInputStream.readByte();
            byte serverNameLength = dataInputStream.readByte();

            byte[] temp = new byte[serverNameLength];
            // Reads the name of the server
            for (int i = 0; i < serverNameLength; i++) {
                temp[i] = dataInputStream.readByte();
            }
            String serverName = new String(temp, StandardCharsets.UTF_8);
            serversList.add(new Server(address, Short.toUnsignedInt(port), numClients, serverNameLength, serverName));

            // padding
            if ((int) serverNameLength % 4 != 0) {
                int lengthToPad = 4 - ((int) serverNameLength % 4);
                for (int i = 0; i < lengthToPad; i++) {
                    checkPadding(dataInputStream.readByte());
                }
            }
        }
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


    /**
     * Get the total number of servers available.
     *
     * @return = nr of servers.
     */
    public short getNumOfServers() {
        return numServers;
    }


    /**
     * Get the full list of all chat servers.
     *
     * @return = the list of servers.
     */
    public ArrayList<Server> getServersList() {
        return serversList;
    }
}

package ChatService.PDUManagment;

/**
 * A class representing the given servers sent to the client in a SList PDU.
 * Each server in the given SList PDU will be assigned to a new instance of
 * this class for ease of management when later choosing a server to connect to.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 21 Oct. 2018.
 */
public class Server {

    private StringBuilder address;
    private String serverName;
    private byte serverNameLength;
    private byte numClients;
    private int port;

    /**
     * A constructor which saves all the information about each separate
     * chat server that the client can connect to.
     *
     * @param address = the address / Ipv4 of this particular chat server.
     * @param port = the port the chat server listens to.
     * @param numClients = number of clients currently connected to the chat server.
     * @param serverNameLength = the length of the chat server name.
     * @param serverName = the name of the chat server.
     */
    Server(StringBuilder address, int port, byte numClients, byte serverNameLength, String serverName) {
        this.address = address;
        this.port = port;
        this.numClients = numClients;
        this. serverNameLength = serverNameLength;
        this.serverName = serverName;
    }


    /**
     * Simple print method for the given address.
     */
    public void printAddress() { System.out.println(address); }


    /**
     * Get the address for this server.
     *
     * @return = the address of the server as a string.
     */
    public String getAddress() { return address.toString(); }


    /**
     * Get method for fetching the port nr of this chat server.
     *
     * @return = the port number as a short.
     */
    public int getPort() { return port; }


    /**
     * Get method for fetching the number of clients currently
     * connected to this chat server.
     *
     * @return = number of clients.
     */
    public byte getNumOfClients() {
        return numClients;
    }


    /**
     * Get the length of the name of the server.
     *
     * @return = length of the server name a byte.
     */
    public byte getServerNameLength() {
        return serverNameLength;
    }


    /**
     * Simple print method for viewing the chat server name in the console.
     */
    public void printServerInfo() {
        System.out.println(serverName + "   Port: " + port + "   Nr. of Participants: " + Byte.toUnsignedInt(numClients));
    }
}

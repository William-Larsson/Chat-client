package ChatService.SocketAbstraction;
import ChatService.PDUManagment.GetListPDU;
import ChatService.PDUManagment.SListPDU;
import ChatService.PDUManagment.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  A supporting class for the Chat Logic that is solely responsible for
 *  connecting to a chat server in case we have initialized a connection
 *  to a name server.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 16 Oct. 2018.
 */
class NameServer {
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private SListPDU slist;
    private Server chatServer;


    /**
     * Constructor that is given input/output streams to the name server.
     *
     * @param dataIn  = incoming stream from name server
     * @param dataOut = outgoing stream to the name server
     */
    NameServer(DataInputStream dataIn, DataOutputStream dataOut){
        this.dataIn = dataIn;
        this.dataOut = dataOut;
    }


    /**
     * Calls methods that will request list of available servers and
     * deserialize the server response for later use.
     *
     * @throws IOException = in case there is an error with the stream
     */
    void requestSList() throws IOException {
        // get a SList:
        sendRequest(new GetListPDU());
        DeSerialize deSerialize = new DeSerialize();
        slist = (SListPDU) deSerialize.deSerialize(dataIn.readByte(), dataIn);
    }

    /**
     *  Calls methods that will print the available servers as well as
     *  gives the user opportunity to choose a chat server from the
     *  the available list of servers.
     */
    void chooseChatServer(){
        // Choose a chat server:
        printAvailableServers(slist.getServersList());
        chatServer = userChoice(slist.getServersList());
    }


    /**
     * Sends a GetList PDU to the name server to initialize a response.
     *
     * @param getList      = GetList class instance
     * @throws IOException = in case there is an error with the stream
     */
    private void sendRequest(GetListPDU getList) throws IOException {
        dataOut.write(getList.serializePDU());
        dataOut.flush();
    }


    /**
     * Prints all available servers that the client can connect
     * with to the console.
     *
     * @param servers = list of all chat servers
     */
    private void printAvailableServers(ArrayList<Server> servers) {
        System.out.println("\nAll available chat servers: ");
        for (int i = 1; i <= servers.size(); i++){
            Server s = servers.get(i-1);
            System.out.print(i + ": ");
            s.printServerInfo();
        }
    }


    /**
     * Prompts user to choose a server to connect with a
     * reads user input. Returns the chosen server.
     */
    private Server userChoice(ArrayList<Server> servers) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose a chat server by entering the number written before it: ");
        return servers.get(scanner.nextInt());
    }


    /**
     *  Get the address of the user chosen chat server.
     *
     * @return = the chat server IP-address
     */
    String getServerAddress(){
        return chatServer.getAddress();
    }


    /**
     * Get the port number of the user chosen chat server.
     *
     * @return = the port number that the server listens to.
     */
    int getServerPort(){
        return chatServer.getPort();
    }
}

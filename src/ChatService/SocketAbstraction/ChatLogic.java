package ChatService.SocketAbstraction;
import ChatService.PDUManagment.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The chat logic. This is the center hub for the whole chat client.
 * This class initializes a connection to a chat server and contains
 * all the main algorithms for controlling incoming and outgoing data.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 21 Oct. 2018.
 */
class ChatLogic {

    private Socket socket = null;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private InputQueueThread inputThread;
    private OutputQueueThread outputThread;
    private Thread read = null;
    private Thread write = null;

    /**
     * Constructor for the chat logic. It serves the purpose of creating a
     * connection with the chat server so that the rest of the program
     * can start from there.
     *
     * @param identity     = client nickname
     * @param typeOfServer = chat server or name server
     * @param hostServer   = the server address
     * @param serverPort   = the server port
     * @throws IOException = in case there is a problem with the streams
     */
    ChatLogic(String identity, String typeOfServer, String hostServer, int serverPort) throws IOException {
        // Start the socket and streams.
        initializeConnection(hostServer, serverPort);

        if (typeOfServer.equals("ns")){
            NameServer ns = new NameServer(dataIn, dataOut);
            ns.requestSList();
            close();
            ns.chooseChatServer();
            initializeConnection(ns.getServerAddress(), ns.getServerPort());
        }
        else if (!typeOfServer.equals("cs")){
            System.err.println("Wrong server type input!");
        }

        inputThread = new InputQueueThread(dataIn);
        outputThread = new OutputQueueThread(identity, dataOut);
    }


    /**
     * This thread reads the queue of PDUs that have arrived to
     * the client from the server and redirects the given PDU
     * to the whatToDoNext method.
     */
    private Thread readThread = new Thread(){
        @SuppressWarnings("InfiniteLoopStatement")
        public void run(){
        while (read != null) {
            if (!inputThread.isInputQueueEmpty()) {
                try {
                    PDU pdu = inputThread.getPDUFromQueue();
                    whatToDoNext(pdu);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    };


    /**
     * This thread reads input from the console that has bee written
     * by the user of the program. After the user inputs "Enter" the
     * thread then interprets the input either as a message or as a
     * prompt to close the program.
     */
    private Thread writeThread = new Thread(){
        public void run(){
            Scanner stdin = new Scanner(System.in);
            while (write != null) {
                if (stdin.hasNextLine()){
                    String str = stdin.nextLine();
                    if (str.toLowerCase().equals("avsluta")){
                        QuitPDU quitPDU = new QuitPDU();
                        outputThread.addToOutputQueue(quitPDU.serializePDU());
                        break;
                    }
                    else {
                        MessPDU messPDU = new MessPDU();
                        outputThread.addToOutputQueue(messPDU.serializePDU(str));
                    }
                }
            }
        }
    };


    /**
     * This will set the connection between the client and the chat server as well as
     * initialize all functions needed for communication with the chat server.
     *
     * @param hostServer   = the chat server
     * @param serverPort   = the chat server port number
     * @throws IOException = in case there is an error with the socket
     */
    private void initializeConnection(String hostServer, int serverPort) throws IOException {
        socket = new Socket(hostServer, serverPort);
        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Method that takes a PDU from the inputQueue and decides what to do next.
     *
     * @param pdu = given PDU that has been sent to the client from the server.
     * @throws IOException = if there is any issue with the socket or the streams
     */
    private void whatToDoNext(PDU pdu) throws IOException {
        byte opNr = 0;
        if (pdu != null) {
            opNr = pdu.getOP();
        }

        switch (opNr) {
            case 10:// Mess
                MessPDU messPDU = (MessPDU) pdu;
                // Prints to all
                messPDU.printClientID();
                messPDU.printMessage();
                break;

            case 11:// Quit
                // close down threads, streams and socket.
                stopThreads();
                close();
                break;

            case 16:// PJoin
                PJoinPDU pJoinPDU = (PJoinPDU) pdu;
                // Prints to all
                pJoinPDU.printStr();
                System.out.println(" has joined the session.");
                break;

            case 17://PLeave
                PLeavePDU pLeavePDU = (PLeavePDU) pdu;
                // Prints to all
                pLeavePDU.printStr();
                System.out.println(" has left the session.");
                break;

            case 19:// Participants
                ParticipantsPDU parPDU = (ParticipantsPDU) pdu;
                // Prints only to client
                System.out.println("Participants in current session:");
                parPDU.printParticipantList();
                break;

            default:
                System.out.println("Invalid OP-number.");

        }
    }

    /**
     * Method for starting all of the four threads that
     * the program rely on.
     */
    void startThreads(){
        readThread.start();
        read = readThread;

        inputThread.start();
        inputThread.setThread(inputThread);

        writeThread.start();
        write = writeThread;

        outputThread.start();
        outputThread.setThread(outputThread);
    }

    /**
     * Will stop all the threads by termination their respecting
     * while loops.
     */
    private void stopThreads(){
        outputThread.setThread(null);
        write = null;
        inputThread.setThread(null);
        read = null;
    }

    /**
     * Close down current socket as well as the streams associated
     * with said socket.
     *
     * @throws IOException = if there is an error while closing.
     */
    private void close() throws IOException {
        dataOut.close();
        dataIn.close();
        socket.close();
    }
}
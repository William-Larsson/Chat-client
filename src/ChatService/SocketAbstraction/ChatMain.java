package ChatService.SocketAbstraction;
import java.io.IOException;

/**
 *  ChatMain class for the chat client part of the chat service program.
 *  This main function is only meant to start the execution of the
 *  the main chat logic algorithm and corresponding threads.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 21 Oct. 2018.
 */
public class ChatMain {

    /**
     * The main function, meant to kick-start the rest of the execution
     * that will be done in the client logic.
     *
     * @param args = 0: Id (Nickname), 1: ns or cs, 2: ns- or cs-host. 3: server port
     */
    public static void main (String args[]) throws IOException {
        // Start the ChatLogic.
        ChatLogic chatLogic = new ChatLogic(args[0], args[1], args[2], Integer.parseInt(args[3]));
        chatLogic.startThreads();
    }
}
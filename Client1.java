/**
 * The class Client1 sets up a network client for sending chat messages using the UDP protocol. 
 * The class sets up a datagram socket to send and receive datagram packets through. 
 * It calculates a checksum on the output message and displays it for data integrity validation.
 * @author RNGTAM002, GLBALI002, RJKRAH001.
 * @date 1st April 2021. 
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.Adler32;

public class Client1 {
  
  /**
  * Main method: 
  * Gets the IP address of the host.
  * Sets the destination port, which is the server's listening port, to 5298. 
  * Sets the boolean running value to true, where when the clients are finished communicating, this value is changed to false. 
  */ 
  public static void main(String[] args) throws Exception {
    long sendChecksum;
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address of host. 
    int messageNum = 1;
    int sendport = 5298; //set destination port
    boolean running =  true;

    /**
    * Creates the first client's socket with port number 4444. 
    * This client first sends a message. 
    * The client inputs the message which is then converted into bytes. 
    * The datagram packet of the message is sent to the server with port 5298. 
    */
    try {
      DatagramSocket clientsocket1 = new DatagramSocket(4444); 
      while(running){
        byte[] sendmessage = new byte[1024];
        System.out.print("Enter Message: "); //match this with GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input
        System.out.println("Sent message " + messageNum + ": "+ input); //match with GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket1.send(sendpacket);

        /**
        * If the client has finished communicating, they can type end as an input to leave.
        * The socket is then closed. 
        */
        if (input.equals("end")){
          running = false;
          clientsocket1.close();
          continue;
        }
        
        /**
        * Checksum output calculation.
        * This calculates the checksum value based on the message to be sent. 
        * This value is printed for debugging purposes and is then compared to the checksum value calculated on the server side which is also printed. 
        */
        //Server server = new Server();
        ByteArrayInputStream bias = new ByteArrayInputStream(sendmessage);
        CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
        byte readBuffer[] = new byte[10];
        while(cis.read(readBuffer) >= 0) {
          sendChecksum = cis.getChecksum().getValue();
          //System.out.println("Checksum value: " + sendChecksum);
          //server.setSendChecksum(sendChecksum);
        }
        
        /**
        * This is when client 1 receives a message from client 2 through the server. 
        */
        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket1.receive(receivepacket);
        String inputText = new String(receivepacket.getData(), 0, receivepacket.getLength());
        String inputText1 = new String(receivepacket.getData());
  
        /**
        * If the message received is 'end', communication has finished and the client can leave the application.
        * The socket is then closed. 
        */
        if (inputText1.equals("end")){
          running = false;
          clientsocket1.close();
          continue;
        }
        
        /**
        * The message is printed with an indication that it has been received. 
        * A message counter is increased to ensure tracking of the number of messages being sent and received. 
        */
        System.out.println("Received: "+ inputText); 
        messageNum += 2;

      } 
    }
    
    /** 
    * Catching errors for Timeout exceptions and Input Output exceptions. 
    */
    catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
  }
}
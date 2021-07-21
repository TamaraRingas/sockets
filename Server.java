/**
 * The class Server sets up a network host for sending chat messages using the UDP protocol. 
 * The class sets up a port to listen for incoming datagram packets which contain the messages.
 * It sends and receives the datagram packets through datagram sockets.
 * @author RNGTAM002, GLBALI002, RJKRAH001.
 * @date 1st April 2021.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Server {
    private static long sendChecksum;
    
    /**
    * Method to set the send checksum value. 
    * @param long Checksum value. 
    */
    public void setSendChecksum(long sendChecksum){
      this.sendChecksum = sendChecksum;
    }

    /**
    * Method to get the send checksum value.
    */
    public static long getSendChecksum(){
      return sendChecksum;
    }

    /**
    * Main method:
    * Sets listening port to 5298 which is the destination port for the clients. 
    * Creates the server socket on this port. 
    * Sets running boolean value to true, when the clients have finished communicating this value is changed to false. 
    */ 
    public static void main(String[] args) throws Exception{
      int listeningport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listeningport);
      byte[] sendmessage = new byte[1024];
      byte[] receivemessage = new byte[1024];
      long receiveChecksum;
      boolean running = true;
      try{
        while(running){ 
            
             /**
             * Receives the messages from the clients. 
             * Converts the message into bytes. 
             * Gets the IP address and the port number that the message came from. 
             * Prints out the message and received for checking purposes. 
             */
              DatagramPacket fromClient = new DatagramPacket(receivemessage, receivemessage.length);
              serversocket.receive(fromClient);

              String input = new String(fromClient.getData());
              byte[] message = input.getBytes();
              InetAddress ip = fromClient.getAddress();
              int port = fromClient.getPort(); 
              System.out.println("Received: "+ input);

              /**
              * If the message received is 'end', communication has finished between the clients.
              * The server socket is then closed. 
              */
              if (input.equals("end")){
                running = false;
                serversocket.close();
                continue;
              }

              /**
              * Checks if the message was received from client 1 which has the port number 4444. 
              * If this is the case the message is sent to client 2 with port 4445. 
              * The checksum value is calculated on the message. 
              * This value is printed for debugging purposes and is then compared to the checksum value calculated on the client side which is also printed. 
              * The message is sent. 
              */
              if (port == 4444) {
                DatagramPacket toClient = new DatagramPacket(message, message.length, ip, 4445);
                long checksum = getSendChecksum();
              
                ByteArrayInputStream bias = new ByteArrayInputStream(receivemessage);
                CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
                byte readBuffer[] = new byte[10];
                while(cis.read(readBuffer) >= 0) {
                  receiveChecksum = cis.getChecksum().getValue();
                }
                long setChecksum = getSendChecksum();
                serversocket.send(toClient); 
                 
              }
            
            /**
            * Checks if the message was received from client 2 which has the port number 4445. 
            * If this is the case the message is sent to client 1 with port 4444. 
            * The checksum value is calculated on the message. 
            * This value is printed for debugging purposes and is then compared to the checksum value calculated on the client side which is also printed. 
            * The message is sent. 
            */
            if (port == 4445) {
               DatagramPacket toClient = new DatagramPacket(message, message.length, ip, 4444);

              ByteArrayInputStream bias = new ByteArrayInputStream(receivemessage);
              CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
              byte readBuffer[] = new byte[10];
              while(cis.read(readBuffer) >= 0) {
                 receiveChecksum = cis.getChecksum().getValue();
              }
              serversocket.send(toClient); 
                
            }
          
        }
      }
        
      /** 
      * Catching errors for Timeout exceptions. 
      */
      catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
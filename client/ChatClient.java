// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  
  String loginID;
  
  boolean loggedIn;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI, String login) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    loginID = login;
    this.clientUI = clientUI;
    
    if(loginID.equals("")) {
    	System.out.println("Users must input a user ID");
    	quit();
    }
    
    openConnection();
    loggedIn = true;
    this.sendToServer("#login " + loginID);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {  
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
	    switch(message) {
	  	  case("#quit"):
	  		System.out.println("Quitting the service");
	  	  	quit();
	  	  case("#logoff"):
	  		  // This does not disconnect from server!
	  		System.out.println("Logging off");
	  	  	this.closeConnection();
	  	  	loggedIn = false;
	  	  	//connectionClosed();
	  	  case("#sethost"):
	  		  break;
	  	  case("#setport"):
	  		  break;
	  	  case("login"):
	  		  if(loggedIn = false) {
	  			  this.openConnection();
	  		  }
	  		  else {
	  			  System.out.println("User already logged in!");
	  		  }
	  		  break;
	  	  case("#gethost"):
	  		System.out.println(this.getHost());
	  	  	break;
	  	  case("#getport"):
	  		System.out.println(this.getPort());
	  	  	break;
	    }
	    
	    
	    if (message.contains("#setport")){
			int port = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
			this.setPort(port);
			System.out.println("Port has been changed to " + port);}
	    else if (message.contains("#sethost")) {
	    	String host = message.substring(message.lastIndexOf(" ") + 1);
	    	this.setHost(host);
	    	System.out.println("Host has been changed to " + host);
	    }
	    
	    
	    
	    sendToServer(message);    
    }catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  @Override
  protected void connectionClosed() {
	  System.out.println("Server has shutdown, now quitting");
  }
  
  @Override 
  protected void connectionException(Exception e) {
	  System.out.println("Server has shutdown, now quitting");
	  System.exit(0);
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
}
//End of ChatClient class

import java.net.*;
import java.util.*;
import java.io.*;

public class Server implements Runnable, IServer
{
	private int port; 
	private int id;
	private boolean running;
	private ArrayList<IClient> clientList;
	
	public static void main(String[] args)
	{
		Server server = new Server(99);
		Thread t = new Thread(server);
		t.start();
		try{
		System.out.println("Server started\n" + 
			"Connected to: " + InetAddress.getLocalHost() + " and port 99");
		}
		catch(Exception e) { e.printStackTrace(); }
	}
    public Server(int port) 
    {
    	this.port = port;
    	running = true;
    	id = 0;
    	clientList = new ArrayList<IClient>();
    }
    
    public IClient[] getClients()
    {
    	return (IClient[])clientList.toArray(new IClient[]{});
    }
    
    public void broadcast(String data)
    {
    	for(IClient c : clientList)
    		c.send(data);
    }
    
    public void remove(IClient c)
    {
    	for(int i = 0; i < clientList.size(); i++)
    		if(clientList.get(i).getId() == c.getId()){
    			clientList.remove(i);
    			break;
    		}
    	System.out.println(c.getHandle() + " has disconnected");
    }
    
    public void run()
    {
    	Random rand = new Random();
    	try{
    		ServerSocket listener = new ServerSocket(port);
    		while(running){
    			Socket clientSocket = listener.accept();
    			IClient client = new ServerSideClient(nextID(), this, clientSocket);
    			clientList.add(client);
    			System.out.println(client.getHandle() + " connected");
    		}
    	}
    	catch(Exception e){ e.printStackTrace(); }
    }
    
    public void stop()
    {
    	running = false;
    }
    public int nextID(){ return id++; }
}
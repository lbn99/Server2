import java.net.*;
import java.util.*;
import java.io.*;

public class ServerSideClient  implements Runnable, IClient
{
	private String handle;
	private int id;
	private IServer server;
	private Socket socket;
	private boolean running;
	private ArrayList<INetworkListener> listenerList;
	private BufferedReader in;
	private PrintWriter out;
	
    public ServerSideClient(int id, IServer server, Socket socket) throws IOException
    {
    	Random rand = new Random();
    	this.id = id;
    	this.server = server;
    	this.socket = socket;
    	running = true;
    	in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    	out = new PrintWriter(socket.getOutputStream(), true);
    	listenerList = new ArrayList<INetworkListener>();
    	Thread t = new Thread(this);
    	t.start();
    	
    	handle = "Anon" + (rand.nextInt(900000)+100000);
    	
		addNetworkListener(new QuitCommand());
		addNetworkListener(new SayCommand());
		addNetworkListener(new SetHandleCommand());
		addNetworkListener(new ListCommand());
		
		for(IClient c : server.getClients())
			if(c.getHandle() != handle)
				c.send("ADD " + handle);
		send("SETHANDLE " + handle);
    }
    
    public String getHandle()
    {
    	return handle;
    }
    
    public void setHandle(String h)
    {
    	handle = h;
    }
    
    public void addNetworkListener(INetworkListener listener)
	{
		listenerList.add(listener);
	}
    
    public int getId()
    {
    	return id;
    }
    
    public void run()
    {
    	while(running){
    		try{
    			process(in.readLine());
    		}
    		catch(Exception e) { 
    			stop();
    			server.remove(this);
    		}
    	}
    }
    
    public void stop()
    {
    	running = false;
    }
    
    //data from server (to client)
    public void send(String data)
    {
    	for(INetworkListener l : listenerList)
    		l.send(data, this);
    	out.println(data);
    }
    
    //data from client (to server)
    public void process(String message)
    {
    	System.out.println(">" + message);
    	for(INetworkListener l : listenerList)
    		l.process(message, this, server);
    }
}
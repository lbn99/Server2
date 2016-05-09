public interface IClient 
{
	//This is called when you want to send data to the server
	//Before sending data to the server, you should call the send method on all INetworkListener objects that are listening to this client
	public void send(String data);
	
	//This is called when data is received from the server
	//This data should be passed to all of the INetworkListener objects that are listening to this client
	public void process(String str);

	//This adds the parameter INetworkListener to a list of INetworkListeners.
	//Everything in that List is considered to be "listening" to this Client
	//Objects that are listening to this Client are able to respond to send and process events
	public void addNetworkListener(INetworkListener listener);
	
	//This has an "infinite" loop that listens for data from the server
	public void run();
	
	//This makes the run method stop it's "infinite" loop
	public  void stop();
	
	//Return the handle of this client
	public String getHandle();
	
	//Set the handle of this client
	public void setHandle(String s);
	
	//Return the ID of this client
	public int getId();
}
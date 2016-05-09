
class QuitCommand extends NetworkListenerAdapter
{
	public static String COMMAND = "QUIT";
	
	//send message to client
	public void send(String message, IClient client)
	{
		if(isCommand(message, COMMAND))
		{
			client.stop();
		}
	}
	
	//receive from server
	public void process(String message, IClient client, IServer server)
	{
		if(isCommand(message, COMMAND))
		{
			client.send("DISCONNECT");
			server.remove(client);
			server.broadcast("REMOVE " + client.getHandle());
		}
	}
}
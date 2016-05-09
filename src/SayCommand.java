
class SayCommand extends NetworkListenerAdapter
{
	public static String COMMAND = "SAY";
	
	//receive from server
	public void process(String message, IClient client, IServer server)
	{
		if(isCommand(message, COMMAND))
		{
			server.broadcast(COMMAND + " " + client.getHandle().length() + " " + client.getHandle() + message.substring(4));
		}
	}
}
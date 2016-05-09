
class ListCommand extends NetworkListenerAdapter
{
	public static String COMMAND = "LIST";
	
	//receive from server
	public void process(String message, IClient client, IServer server)
	{
		if(isCommand(message, COMMAND))
		{
			String list = "LIST ";
			for(IClient c : server.getClients())
				list += c.getHandle().length() + " " + c.getHandle();
			client.send(list);
		}
	}
}

class SetHandleCommand extends NetworkListenerAdapter
{
	public static String COMMAND = "SETHANDLE";
	
	//receive from server
	public void process(String message, IClient client, IServer server)
	{
		if(isCommand(message, COMMAND))
		{
			boolean goodHandle = true;
			for(IClient c : server.getClients())
				if(c.getHandle().equals(message.substring(10)))
					goodHandle = false;
			if(goodHandle){
				String oldHandle = client.getHandle();
				client.setHandle(message.substring(10));
				server.broadcast("RENAME " + oldHandle.length() + " " + oldHandle + client.getHandle().length() + " " + client.getHandle());
				client.send(COMMAND + " " + client.getHandle());
			}
			else
				client.send("BADHANDLE This handle is already taken!");
		}
	}
}
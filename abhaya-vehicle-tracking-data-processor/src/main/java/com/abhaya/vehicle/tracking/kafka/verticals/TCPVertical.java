package com.abhaya.vehicle.tracking.kafka.verticals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TCPVertical extends AbstractVerticle 
{
	
	@Value("${tcp.host.port}")
	private  int port;

	private static final int BUFF_SIZE = 32 * 1024;
	
	@Autowired
	private ProducerVertical producerVertical;
	
	@Override
	public void start() throws Exception 
	{
		NetServerOptions def = new NetServerOptions().setReceiveBufferSize(BUFF_SIZE).setSendBufferSize(BUFF_SIZE).
							   setTcpKeepAlive(true).setTcpNoDelay(true).setUsePooledBuffers(true);
		
		NetServer server = vertx.createNetServer(def);
		server.connectHandler(new Handler<NetSocket>() 
		{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void handle(NetSocket sock) 
			{
				Buffer fullRequestBody = Buffer.buffer();
				sock.handler(new Handler<Buffer>() 
				{
					public void handle(Buffer buffer) 
					{
						fullRequestBody.appendBuffer(buffer);
					}
				});
				sock.endHandler(new Handler()
				{
					@Override
					public void handle(Object arg0) 
					{
						try 
					     {
							if (fullRequestBody.length() > 0)
							{
								//producerVertical.produce(fullRequestBody);
							}
						 } 
					     catch (Exception e) 
					     {
							log.info("Exception in Vert.x:: "+e.getMessage());
						}
					}
				});
			}
		});
		server.listen(port);
	}
}

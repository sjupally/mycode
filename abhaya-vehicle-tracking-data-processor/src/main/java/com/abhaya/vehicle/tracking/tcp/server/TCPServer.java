package com.abhaya.vehicle.tracking.tcp.server;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.processor.DataIngestionProcessor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TCPServer extends AbstractVerticle 
{

	@Value("${tcp.host.port}")
    private  int port;
	
	private static final int BUFF_SIZE = 32 * 1024;
	
	private DataIngestionProcessor dataIngestionProcessor;
	
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
							log.info("****** I received " + fullRequestBody.length() + " bytes of data");
							if (fullRequestBody.length() > 0)
						      vertx.eventBus().send("next.topic", fullRequestBody);
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
		EventBus eb = vertx.eventBus();
		eb.consumer("next.topic", new Handler<Message<Buffer>>() 
		{
			@Override
			public void handle(Message<Buffer> output) 
			{
				try 
				{
					ByteArrayOutputStream output1 = new ByteArrayOutputStream();
					output1.write(output.body().getBytes());
					log.info("****Recieved total Data Packet:: " + Hex.encodeHexString(output1.toByteArray()).toUpperCase());
					dataIngestionProcessor.parse(Hex.encodeHexString(output1.toByteArray()).toUpperCase());
				} 
				catch (Exception e) 
				{
					log.info("Exception in Vert.x:: "+e.getMessage());
				}
			}
		});
	}
}
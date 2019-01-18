package com.abhaya.vehicle.tracking.kafka.verticals;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import io.vertx.kafka.client.producer.RecordMetadata;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProducerVertical extends AbstractVerticle 
{
	@Value("${tcp.host.port}")
	private  int port;
	
	@Value("${tcp.host.name}")
	private  String host;

	private static final int BUFF_SIZE = 32 * 1024;
	
	@Autowired KafkaProperties kafkaProperties ;
	
	@Override
	public void start() throws Exception
	{
		log.info("************** Host : " + host + "  - port : "+ port);

		KafkaProducer<String, String> producer = kafkaProperties.createKafkaProducer(vertx);
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
							log.info("*************Length of Packet -  " + fullRequestBody.length());
							if (fullRequestBody.length() > 0)
							{
								log.info("*************Packet -  " + fullRequestBody);
								//if (!"#GNV".equalsIgnoreCase(fullRequestBody.toString().split(",")[0]))
								/*if(!fullRequestBody.toString().split(",")[0].startsWith("#GNV"))
								{
									log.info("fullRequestBody.toString() " + fullRequestBody.toString() );
									log.info("Hex.encodeHexString " + Hex.encodeHexString(fullRequestBody.getBytes()).toUpperCase() );
								}*/
								KafkaProducerRecord<String, String> record =  KafkaProducerRecord.create("abhaya-topic", Hex.encodeHexString(fullRequestBody.getBytes()).toUpperCase());
								producer.write(record, done -> 
								{
									if (done.succeeded()) 
									{
										 RecordMetadata recordMetadata = done.result();
									}
								});
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
			server.listen(port, host);
	}
}

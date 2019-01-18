package com.abhaya.vehicle.tracking.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ESConfig 
{
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	
	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;
	
	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException 
	{
		
		 String server = clusterNodes.split(":")[0];
         Integer port = Integer.parseInt(clusterNodes.split(":")[1]);
		
	    Settings settings = Settings.builder()
	            .put("client.transport.sniff", true)
	            .put("cluster.name", clusterName).build();

	    TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(server), port));

	    return new ElasticsearchTemplate(client);

	}
}

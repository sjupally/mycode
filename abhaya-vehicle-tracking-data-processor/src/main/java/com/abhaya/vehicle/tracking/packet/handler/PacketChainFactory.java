package com.abhaya.vehicle.tracking.packet.handler;

import java.util.Arrays;
import java.util.List;

public class PacketChainFactory 
{
    private PacketChainFactory() {
    }

    private static class Holder 
    {
    	private static PacketChainFactory instance = new PacketChainFactory();
    }

    public static PacketChainFactory getInstance() {
    	return Holder.instance;
    }

    public PacketHandler createRules(PacketHandler... rules) 
    {

		if (rules.length < 2) {
			throw new IllegalArgumentException("a chain must contain at least two rules");
		}

		List<PacketHandler> listOrdered = Arrays.asList(rules);

		PacketHandler prevRule = listOrdered.get(0);
		for (int i = 1; i < listOrdered.size(); i++) 
		{
			PacketHandler currentRule = listOrdered.get(i);
			prevRule.nextHandler(currentRule);
			prevRule = currentRule;
		}
		return listOrdered.get(0);
	}
}

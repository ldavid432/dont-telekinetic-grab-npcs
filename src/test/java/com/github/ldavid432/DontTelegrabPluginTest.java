package com.github.ldavid432;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DontTelegrabPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DontTelegrabPlugin.class);
		RuneLite.main(args);
	}
}

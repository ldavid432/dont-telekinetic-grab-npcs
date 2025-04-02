package com.github.ldavid432;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("donttelegrabnpcs")
public interface DontTelegrabConfig extends Config
{
	@ConfigItem(
		keyName = "telegrabWhitelist",
		name = "Whitelist",
		description = "Allow certain NPCs to have telekinetic grab cast on them, separated by commas."
	)
	default String whitelistedNpcs()
	{
		return "Maze Guardian";
	}
}

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
		return "Maze Guardian, " +
			// Sailing birds
			"Tern, Osprey, Frigatebird, Albatross, " +
			// Sailing mammals
			"Dolphin, Mogre, Narwhal, Orca, " +
			// Sailing krakens
			"Pygmy kraken, Spined kraken, Armoured kraken, Vampyre kraken, " +
			// Sailing sharks
			"Bull shark, Hammerhead shark, Tiger shark, Great white shark, " +
			// Sailing rays
			"Eagle Ray, Butterfly Ray, Stingray, Manta ray";
	}
}

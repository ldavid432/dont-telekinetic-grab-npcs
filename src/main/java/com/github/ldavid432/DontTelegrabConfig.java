package com.github.ldavid432;

import static com.github.ldavid432.DontTelegrabConfig.GROUP;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(GROUP)
public interface DontTelegrabConfig extends Config
{
	String GROUP = "donttelegrabnpcs";

	String CUSTOM_WHITELIST_KEY = "telegrabWhitelist";
	String VERSION_KEY = "version";

	@ConfigItem(
		keyName = "version",
		name = "",
		description = "",
		hidden = true
	)
	default int getVersion()
	{
		return 0;
	}

	@ConfigSection(
		name = "Whitelist",
		description = "Allow certain NPCs to have telekinetic grab cast on them",
		position = 0
	)
	String whitelistSection = "whitelist";

	@ConfigItem(
		keyName = CUSTOM_WHITELIST_KEY,
		name = "Custom Names",
		description = "<strong>Names</strong> of NPCs to whitelist, separated by commas.",
		section = whitelistSection,
		position = 0
	)
	default String whitelistedNpcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "whitelistSailingCorpses",
		name = "Sailing Creature Corpses",
		description = "Whitelist the corpses of sailing creatures",
		section = whitelistSection,
		position = 1
	)
	default boolean isWhitelistSailingCorpses()
	{
		return true;
	}

	@ConfigItem(
		keyName = "whitelistSailingCrates",
		name = "Sailing Crates",
		description = "Whitelist the lost crates from Sailing random encounters",
		section = whitelistSection,
		position = 2
	)
	default boolean isWhitelistSailingCrates()
	{
		return true;
	}

	@ConfigItem(
		keyName = "whitelistMazeGuardian",
		name = "Maze Guardian",
		description = "Whitelist the Maze Guardian from the Mage Training Arena",
		section = whitelistSection,
		position = 3
	)
	default boolean isWhitelistMazeGuardian()
	{
		return true;
	}

	@ConfigItem(
		keyName = "whitelistKingsRansomGuard",
		name = "King's Random Guard",
		description = "Whitelist the Guard from the King's Ransom quest",
		section = whitelistSection,
		position = 4
	)
	default boolean isWhitelistKingsRansomGuard()
	{
		return true;
	}
}

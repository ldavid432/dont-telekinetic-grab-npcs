package com.github.ldavid432;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.NPC;
import net.runelite.client.util.Text;

@Singleton
public class Whitelist
{
	private final List<Integer> whitelistedIds = new ArrayList<>();
	private final List<String> whitelistedNames = new ArrayList<>();

	@Inject
	DontTelegrabConfig config;

	public void refreshWhiteList()
	{
		clearWhitelist();

		whitelistedNames.addAll(getCustomWhitelist());

		for (WhitelistEntry entry : WhitelistEntry.values())
		{
			if (entry.isEnabled(config))
			{
				whitelistedIds.addAll(entry.ids);
			}
		}
	}

	public void clearWhitelist()
	{
		whitelistedIds.clear();
		whitelistedNames.clear();
	}

	public boolean isInWhitelist(final NPC npc)
	{
		return isNameWhitelisted(npc.getName()) || isIdWhitelisted(npc.getId());
	}

	private boolean isNameWhitelisted(String name)
	{
		return name != null && whitelistedNames.stream()
			.anyMatch(whitelistedName -> Text.removeTags(name).equalsIgnoreCase(whitelistedName));
	}

	private boolean isIdWhitelisted(Integer id)
	{
		return id != null && whitelistedIds.contains(id);
	}

	public List<String> getCustomWhitelist()
	{
		return splitCustomWhitelist(config.whitelistedNpcs());
	}

	public static List<String> splitCustomWhitelist(String list)
	{
		return List.of(list.split(" *, *"));
	}

	public static String joinCustomWhitelist(List<String> list)
	{
		return String.join(", ", list);
	}

	public static final String LEGACY_NPC_CONFIG_NAMES = "Maze Guardian, " +
		// Sailing birds
		"Tern, Osprey, Frigatebird, Albatross, " +
		// Sailing mammals
		"Dolphin, Mogre, Narwhal, Orca, " +
		// Sailing krakens
		"Pygmy kraken, Spined kraken, Armoured kraken, Vampyre kraken, Veiled kraken, " +
		// Sailing sharks
		"Bull shark, Hammerhead shark, Tiger shark, Great white shark, " +
		// Sailing rays
		"Eagle Ray, Butterfly Ray, Stingray, Manta ray, " +
		// Sailing crates
		"Lost wooden crate, Lost oak crate, Lost teak crate, Lost mahogany crate, Lost camphor crate, Lost ironwood crate, " +
		// Bug in original whitelist added these without a comma
		"Manta rayLost wooden crate";

}

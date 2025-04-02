package com.github.ldavid432;

import com.google.inject.Provides;
import java.util.Arrays;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Don't Telegrab NPCs",
	description = "Disables casting telegrab on NPCs",
	tags = {"telekinetic", "grab", "npc", "monster", "no"}
)
public class DontTelegrabPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DontTelegrabConfig config;

	// Based on https://github.com/oohwooh/no-use-players/blob/master/src/main/java/com/oohwooh/NoUsePlayerPlugin.java
	@Subscribe
	public void onClientTick(ClientTick clientTick)
	{
		// The menu is not rebuilt when it is open, so don't swap or else it will
		// repeatedly swap entries
		if (client.getGameState() != GameState.LOGGED_IN || client.isMenuOpen())
		{
			return;
		}

		final Widget selectedWidget = client.getSelectedWidget();
		if (selectedWidget == null)
		{
			return;
		}

		final int spellWidgetId = selectedWidget.getId();
		// Telekinetic grab widget ID: 14286875
		if (spellWidgetId != 14286875 || !client.isWidgetSelected())
		{
			return;
		}

		MenuEntry[] menuEntries = client.getMenu().getMenuEntries();

		MenuEntry[] newEntries = Arrays.stream(menuEntries)
			.filter(e -> {
				if (e.getType() != null && e.getType() == MenuAction.WIDGET_TARGET_ON_NPC)
				{
					return e.getNpc() != null && e.getNpc().getName() != null && isInWhitelist(e.getNpc().getName());
				}
				else
				{
					return true;
				}
			})
			.toArray(MenuEntry[]::new);

		client.getMenu().setMenuEntries(newEntries);
	}

	private boolean isInWhitelist(final String name)
	{
		// Use contains because NPCs can have colors in their name (Maze Guardian is actually: <col=ff9040>Maze Guardian</col>)
		return Arrays.stream(config.whitelistedNpcs().toLowerCase().split(" *, *"))
			.anyMatch((whitelistedName) -> name.toLowerCase().contains(whitelistedName));
	}

	@Provides
	DontTelegrabConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DontTelegrabConfig.class);
	}
}

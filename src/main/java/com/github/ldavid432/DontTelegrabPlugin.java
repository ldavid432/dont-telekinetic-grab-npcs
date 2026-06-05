package com.github.ldavid432;

import static com.github.ldavid432.DontTelegrabConfig.VERSION_KEY;
import static com.github.ldavid432.DontTelegrabConfig.CUSTOM_WHITELIST_KEY;
import static com.github.ldavid432.Whitelist.joinCustomWhitelist;
import static com.github.ldavid432.Whitelist.splitCustomWhitelist;
import com.google.inject.Provides;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Menu;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.ColorUtil;

@Slf4j
@PluginDescriptor(
	name = "Don't Telegrab NPCs",
	description = "Disables casting telegrab on NPCs",
	tags = {"telekinetic", "grab", "npc", "monster", "no", "telegrab", "block", "sailing"}
)
public class DontTelegrabPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DontTelegrabConfig config;

	@Inject
	private Whitelist whitelist;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Override
	protected void startUp() throws Exception
	{
		if (config.getVersion() < 1)
		{
			if (client.getGameState() != GameState.LOGGED_IN)
			{
				// Migrate from custom list only to list + configs - basically just remove anything in a config from the list
				List<String> customWhitelist = whitelist.getCustomWhitelist();
				List<String> legacyWhitelist = splitCustomWhitelist(Whitelist.LEGACY_NPC_CONFIG_NAMES);

				List<String> newWhitelist = customWhitelist.stream()
					.filter(npc -> legacyWhitelist.stream().noneMatch(entry -> entry.equalsIgnoreCase(npc)))
					.collect(Collectors.toList());

				configManager.setConfiguration(DontTelegrabConfig.GROUP, CUSTOM_WHITELIST_KEY, joinCustomWhitelist(newWhitelist));

				chatMessageManager.queue(
					QueuedMessage.builder()
						.type(ChatMessageType.CONSOLE)
						.runeLiteFormattedMessage(
							ColorUtil.wrapWithColorTag("Don't Telegrab NPCs has been updated!<br>", Color.RED) +
								ColorUtil.wrapWithColorTag("* Pre-populated whitelist items have been moved into their own config toggles.<br>", Color.RED) +
								ColorUtil.wrapWithColorTag("* Brings newly whitelisted NPCs to users who installed the plugin before they were added.", Color.RED)
						).build()
				);
			}

			configManager.setConfiguration(DontTelegrabConfig.GROUP, VERSION_KEY, 1);
		}

		whitelist.refreshWhiteList();
	}

	// Based on https://github.com/oohwooh/no-use-players/blob/master/src/main/java/com/oohwooh/NoUsePlayerPlugin.java
	@Subscribe
	public void onClientTick(ClientTick clientTick)
	{
		if (client.getGameState() != GameState.LOGGED_IN || client.isMenuOpen() || !client.isWidgetSelected())
		{
			return;
		}

		final Widget selectedWidget = client.getSelectedWidget();
		if (selectedWidget == null || selectedWidget.getId() != InterfaceID.MagicSpellbook.TELEGRAB)
		{
			return;
		}

		Menu menu = client.getMenu();
		MenuEntry[] menuEntries = menu.getMenuEntries();

		MenuEntry[] newEntries = Arrays.stream(menuEntries)
			.filter(entry -> entry.getType() != MenuAction.WIDGET_TARGET_ON_NPC || (entry.getNpc() != null && whitelist.isInWhitelist(entry.getNpc())))
			.toArray(MenuEntry[]::new);

		menu.setMenuEntries(newEntries);
	}

	@Provides
	DontTelegrabConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DontTelegrabConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (DontTelegrabConfig.GROUP.equals(event.getGroup()))
		{
			whitelist.refreshWhiteList();
		}
	}

}

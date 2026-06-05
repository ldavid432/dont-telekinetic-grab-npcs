package com.github.ldavid432;

import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import net.runelite.api.gameval.NpcID;

@AllArgsConstructor
public enum WhitelistEntry
{
	SAILING_CORPSES(
		DontTelegrabConfig::isWhitelistSailingCorpses,
		List.of(
			// Birds
			NpcID.SAILING_TERN_DEAD, NpcID.SAILING_OSPREY_DEAD, NpcID.SAILING_FRIGATEBIRD_DEAD, NpcID.SAILING_ALBATROSS_DEAD,
			// Mammals
			NpcID.SAILING_DOLPHIN_DEAD, NpcID.SAILING_SEA_MOGRE_DEAD, NpcID.SAILING_SEA_MOGRE_DEAD_F, NpcID.SAILING_NARWHAL_DEAD, NpcID.SAILING_ORCA_DEAD,
			// Krakens
			NpcID.SAILING_PYGMY_KRAKEN_DEAD, NpcID.SAILING_SPINED_KRAKEN_DEAD, NpcID.SAILING_ARMOURED_KRAKEN_DEAD, NpcID.SAILING_VAMPYRE_KRAKEN_DEAD, NpcID.SAILING_VEILED_KRAKEN_DEAD,
			// Sharks
			NpcID.SAILING_BULL_SHARK_DEAD, NpcID.SAILING_HAMMERHEAD_SHARK_DEAD, NpcID.SAILING_TIGER_SHARK_DEAD, NpcID.SAILING_GREAT_WHITE_SHARK_DEAD,
			// Rays
			NpcID.SAILING_EAGLE_RAY_DEAD, NpcID.SAILING_BUTTERFLY_RAY_DEAD, NpcID.SAILING_STINGRAY_DEAD, NpcID.SAILING_MANTA_RAY_DEAD
		)
	),
	SAILING_CRATES(
		DontTelegrabConfig::isWhitelistSailingCrates,
		List.of(
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_WOOD,
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_OAK,
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_TEAK,
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_MAHOGANY,
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_CAMPHOR,
			NpcID.SAILING_CHANCE_ENCOUNTER_LOST_GOODS_IRONWOOD
		)
	),
	MAZE_GUARDIAN(
		DontTelegrabConfig::isWhitelistMazeGuardian,
		List.of(NpcID.MAGICTRAINING_GUARD_MAZE_INCOMPLETE)
	),
	KINGS_RANSOM_GUARD(
		DontTelegrabConfig::isWhitelistKingsRansomGuard,
		List.of(NpcID.KR_KEEP_GUARD_HAIR)
	),
	;

	final Function<DontTelegrabConfig, Boolean> isEnabled;
	final List<Integer> ids;

	public boolean isEnabled(DontTelegrabConfig config)
	{
		return isEnabled.apply(config);
	}
}

package se.fredsfursten.plugintools;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Misc {
	private static int doDebugPrint = -1;
	private static JavaPlugin _plugin;

	public static Block getFirstBlockOfMaterial(Material material, Location location, int maxDistance) {
		int x1 = location.getBlockX(); 
		int y1 = location.getBlockY();
		int z1 = location.getBlockZ();

		World world = location.getWorld();

		for (int distance = 0; distance < maxDistance; distance++) {
			for (int xPoint = x1-distance; xPoint <= x1+distance; xPoint++) {
				int currentDistance = Math.abs(xPoint-x1);
				boolean okDistanceX = (currentDistance == distance);
				for (int yPoint = y1-distance; yPoint <= y1+distance; yPoint++) {
					currentDistance = Math.abs(yPoint-y1);
					boolean okDistanceY = (currentDistance == distance);
					for (int zPoint = z1-distance; zPoint <= z1+distance; zPoint++) {
						currentDistance = Math.abs(zPoint-z1);
						boolean okDistanceZ = (currentDistance == distance);
						if (!okDistanceX && !okDistanceY && !okDistanceZ) continue;
						Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
						if (currentBlock.getType() == material) return currentBlock;
					}
				}
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayerFromString(String playerIdOrName) {
		Player player = null;
		try {
			UUID id = UUID.fromString(playerIdOrName);
			player = Bukkit.getPlayer(id);
		} catch (Exception e) {
		}
		if (player == null) player = Bukkit.getPlayer(playerIdOrName);
		return player;
	}

	public static void enable(JavaPlugin plugin)
	{
		_plugin = plugin;
	}

	static boolean recursive = false;

	static boolean shouldDebugPrint() {
		if (recursive) return true;
		try {
			recursive = true;
			if (doDebugPrint < 0) {
				if (_plugin == null) {
					Misc.warning("Misc has not been enabled.");
					return true;
				}
				PluginConfig config = PluginConfig.get(_plugin);
				doDebugPrint = config.getInt("DoDebugPrint", 0);
			}
			return doDebugPrint > 0;
		} finally {
			recursive = false;
		}
	}

	@Deprecated
	public static void executeCommand(String command)
	{
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
	}

	public static long secondsToTicks(double seconds)
	{
		return Math.round(20*seconds);
	}

	public static double ticksToSeconds(long ticks)
	{
		return ticks/20.0;
	}

	public static void consolePrintF(String format, Object... args) {
		try {
			if (System.console() != null) System.console().printf(format, args);
		} catch (Exception ex) {
			if (System.console() != null) System.console().printf("Wrong format? \"%s\", %d arguments: %s", format, args.length, ex.getMessage());
		}
	}

	public static void debugInfo(String format, Object... args) 
	{
		if (shouldDebugPrint()) info(format, args);
	}

	public static void info(String format, Object... args) 
	{
		try {
			Bukkit.getLogger().info(String.format(format, args));
		} catch (Exception e) {
			Bukkit.getLogger().warning(String.format("Wrong format? \"%s\", %d arguments: %s", format, args.length, e.getMessage()));
		}
	}

	public static void warning(String format, Object... args) 
	{
		try {
			Bukkit.getLogger().warning(String.format(format, args));
		} catch (Exception e) {
			Misc.consolePrintF(format, args);		}
	}

	public static void error(String format, Object... args) 
	{
		warning(format, args);
	}

	public static void makeSureParentDirectoryExists(File file){
		File directory = file.getParentFile();
		if (!directory.exists())
		{
			directory.mkdirs();
		}
	}
}

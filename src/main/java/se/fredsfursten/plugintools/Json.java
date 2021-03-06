package se.fredsfursten.plugintools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Deprecated
public class Json {
	@SuppressWarnings("unchecked")
	public static JSONObject fromLocation(Location location, boolean withWorld)
	{
		JSONObject json = new JSONObject();
		if (withWorld) json.put("world", fromWorld(location.getWorld()));
		json.put("x", location.getX());
		json.put("y", location.getY());
		json.put("z", location.getZ());
		json.put("yaw", location.getYaw());
		json.put("pitch", location.getPitch());
		return json;
	}

	public static Location toLocation(JSONObject json, World world)
	{
		if (world == null) {
			world = toWorld((JSONObject) json.get("world"));
		}
		double x = (double) json.get("x");
		double y = (double) json.get("y");
		double z = (double) json.get("z");
		float yaw = (float) (double) json.get("yaw");
		float pitch = (float) (double) json.get("pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject fromBlock(Block block, boolean withWorld)
	{
		JSONObject json = new JSONObject();
		if (withWorld) json.put("world", fromWorld(block.getWorld()));
		json.put("x", block.getX());
		json.put("y", block.getY());
		json.put("z", block.getZ());
		return json;
	}

	public static Block toBlock(JSONObject json, World world)
	{
		if (world == null) {
			world = toWorld((JSONObject) json.get("world"));
		}
		int x = (int) json.get("x");
		int y = (int) json.get("y");
		int z = (int) json.get("z");
		return world.getBlockAt(x, y, z);
	}

	@SuppressWarnings("unchecked")
	public static JSONObject fromPlayer(Player player)
	{
		JSONObject json = new JSONObject();
		json.put("id", player.getUniqueId().toString());
		json.put("name", player.getName());
		return json;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject fromPlayer(UUID id, String name)
	{
		JSONObject json = new JSONObject();
		json.put("id", id.toString());
		json.put("name", name);
		return json;
	}

	@SuppressWarnings("deprecation")
	public static Player toPlayer(JSONObject json)
	{
		UUID id = UUID.fromString((String) json.get("id"));
		Player player = Bukkit.getPlayer(id);
		if (player == null) {
			String name = (String) json.get("name");
			player = Bukkit.getPlayer(name);				
		}
		return player;
	}

	public static UUID toPlayerId(JSONObject json)
	{
		return UUID.fromString((String) json.get("id"));
	}

	public static String toPlayerName(JSONObject json)
	{
		return (String) json.get("name");
	}

	@SuppressWarnings("unchecked")
	public static JSONObject fromWorld(World world)
	{
		JSONObject json = new JSONObject();
		json.put("id", world.getUID().toString());
		json.put("name", world.getName());
		return json;
	}

	public static World toWorld(JSONObject json)
	{
		UUID id = UUID.fromString((String) json.get("id"));
		World world = Bukkit.getWorld(id);
		if (world == null) {
			String name = (String) json.get("name");
			world = Bukkit.getWorld(name);				
		}
		return world;
	}

	public static UUID toWorldId(JSONObject json)
	{
		return (UUID) UUID.fromString((String) json.get("id"));
	}

	public static String toWorldName(JSONObject json)
	{
		return (String) json.get("name");
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public static JSONObject fromBody(String name, int version, JSONArray payload)
	{
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("version", version);
		json.put("payload", payload);
		return json;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject fromBody(String name, int version, Object payload)
	{
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("version", version);
		json.put("payload", payload);
		return json;
	}

	public static Object toBodyPayload(JSONObject json)
	{
		return json.get("payload");
	}

	public static String toBodyName(JSONObject json)
	{
		return (String) json.get("name");
	}

	public static int toBodyVersion(JSONObject json)
	{
		return (int) json.get("version");
	}

	public static void save(File file, JSONObject data) {
		Misc.makeSureParentDirectoryExists(file);
		try {
			Writer writer = new FileWriter(file);
			data.writeJSONString(writer);
			writer.close();
			Misc.info("Saved \"%s\".", file.getName());
		} catch (IOException e) {
			Misc.info("Can't create file \"%s\" for save: %s", file.getName(), e.getMessage());
		} catch (Exception e) {
			Misc.warning("Failed to save file \"%s\": %s", file.getName(), e.getMessage());
		}
	}

	public static JSONObject load(File file) {
		Misc.makeSureParentDirectoryExists(file);
		JSONObject data = null;
		try {
			Reader reader = new FileReader(file);
			Object o = JSONValue.parseWithException(reader);
			if (o == null) {
				Misc.debugInfo("Load, phase parse returned empty.");
			}
			data = (JSONObject) o;
			if (data == null) {
				Misc.debugInfo("Load, phase cast returned empty.");
			}
			reader.close();
			if (data == null) {
				Misc.warning("Loading \"%s\" resulted in null.", file.getName());
			} else {
				Misc.info("Loaded \"%s\".", file.getName());
			}
		} catch (FileNotFoundException e) {
			Misc.info("Can't open file \"%s\" for load: %s", file.getName(), e.getMessage());
		} catch (Exception e) {
			Misc.warning("Failed to load file \"%s\": %s", file.getName(), e.getMessage());
		}
		return data;
	}

	public static void delayedSave(File file, JSONObject data, JavaPlugin plugin) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				save(file, data);
			}
		});		
	}

	@Deprecated
	public static JSONArray loadDataArray(File file) {
		JSONArray jsonArray = null;
		try {
			Reader reader = new FileReader(file);
			Object o = JSONValue.parseWithException(reader);
			jsonArray = (JSONArray) o;
			reader.close();
		} catch (FileNotFoundException e) {
			Misc.info("Can't open file \"%s\" for load.", file.getName());
			return null;
		} catch (Exception e) {
			Misc.warning("Failed to load file \"%s\".", file.getName());
			e.printStackTrace();
			return null;
		}
		return jsonArray;
	}

	@Deprecated
	public static int saveData(File file, JSONArray data) {
		try {
			Writer writer = new FileWriter(file);
			data.writeJSONString(writer);
			writer.close();
		} catch (IOException e) {
			Misc.info("Can't create file \"%s\" for save.", file.getName());
		} catch (Exception e) {
			Misc.warning("Failed to save file \"%s\".", file.getName());
			e.printStackTrace();
			return 0;
		}
		return data.size();
	}
}

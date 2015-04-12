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
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Json {
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

	public static JSONObject fromPlayer(Player player)
	{
		JSONObject json = new JSONObject();
		json.put("id", player.getUniqueId().toString());
		json.put("name", player.getName());
		return json;
	}

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
}

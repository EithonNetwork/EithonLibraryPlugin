package se.fredsfursten.plugintools;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

public class PlayerCollection<T extends IJson<T> & IUuidAndName> implements Iterable<T>, IJson<PlayerCollection<T>>, Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<UUID, T> playerInfo = null;
	private T _instance;

	public PlayerCollection(IFactory<T> factory) {
		this.playerInfo = new HashMap<UUID, T>();
		this._instance = factory.factory();
	}
	
	public PlayerCollection(T instance) {
		this.playerInfo = new HashMap<UUID, T>();
		this._instance = instance;
	}
	
	public void put(Player player, T info) {
		UUID id = player.getUniqueId();
		put(id, info);
	}
	
	public void put(UUID playerId, T info) {
		this.playerInfo.put(playerId, info);
	}
	
	public T get(Player player) {
		UUID id = player.getUniqueId();
		return get(id);
	}
	
	public T get(UUID playerId) {
		return this.playerInfo.get(playerId);
	}
	
	public boolean hasInformation(Player player) {
		UUID id = player.getUniqueId();
		return hasInformation(id);
	}
	
	public boolean hasInformation(UUID playerId) {
		return this.playerInfo.containsKey(playerId);
	}
	
	public void remove(Player player) {
		UUID id = player.getUniqueId();
		remove(id);
	}
	
	public void remove(UUID playerId) {
		this.playerInfo.remove(playerId);
	}
	
	public Set<UUID> getPlayers() {
		return this.playerInfo.keySet();
	}
	
	public Iterator<T> iterator() {
		return this.playerInfo.values().iterator();
	}
	
	@SuppressWarnings("unchecked")
	public Object toJson() {
		JSONArray json = new JSONArray();
		for (T value : this.playerInfo.values()) {
			if (!(value instanceof IJson<?>)) {
				Misc.error("%s must implement interface J", value.toString());
				return null;
			}
			IJson<T> info = (IJson<T>) value;
			json.add(info.toJson());
		}
		return json;
	}
	
	@Override
	public void fromJson(Object json) {
		JSONArray jsonArray = (JSONArray) json;
		HashMap<UUID, T> playerInfo = new HashMap<UUID, T>();
		for (Object o : jsonArray) {
			T info = this._instance.factory();
			info.fromJson(o);
			playerInfo.put(info.getUniqueId(), info);
		}
		this.playerInfo = playerInfo;
	}

	@Override
	public PlayerCollection<T> factory() {
		return new PlayerCollection<T>(this._instance);
	}
}
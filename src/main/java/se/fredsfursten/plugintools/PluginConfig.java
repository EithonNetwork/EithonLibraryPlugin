package se.fredsfursten.plugintools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfig {
	private File configFile;
	private FileConfiguration config;
	static HashMap<String, PluginConfig> instances = new HashMap<String, PluginConfig>();
	private int doDebugPrint;
	private JavaPlugin _plugin;

	private PluginConfig(JavaPlugin plugin)
	{
		this._plugin = plugin;
		initialize(this._plugin, "config.yml");
	}
	
	@Deprecated
	public void enable()
	{
	}
	
	@Deprecated
	public void disable()
	{
	}
	
	public static PluginConfig get(JavaPlugin plugin) {
		PluginConfig config = instances.get(plugin.getName());
		if (config == null) {
			config = new PluginConfig(plugin);
			instances.put(plugin.getName(), config);
		}
		return config;
	}
	
	private void initialize(JavaPlugin plugin, String fileName) {
		this.configFile = initializeConfigFile(plugin, fileName);
		this.config = new YamlConfiguration();
		load();
		this.doDebugPrint = this.config.getInt("DoDebugPrint");
	}
	
	public ConfigurableFormat getConfigurableFormat(String path, int parameters, String defaultValue) {
		return new ConfigurableFormat(path, parameters, defaultValue, this);
	}
	
	public double getDouble(String path, double defaultValue)
	{
		double result;
		try {
			result = this.config.getDouble(path, defaultValue);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
			result = defaultValue;
		}
		Misc.debugInfo("Configuration \"%s\" = %.2f" , path, result);
		return result;
	}
	
	public int getInt(String path, int defaultValue)
	{
		int result;
		try {
			result = this.config.getInt(path, defaultValue);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
			result = defaultValue;
		}
		Misc.debugInfo("Configuration \"%s\" = %d" , path, result);
		return result;
	}
	
	public List<Integer> getIntegerList(String path)
	{
		List<Integer> result = null;
		try {
			result = this.config.getIntegerList(path);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
		}
		String s = "";
		boolean first = true;
		for (Integer integer : result) {
			if (first) first = false;
			else s += ", ";
			s += integer.toString();
		}
		Misc.debugInfo("Configuration \"%s\" = [%s]" , path, s);
		return result;
	}
	
	public String getString(String path, String defaultValue)
	{
		String result;
		try {
			result = this.config.getString(path, defaultValue);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
			result = defaultValue;
		}
		Misc.debugInfo("Configuration \"%s\" = %s" , path, result);
		return result;
	}
	
	public List<String> getStringList(String path)
	{
		List<String> result = null;
		try {
			result = this.config.getStringList(path);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
		}
		String s = "";
		boolean first = true;
		for (String string : result) {
			if (first) first = false;
			else s += ", ";
			s += string;
		}
		Misc.debugInfo("Configuration \"%s\" = [%s]" , path, s);
		return result;
	}
	
	public Object get(String path, Object defaultValue)
	{
		Object result;
		try {
			result = this.config.get(path, defaultValue);
		} catch (Exception ex) {
			Misc.warning("Failed to read configuration \"%s\", will use default value.", path);
			ex.printStackTrace();
			result = defaultValue;
		}
		Misc.debugInfo("Configuration \"%s\" = %s" , path, result.toString());
		return result;
	}
	
	@Deprecated
	boolean shouldDebugPrint() {
		return this.doDebugPrint > 0;
	}

	private File initializeConfigFile(JavaPlugin plugin, String fileName) {
		File file = new File(plugin.getDataFolder(), fileName);
		Misc.makeSureParentDirectoryExists(file);
		if(!file.exists()) {
			copy(plugin.getResource(fileName), file);
		}

		return file;
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public FileConfiguration getFileConfiguration()
	{
		return this.config;
	}

	@Deprecated
	public void debugInfo(String format, Object... args) 
	{
		if (this.doDebugPrint == 0) return;
		try {
			Bukkit.getLogger().info(String.format(format, args));
		} catch (Exception ex) {
			Bukkit.getLogger().warning(String.format("Wrong format? \"%s\", %d arguments: %s", format, args.length, ex.getMessage()));
			ex.printStackTrace();
		}
	}

	public void load()
	{
		try {
			this.config.load(this.configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
	    try {
	        this.config.save(this.configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

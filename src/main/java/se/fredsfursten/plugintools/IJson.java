package se.fredsfursten.plugintools;

public interface IJson<T> extends IFactory<T> {
	Object toJson();
	void fromJson(Object json);
}

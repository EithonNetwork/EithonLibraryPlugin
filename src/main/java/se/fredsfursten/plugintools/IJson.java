package se.fredsfursten.plugintools;

@Deprecated
public interface IJson<T> extends IFactory<T> {
	Object toJson();
	void fromJson(Object json);
}

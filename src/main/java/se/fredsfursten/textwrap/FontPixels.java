package se.fredsfursten.textwrap;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class FontPixels
{
	private static FontPixels singleton = null;
	private static HashMap<String, Integer> pixelWidths = new HashMap<String, Integer>();

	private FontPixels() {
		initializeHashMap();
	}

	public static FontPixels get()
	{
		if (singleton == null) {
			singleton = new FontPixels();
		}
		return singleton;
	}

	public int pixelWidth(char c)
	{
		Integer width = pixelWidths.get(c);
		if (width == null) {
			Bukkit.getLogger().warning(String.format("No pixel width for character '%s'", c));
			return 0;
		}
		return width;
	}

	public int pixelWidth(StringBuilder s) {
		return pixelWidth(s.toString());
	}

	public int pixelWidth(String s) {
		int length = 0;
		for(char c : s.toCharArray()){
			length += pixelWidth(c);
		}
		return length;
	}

	public String breakAfterPixels(StringBuilder s, int lengthInPixels, StringBuilder rest) {
		return breakAfterPixels(s.toString(), lengthInPixels, rest);
	}	

	public String breakAfterPixels(String s, int lengthInPixels, StringBuilder rest) {
		int length = 0;
		String first = "";
		rest.setLength(0);
		char[] charArray = s.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			length += pixelWidth(c);
			if (length > lengthInPixels) {
				first = s.substring(0, i-1);
				rest.append(s.substring(i));
			}
		}
		return first;
	}

	private void initializeHashMap() 
	{	
		pixelWidths.put ("â˜º", new Integer(9));
		pixelWidths.put ("â˜»", new Integer(9));
		pixelWidths.put ("â™¥", new Integer(8));
		pixelWidths.put ("â™¦", new Integer(8));
		pixelWidths.put ("â™£", new Integer(8));
		pixelWidths.put ("â™ ", new Integer(8));
		pixelWidths.put ("â€¢", new Integer(5));
		pixelWidths.put ("â—˜", new Integer(9));
		pixelWidths.put ("â—‹", new Integer(7));
		pixelWidths.put ("â—™", new Integer(9));
		pixelWidths.put ("â™‚", new Integer(9));
		pixelWidths.put ("â™€", new Integer(7));
		pixelWidths.put ("â™ª", new Integer(9));
		pixelWidths.put ("â™«", new Integer(9));
		pixelWidths.put ("â˜¼", new Integer(9));
		pixelWidths.put ("â–º", new Integer(8));
		pixelWidths.put ("â—„", new Integer(8));
		pixelWidths.put ("â†•", new Integer(7));
		pixelWidths.put ("â€¼", new Integer(7));
		pixelWidths.put ("Â¶", new Integer(9));
		pixelWidths.put ("Â§", new Integer(7));
		pixelWidths.put ("â–¬", new Integer(7));
		pixelWidths.put ("â†¨", new Integer(9));
		pixelWidths.put ("â†‘", new Integer(7));
		pixelWidths.put ("â†“", new Integer(7));
		pixelWidths.put ("â†’", new Integer(8));
		pixelWidths.put ("â†�", new Integer(8));
		pixelWidths.put ("âˆŸ", new Integer(8));
		pixelWidths.put ("â†”", new Integer(9));
		pixelWidths.put ("â–²", new Integer(9));
		pixelWidths.put ("â–¼", new Integer(9));
		pixelWidths.put (" ", new Integer(5));
		pixelWidths.put ("!", new Integer(2));
		pixelWidths.put ("\"", new Integer(5));
		pixelWidths.put ("#", new Integer(6));
		pixelWidths.put ("$", new Integer(6));
		pixelWidths.put ("%", new Integer(6));
		pixelWidths.put ("&", new Integer(6));
		pixelWidths.put ("'", new Integer(3));
		pixelWidths.put ("(", new Integer(5));
		pixelWidths.put (")", new Integer(5));
		pixelWidths.put ("*", new Integer(5));
		pixelWidths.put ("+", new Integer(6));
		pixelWidths.put (",", new Integer(2));
		pixelWidths.put ("-", new Integer(6));
		pixelWidths.put (".", new Integer(2));
		pixelWidths.put ("/", new Integer(6));
		pixelWidths.put ("0", new Integer(6));
		pixelWidths.put ("1", new Integer(6));
		pixelWidths.put ("2", new Integer(6));
		pixelWidths.put ("3", new Integer(6));
		pixelWidths.put ("4", new Integer(6));
		pixelWidths.put ("5", new Integer(6));
		pixelWidths.put ("6", new Integer(6));
		pixelWidths.put ("7", new Integer(6));
		pixelWidths.put ("8", new Integer(6));
		pixelWidths.put ("9", new Integer(6));
		pixelWidths.put (":", new Integer(2));
		pixelWidths.put (";", new Integer(2));
		pixelWidths.put ("<", new Integer(5));
		pixelWidths.put ("=", new Integer(6));
		pixelWidths.put (">", new Integer(5));
		pixelWidths.put ("?", new Integer(6));
		pixelWidths.put ("@", new Integer(7));
		pixelWidths.put ("A", new Integer(6));
		pixelWidths.put ("B", new Integer(6));
		pixelWidths.put ("C", new Integer(6));
		pixelWidths.put ("D", new Integer(6));
		pixelWidths.put ("E", new Integer(6));
		pixelWidths.put ("F", new Integer(6));
		pixelWidths.put ("G", new Integer(6));
		pixelWidths.put ("H", new Integer(6));
		pixelWidths.put ("I", new Integer(4));
		pixelWidths.put ("J", new Integer(6));
		pixelWidths.put ("K", new Integer(6));
		pixelWidths.put ("L", new Integer(6));
		pixelWidths.put ("M", new Integer(6));
		pixelWidths.put ("N", new Integer(6));
		pixelWidths.put ("O", new Integer(6));
		pixelWidths.put ("P", new Integer(6));
		pixelWidths.put ("Q", new Integer(6));
		pixelWidths.put ("R", new Integer(6));
		pixelWidths.put ("S", new Integer(6));
		pixelWidths.put ("T", new Integer(6));
		pixelWidths.put ("U", new Integer(6));
		pixelWidths.put ("V", new Integer(6));
		pixelWidths.put ("W", new Integer(6));
		pixelWidths.put ("X", new Integer(6));
		pixelWidths.put ("Y", new Integer(6));
		pixelWidths.put ("Z", new Integer(6));
		pixelWidths.put ("[", new Integer(4));
		pixelWidths.put ("\\", new Integer(6));
		pixelWidths.put ("]", new Integer(4));
		pixelWidths.put ("^", new Integer(6));
		pixelWidths.put ("_", new Integer(6));
		pixelWidths.put ("`", new Integer(3));
		pixelWidths.put ("a", new Integer(6));
		pixelWidths.put ("b", new Integer(6));
		pixelWidths.put ("c", new Integer(6));
		pixelWidths.put ("d", new Integer(6));
		pixelWidths.put ("e", new Integer(6));
		pixelWidths.put ("f", new Integer(5));
		pixelWidths.put ("g", new Integer(6));
		pixelWidths.put ("h", new Integer(6));
		pixelWidths.put ("i", new Integer(2));
		pixelWidths.put ("j", new Integer(6));
		pixelWidths.put ("k", new Integer(5));
		pixelWidths.put ("l", new Integer(3));
		pixelWidths.put ("m", new Integer(6));
		pixelWidths.put ("n", new Integer(6));
		pixelWidths.put ("o", new Integer(6));
		pixelWidths.put ("p", new Integer(6));
		pixelWidths.put ("q", new Integer(6));
		pixelWidths.put ("r", new Integer(6));
		pixelWidths.put ("s", new Integer(6));
		pixelWidths.put ("t", new Integer(4));
		pixelWidths.put ("u", new Integer(6));
		pixelWidths.put ("v", new Integer(6));
		pixelWidths.put ("w", new Integer(6));
		pixelWidths.put ("x", new Integer(6));
		pixelWidths.put ("y", new Integer(6));
		pixelWidths.put ("z", new Integer(6));
		pixelWidths.put ("{", new Integer(5));
		pixelWidths.put ("|", new Integer(2));
		pixelWidths.put ("}", new Integer(5));
		pixelWidths.put ("~", new Integer(7));
		pixelWidths.put ("âŒ‚", new Integer(6));
		pixelWidths.put ("Ã‡", new Integer(6));
		pixelWidths.put ("Ã¼", new Integer(6));
		pixelWidths.put ("Ã©", new Integer(6));
		pixelWidths.put ("Ã¢", new Integer(6));
		pixelWidths.put ("Ã¤", new Integer(6));
		pixelWidths.put ("Ã ", new Integer(6));
		pixelWidths.put ("Ã¥", new Integer(6));
		pixelWidths.put ("Ã§", new Integer(6));
		pixelWidths.put ("Ãª", new Integer(6));
		pixelWidths.put ("Ã«", new Integer(6));
		pixelWidths.put ("Ã¨", new Integer(6));
		pixelWidths.put ("Ã¯", new Integer(4));
		pixelWidths.put ("Ã®", new Integer(6));
		pixelWidths.put ("Ã¬", new Integer(3));
		pixelWidths.put ("Ã„", new Integer(6));
		pixelWidths.put ("Ã…", new Integer(6));
		pixelWidths.put ("Ã‰", new Integer(6));
		pixelWidths.put ("Ã¦", new Integer(6));
		pixelWidths.put ("Ã†", new Integer(6));
		pixelWidths.put ("Ã´", new Integer(6));
		pixelWidths.put ("Ã¶", new Integer(6));
		pixelWidths.put ("Ã²", new Integer(6));
		pixelWidths.put ("Ã»", new Integer(6));
		pixelWidths.put ("Ã¹", new Integer(6));
		pixelWidths.put ("Ã¿", new Integer(6));
		pixelWidths.put ("Ã–", new Integer(6));
		pixelWidths.put ("Ãœ", new Integer(6));
		pixelWidths.put ("Â¢", new Integer(6));
		pixelWidths.put ("Â£", new Integer(6));
		pixelWidths.put ("Â¥", new Integer(6));
		pixelWidths.put ("â‚§", new Integer(4));
		pixelWidths.put ("Æ’", new Integer(6));
		pixelWidths.put ("Ã¡", new Integer(6));
		pixelWidths.put ("Ã­", new Integer(3));
		pixelWidths.put ("Ã³", new Integer(6));
		pixelWidths.put ("Ãº", new Integer(6));
		pixelWidths.put ("Ã±", new Integer(6));
		pixelWidths.put ("Ã‘", new Integer(6));
		pixelWidths.put ("Âª", new Integer(6));
		pixelWidths.put ("Âº", new Integer(6));
		pixelWidths.put ("Â¿", new Integer(6));
		pixelWidths.put ("âŒ�", new Integer(7));
		pixelWidths.put ("Â¬", new Integer(6));
		pixelWidths.put ("Â½", new Integer(6));
		pixelWidths.put ("Â¼", new Integer(6));
		pixelWidths.put ("Â¡", new Integer(2));
		pixelWidths.put ("Â«", new Integer(6));
		pixelWidths.put ("Â»", new Integer(6));
		pixelWidths.put ("â–‘", new Integer(9));
		pixelWidths.put ("â–’", new Integer(9));
		pixelWidths.put ("â–“", new Integer(9));
		pixelWidths.put ("â”‚", new Integer(3));
		pixelWidths.put ("â”¤", new Integer(6));
		pixelWidths.put ("â•¡", new Integer(6));
		pixelWidths.put ("â•¢", new Integer(8));
		pixelWidths.put ("â•–", new Integer(8));
		pixelWidths.put ("â••", new Integer(6));
		pixelWidths.put ("â•£", new Integer(8));
		pixelWidths.put ("â•‘", new Integer(6));
		pixelWidths.put ("â•—", new Integer(8));
		pixelWidths.put ("â•�", new Integer(8));
		pixelWidths.put ("â•œ", new Integer(9));
		pixelWidths.put ("â•›", new Integer(9));
		pixelWidths.put ("â”�", new Integer(9));
		pixelWidths.put ("â””", new Integer(6));
		pixelWidths.put ("â”´", new Integer(9));
		pixelWidths.put ("â”¬", new Integer(9));
		pixelWidths.put ("â”œ", new Integer(6));
		pixelWidths.put ("â”€", new Integer(9));
		pixelWidths.put ("â”¼", new Integer(9));
		pixelWidths.put ("â•ž", new Integer(5));
		pixelWidths.put ("â•Ÿ", new Integer(7));
		pixelWidths.put ("â•š", new Integer(7));
		pixelWidths.put ("â•”", new Integer(7));
		pixelWidths.put ("â•©", new Integer(9));
		pixelWidths.put ("â•¦", new Integer(9));
		pixelWidths.put ("â• ", new Integer(7));
		pixelWidths.put ("â•�", new Integer(9));
		pixelWidths.put ("â•¬", new Integer(9));
		pixelWidths.put ("â•§", new Integer(9));
		pixelWidths.put ("â•¨", new Integer(9));
		pixelWidths.put ("â•¤", new Integer(9));
		pixelWidths.put ("â•¥", new Integer(9));
		pixelWidths.put ("â•™", new Integer(7));
		pixelWidths.put ("â•˜", new Integer(6));
		pixelWidths.put ("â•’", new Integer(6));
		pixelWidths.put ("â•“", new Integer(7));
		pixelWidths.put ("â•«", new Integer(9));
		pixelWidths.put ("â•ª", new Integer(9));
		pixelWidths.put ("â”˜", new Integer(7));
		pixelWidths.put ("â”Œ", new Integer(6));
		pixelWidths.put ("â–ˆ", new Integer(9));
		pixelWidths.put ("â–„", new Integer(9));
		pixelWidths.put ("â–Œ", new Integer(5));
		pixelWidths.put ("â–�", new Integer(5));
		pixelWidths.put ("â–€", new Integer(9));
		pixelWidths.put ("Î±", new Integer(8));
		pixelWidths.put ("ÃŸ", new Integer(7));
		pixelWidths.put ("Î“", new Integer(7));
		pixelWidths.put ("Ï€", new Integer(8));
		pixelWidths.put ("Î£", new Integer(7));
		pixelWidths.put ("Ïƒ", new Integer(8));
		pixelWidths.put ("Âµ", new Integer(7));
		pixelWidths.put ("Ï„", new Integer(8));
		pixelWidths.put ("Î¦", new Integer(7));
		pixelWidths.put ("Î˜", new Integer(8));
		pixelWidths.put ("Î©", new Integer(8));
		pixelWidths.put ("Î´", new Integer(7));
		pixelWidths.put ("âˆž", new Integer(9));
		pixelWidths.put ("Ï†", new Integer(9));
		pixelWidths.put ("Îµ", new Integer(6));
		pixelWidths.put ("âˆ©", new Integer(7));
		pixelWidths.put ("â‰¡", new Integer(7));
		pixelWidths.put ("Â±", new Integer(7));
		pixelWidths.put ("â‰¥", new Integer(7));
		pixelWidths.put ("â‰¤", new Integer(7));
		pixelWidths.put ("âŒ ", new Integer(6));
		pixelWidths.put ("âŒ¡", new Integer(6));
		pixelWidths.put ("Ã·", new Integer(7));
		pixelWidths.put ("â‰ˆ", new Integer(8));
		pixelWidths.put ("Â°", new Integer(6));
		pixelWidths.put ("âˆ™", new Integer(3));
		pixelWidths.put ("Â·", new Integer(3));
		pixelWidths.put ("âˆš", new Integer(9));
		pixelWidths.put ("â�¿", new Integer(6));
		pixelWidths.put ("Â²", new Integer(5));
		pixelWidths.put ("â– ", new Integer(5));
		pixelWidths.put (" ", new Integer(5));

	}
}
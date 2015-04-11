package se.fredsfursten.textwrap;

import org.bukkit.ChatColor;
import org.bukkit.util.Java15Compat;

import java.util.LinkedList;
import java.util.List;

/**
 * The ChatPaginator takes a raw string of arbitrary length and breaks it down
 * into an array of strings appropriate for displaying on the Minecraft player
 * console.
 */
public class Paginator {
	public static final int PAGE_WIDTH_IN_FONT_PIXELS = 600; // Will never wrap, even with the largest characters
	public static final int UNBOUNDED_PAGE_WIDTH = Integer.MAX_VALUE;
	public static final int OPEN_CHAT_PAGE_HEIGHT = 20; // The height of an expanded chat window
	public static final int CLOSED_CHAT_PAGE_HEIGHT = 10; // The height of the default chat window
	public static final int UNBOUNDED_PAGE_HEIGHT = Integer.MAX_VALUE;

	/**
	 * Breaks a raw string up into pages using the default width and height.
	 *
	 * @param unpaginatedString The raw string to break.
	 * @param pageNumber The page number to fetch.
	 * @return A single chat page.
	 */
	public static ChatPage paginate(String unpaginatedString, int pageNumber) {
		return  paginate(unpaginatedString, pageNumber, PAGE_WIDTH_IN_FONT_PIXELS, CLOSED_CHAT_PAGE_HEIGHT);
	}

	/**
	 * Breaks a raw string up into pages using a provided width and height.
	 *
	 * @param unpaginatedString The raw string to break.
	 * @param pageNumber The page number to fetch.
	 * @param lineLength The desired width of a chat line.
	 * @param pageHeight The desired number of lines in a page.
	 * @return A single chat page.
	 */
	public static ChatPage paginate(String unpaginatedString, int pageNumber, int lineLength, int pageHeight) {
		String[] lines = wordWrap(unpaginatedString, lineLength);

		int totalPages = lines.length / pageHeight + (lines.length % pageHeight == 0 ? 0 : 1);
		int actualPageNumber = pageNumber <= totalPages ? pageNumber : totalPages;

		int from = (actualPageNumber - 1) * pageHeight;
		int to = from + pageHeight <= lines.length  ? from + pageHeight : lines.length;
		String[] selectedLines = Java15Compat.Arrays_copyOfRange(lines, from, to);

		return new ChatPage(selectedLines, actualPageNumber, totalPages);
	}

	/**
	 * Breaks a raw string up into a series of lines. Words are wrapped using
	 * spaces as separators and the newline character is respected.
	 *
	 * @param rawString The raw string to break.
	 * @param lineLength The length of a line of text.
	 * @return An array of word-wrapped lines.
	 */
	public static String[] wordWrap(String rawString, int lineLength) {
		// A null string is a single line
		if (rawString == null) {
			return new String[] {""};
		}

		// A string shorter than the lineWidth is a single line
		if (rawString.length() <= lineLength && !rawString.contains("\n")) {
			return new String[] {rawString};
		}

		char[] rawChars = (rawString + ' ').toCharArray(); // add a trailing space to trigger pagination
		StringBuilder word = new StringBuilder();
		StringBuilder line = new StringBuilder();
		List<String> lines = new LinkedList<String>();
		int lineColorChars = 0;

		for (int i = 0; i < rawChars.length; i++) {
			int lineInPixels = 0;
			int wordInPixels = 0;
			char c = rawChars[i];

			// skip chat color modifiers
			if (c == ChatColor.COLOR_CHAR) {
				word.append(ChatColor.getByChar(rawChars[i + 1]));
				lineColorChars += 2;
				i++; // Eat the next character as we have already processed it
				continue;
			}

			if (c == '\n') {
				// Break the line
				line.append(word);
				lineInPixels += wordInPixels;
				lines.add(line.toString());
				lineInPixels = 0;
				word = new StringBuilder();
				wordInPixels = 0;
			} else {
				int characterInPixels = FontPixels.get().pixelWidth(c);
				if (lineInPixels + wordInPixels + characterInPixels> lineLength) {
					// Break the line
					line.append(word);
					lineInPixels += wordInPixels;
					word = new StringBuilder();
					wordInPixels = 0;
					lines.add(line.toString());
					line = new StringBuilder();
					lineInPixels = 0;
					lineColorChars = 0;
				} 

				if (c == ' ') {
					if (word.length() > 0) {
						line.append(word);
						lineInPixels += wordInPixels;
						word = new StringBuilder(" ");
						wordInPixels = characterInPixels;
					}
				} else {
					word.append(c);
					wordInPixels += characterInPixels;
				}
			}

			if(lineInPixels > 0) { // Only add the last line if there is anything to add
				lines.add(line.toString());
			}

			// Iterate over the wrapped lines, applying the last color from one line to the beginning of the next
			if (lines.get(0).length() == 0 || lines.get(0).charAt(0) != ChatColor.COLOR_CHAR) {
				lines.set(0, ChatColor.WHITE + lines.get(0));
			}
			for (int j = 1; j < lines.size(); j++) {
				final String pLine = lines.get(j-1);
				final String subLine = lines.get(j);

				char color = pLine.charAt(pLine.lastIndexOf(ChatColor.COLOR_CHAR) + 1);
				if (subLine.length() == 0 || subLine.charAt(0) != ChatColor.COLOR_CHAR) {
					lines.set(j, ChatColor.getByChar(color) + subLine);
				}
			}

			return lines.toArray(new String[lines.size()]);
		}
	}

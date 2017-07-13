package xyz.thomasrichards.superxo.game;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class Log<T> {
	private final List<T> l;

	public Log() {
		l = new ArrayList<>();
	}

	public void write(T item) {
		l.add(item);
	}

	public void store(String filepath) {
		try {
			PrintWriter writer = new PrintWriter(filepath, "UTF-8");
			writer.println(l.toString());
			writer.close();
		} catch (IOException e) {
			System.err.println("There was a problem saving the log in path " + filepath);
			System.err.println(e.getMessage());
		}
	}
}

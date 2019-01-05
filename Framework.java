import java.util.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.util.concurrent.*;

public class Framework {

	private static Screen screen;
	private static Camera camera;
	private static ArrayList<Shape> objects;

	private static final int MILLIS_PER_FRAME = 100;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		objects = new ArrayList<>(); // any object added here will be drawn

		screen.startScreen();
		long clock = 0;

		setup();

		while (true) {
			long stime = System.currentTimeMillis();

			// actual code goes here:
			if (!tick(clock)) break;

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			for (Shape obj : objects) {
				camera.render(obj);
			}
			camera.display();
			clock++;
			// ensuring that frames are consistent length:
			while (System.currentTimeMillis()-stime < MILLIS_PER_FRAME) {}
		}
		screen.stopScreen();
	}

	public static void setup() { // run at program initialization.

	}

	public static boolean tick(long clock) { // run at the start of every frame. return false to exit.
		if (clock > 100) return false;

		return true;
	}

}
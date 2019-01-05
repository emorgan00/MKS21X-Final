import java.util.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.util.concurrent.*;

public class Framework {

	public static Screen screen;
	public static Camera camera;
	public static ArrayList<Shape> objects;
	public static final int MILLIS_PER_FRAME = 100;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		objects = new ArrayList<>();

		screen.startScreen();
		long clock = 0;

		while (true) {
			long stime = System.currentTimeMillis();

			// actual code goes here
			if (clock > 1000) break;

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

}
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

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		objects = new ArrayList<>();

		screen.startScreen();
		while (tick()) {
			camera.clearBuffer();
			for (Shape obj : objects) {
				camera.render(obj);
			}
			camera.display();
			camera.doResizeIfNecessary();
		}
		screen.stopScreen();
	}

	public static boolean tick() { // return false to exit the program. otherwise return true
		return false;
	}

}
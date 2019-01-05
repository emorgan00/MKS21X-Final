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

	private static final double MILLIS_PER_FRAME = 5.0;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		objects = new ArrayList<>(); // any object added here will be drawn

		screen.startScreen();
		long clock = -1;
		long stime = System.currentTimeMillis();

		// setup
		camera.setPos(new Vector(-3, 0, 0));
		Shape cube = Shape.Cube(Vector.ZERO, 0.7, new TextColor.RGB(240, 240, 255));
		objects.add(cube);

		while (true) {
			double dt = (int)(System.currentTimeMillis()-stime) / MILLIS_PER_FRAME;
			stime = System.currentTimeMillis();

			// clock is the index of the frame we are on.
			// dt is the number of tick-equivalents passed since the last tick.
			if (++clock > 1000) break;
			System.out.println(dt);
			cube.swivel(0.01*dt);

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			for (Shape obj : objects) {
				camera.render(obj);
			}
			camera.display();
			// ensuring that frames are consistent length:
			while (System.currentTimeMillis()-stime < MILLIS_PER_FRAME) {}
		}
		screen.stopScreen();
	}

}
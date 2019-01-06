import java.util.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.util.concurrent.*;

public class Framework {

	private static Screen screen;
	private static Camera camera;
	private static ArrayList<Shape> objects;
	private static int dt, clock;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		objects = new ArrayList<>(); // any object added here will be drawn

		screen.startScreen();
		clock = -1;
		long stime = System.currentTimeMillis();

		// setup
		camera.setPos(new Vector(-3, 0, 0));
		for (int x = -2; x < 3; x += 2) {
			for (int y = -2; y < 3; y += 2) {
				Shape cube = Shape.Cube(new Vector(0, x, y), 0.7, new TextColor.RGB(255, 255, 255));
				objects.add(cube);
			}
		}

		Vector rot = Vector.UNIT_Z;

		while (true) {
			KeyStroke key = screen.pollInput();
			dt = (int)(System.currentTimeMillis()-stime);
			stime = System.currentTimeMillis();

			// clock is the index of the frame we are on.
			// dt is the number of millis passed since the last tick.
			if (key != null) {
				if      (key.getKeyType() == KeyType.Escape)     break;
				else if (key.getKeyType() == KeyType.ArrowRight) rot = Vector.UNIT_X;
				else if (key.getKeyType() == KeyType.ArrowLeft)  rot = Vector.UNIT_X.scale(-1);
				else if (key.getKeyType() == KeyType.ArrowDown)  rot = Vector.UNIT_Z;
				else if (key.getKeyType() == KeyType.ArrowUp)    rot = Vector.UNIT_Z.scale(-1);
				else if (key.getCharacter() == 'w') camera.translate(Vector.UNIT_X.scale(0.5));
				else if (key.getCharacter() == 's') camera.translate(Vector.UNIT_X.scale(-0.5));
				else if (key.getCharacter() == 'd') camera.translate(Vector.UNIT_Z.scale(0.5));
				else if (key.getCharacter() == 'a') camera.translate(Vector.UNIT_Z.scale(-0.5));
			}

			for (Shape cube : objects) {
				cube.rotate(rot, 0.001*dt);
			}

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			for (Shape obj : objects) {
				camera.render(obj);
			}
			camera.display();
			clock++;
		}
		screen.stopScreen();
	}
}
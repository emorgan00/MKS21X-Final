import java.util.ArrayList;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.util.concurrent.*;
import graphics.*;

public class Rubiks {

	private static Screen screen;
	private static Camera camera;
	private static ArrayList<Shape> cubes;
	private static int dt, clock;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);
		cubes = new ArrayList<>(); // any object added here will be drawn

		screen.startScreen();
		clock = -1;
		long stime = System.currentTimeMillis();

		// setup
		camera.setPos(new Vector(-10, 6, 0));
		camera.rotate(Vector.UNIT_Z, -Math.PI/6);
		for (int x = -2; x < 3; x += 2) {
			for (int y = -2; y < 3; y += 2) {
				for (int z = -2; z < 3; z += 2) {
					Shape cube = Shape.Cube(new Vector(x, y, z), 0.9, new TextColor.RGB((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
					cube.setPos(Vector.ZERO);
					cubes.add(cube);
				}
			}
		}

		double anglebuffer = 0;
		Vector rot = Vector.UNIT_Z;

		while (true) {
			KeyStroke key = screen.pollInput();
			dt = (int)(System.currentTimeMillis()-stime);
			stime = System.currentTimeMillis();

			// clock is the index of the frame we are on.
			// dt is the number of millis passed since the last tick.
			if (key != null && anglebuffer <= 0) {
				if      (key.getKeyType() == KeyType.Escape)     break;
				else if (key.getKeyType() == KeyType.ArrowRight) rot = Vector.UNIT_Y;
				else if (key.getKeyType() == KeyType.ArrowLeft)  rot = Vector.UNIT_Y.scale(-1);
				else if (key.getKeyType() == KeyType.ArrowDown)  rot = Vector.UNIT_Z;
				else if (key.getKeyType() == KeyType.ArrowUp)    rot = Vector.UNIT_Z.scale(-1);
				anglebuffer = Math.PI/2;
			}

			if (anglebuffer > 0) {
				for (Shape obj : cubes) {
					if (obj.pos().dotProduct(rot) > 0)
						obj.rotate(rot, 0.01*dt);
				}
				anglebuffer -= 0.01*dt;
				if (anglebuffer < 0) {
					for (Shape obj : cubes) {
						obj.rotate(rot, anglebuffer);
					}
				}
			}

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			for (Shape obj : cubes) {
				camera.render(obj);
			}
			camera.display();
			clock++;
		}
		screen.stopScreen();
	}
}
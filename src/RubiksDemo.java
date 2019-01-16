import java.util.ArrayList;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import graphics.*;

public class RubiksDemo {

	private static Screen screen;
	private static Camera camera;

	public static void main(String[] args) throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		camera = new Camera(screen);

		screen.startScreen();
		long stime = System.currentTimeMillis();
		int dt;

		// Setting up the cube
		Rubiks cube = new Rubiks(Vector.ZERO, 0.9);

		double anglebuffer = 0;
		boolean scramble = false; // when true, we scramble the cube
		KeyStroke key = null;
		Vector rot = null;
		Rubiks.Face face = null;
		int rotdir = 1;

		camera.setPos(new Vector(-10, 6, 0));
		camera.rotate(Vector.UNIT_Z, -Math.PI/6);

		while (true) {
			key = screen.pollInput();
			dt = (int)(System.currentTimeMillis()-stime);
			stime = System.currentTimeMillis();

			// clock is the index of the frame we are on.
			// dt is the number of millis passed since the last tick.
			if (key != null) {
				rot = null;
				if      (key.getKeyType() == KeyType.Escape) break;
				else if (key.getKeyType() == KeyType.ArrowRight) {
					rot = Vector.UNIT_Y;
				} else if (key.getKeyType() == KeyType.ArrowLeft) {
					rot = Vector.UNIT_Y.scale(-1);
				} else if (key.getKeyType() == KeyType.ArrowDown) {
					rot = Vector.UNIT_Z;
				} else if (key.getKeyType() == KeyType.ArrowUp) {
					rot = Vector.UNIT_Z.scale(-1);
				} else if (key.getCharacter() == ' ') {
					scramble = !scramble;
					key = null;
				}
				/* else if (anglebuffer >= 0) {
					if (key.getCharacter() == 'd') {
						anglebuffer = Math.PI/2;
						face = cube.nearestFace(camera.dir());
						rotdir = -1;
					} else if (key.getCharacter() == 'a') {
						anglebuffer = Math.PI/2;
						face = cube.nearestFace(camera.dir());
						rotdir = 1;
					} 
				} */
			}

			if (key == null && scramble && anglebuffer <= 0) {
				anglebuffer = Math.PI/2;
				face = Rubiks.Face.random();
				rotdir = (int)(Math.random()*2)*2-1;
			}

			if (anglebuffer > 0) {
				if (face != null) {
					cube.rotateFace(face, 0.005*dt*rotdir);
					anglebuffer -= 0.005*dt;
					if (anglebuffer < 0) { // if we overshot the rotation by a little
						cube.rotateFace(face, anglebuffer*rotdir);
						face = null;
					}
				}
			}

			if (rot != null) {
				cube.rotate(rot, 0.3);
				rot = null;
			}

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			camera.render(cube);
			camera.display();
		}
		screen.stopScreen();
	}
}
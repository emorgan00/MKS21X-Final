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
		Rubiks cube = new Rubiks(Vector.UNIT_Z, 0.9);

		double anglebuffer = 0;
		boolean scramble = false; // when true, we scramble the cube
		int view = 0;
		Vector rot = Vector.UNIT_Z; // which axis to rotate a face around
		Vector face = Vector.ZERO; // which face to rotate. ZERO means all faces
		KeyStroke key = null;

		camera.setPos(new Vector(-10, 6, 0));
		camera.rotate(Vector.UNIT_Z, -Math.PI/6);

		while (true) {
			if (!scramble) key = screen.pollInput();
			dt = (int)(System.currentTimeMillis()-stime);
			stime = System.currentTimeMillis();

			// clock is the index of the frame we are on.
			// dt is the number of millis passed since the last tick.
			if (key != null && anglebuffer <= 0) {
				anglebuffer = Math.PI/2;
				if      (key.getKeyType() == KeyType.Escape) break;
				else if (key.getKeyType() == KeyType.ArrowRight) {
					rot = Vector.UNIT_Y;
					face = Vector.ZERO;
				} else if (key.getKeyType() == KeyType.ArrowLeft) {
					rot = Vector.UNIT_Y.scale(-1);
					face = Vector.ZERO;
				} else if (key.getKeyType() == KeyType.ArrowDown) {
					rot = Vector.UNIT_Z;
					face = Vector.ZERO;
				} else if (key.getKeyType() == KeyType.ArrowUp) {
					rot = Vector.UNIT_Z.scale(-1);
					face = Vector.ZERO;
				} else if (key.getKeyType() == KeyType.Tab) {
					if (view == 0) {
						view = 1;
						camera.setPos(new Vector(-7, 5, 7));
						camera.rotate(Vector.UNIT_Y, Math.PI/4);
					} else if (view == 1) {
						view = 0;
						camera.setPos(new Vector(-10, 6, 0));
						camera.rotate(Vector.UNIT_Y, -Math.PI/4);
					}
					anglebuffer = 0; // overriding rotation
				} else if (key.getCharacter() == 'e') {
					rot = Vector.UNIT_Y;
					face = Vector.UNIT_Y;
				} else if (key.getCharacter() == 'q') {
					rot = Vector.UNIT_Y.scale(-1);
					face = Vector.UNIT_Y;
				} else if (key.getCharacter() == 'c') {
					rot = Vector.UNIT_Y;
					face = Vector.UNIT_Y.scale(-1);
				} else if (key.getCharacter() == 'z') {
					rot = Vector.UNIT_Y.scale(-1);
					face = Vector.UNIT_Y.scale(-1);
				} else if (key.getCharacter() == 'd') {
					rot = Vector.UNIT_X;
					face = Vector.UNIT_X.scale(-1);
				} else if (key.getCharacter() == 'a') {
					rot = Vector.UNIT_X.scale(-1);
					face = Vector.UNIT_X.scale(-1);
				} else if (key.getCharacter() == ' ') {
					scramble = true;
					key = null;
					anglebuffer = 0;
				} else anglebuffer = 0;
			} else if (scramble && anglebuffer <= 0) {
				if (screen.pollInput() != null) scramble = false;
				anglebuffer = Math.PI/2;
				int rand = (int)(Math.random()*6);
					 if (rand == 0) face = Vector.UNIT_X;
				else if (rand == 1) face = Vector.UNIT_X.scale(-1);
				else if (rand == 2) face = Vector.UNIT_Y;
				else if (rand == 3) face = Vector.UNIT_Y.scale(-1);
				else if (rand == 4) face = Vector.UNIT_Z;
				else if (rand == 5) face = Vector.UNIT_Z.scale(-1);
				rot = face.scale((int)(Math.random()*2)*2-1);
			}

			if (anglebuffer > 0) {
				cube.rotate(rot, 0.005*dt);
				anglebuffer -= 0.005*dt;
				if (anglebuffer < 0) { // if we overshot the rotation by a little
					cube.rotate(rot, anglebuffer);
				}
			}

			camera.doResizeIfNecessary();
			camera.clearBuffer();
			camera.render(cube);
			camera.display();
		}
		screen.stopScreen();
	}
}
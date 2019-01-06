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

		// Setting up initial triangles
		Vector a = new Vector(0.9, 0.9, 0.9);
		Vector b = new Vector(0.9, 0.9, -0.9);
		Vector c = new Vector(0.9, -0.9, 0.9);
		Vector d = new Vector(0.9, -0.9, -0.9);
		Vector e = new Vector(-0.9, 0.9, 0.9);
		Vector f = new Vector(-0.9, 0.9, -0.9);
		Vector g = new Vector(-0.9, -0.9, 0.9);
		Vector h = new Vector(-0.9, -0.9, -0.9);

		Triangle r0 = new Triangle(a, e, b, new TextColor.RGB(255, 0, 0));
		Triangle r1 = new Triangle(e, f, b, new TextColor.RGB(255, 0, 0));
		Triangle o0 = new Triangle(c, d, g, new TextColor.RGB(255, 127, 0));
		Triangle o1 = new Triangle(d, h, g, new TextColor.RGB(255, 127, 0));
		Triangle b0 = new Triangle(c, a, b, new TextColor.RGB(0, 0, 255));
		Triangle b1 = new Triangle(d, c, b, new TextColor.RGB(0, 0, 255));
		Triangle g0 = new Triangle(f, e, g, new TextColor.RGB(0, 255, 0));
		Triangle g1 = new Triangle(h, f, g, new TextColor.RGB(0, 255, 0));
		Triangle w0 = new Triangle(f, d, b, new TextColor.RGB(255, 255, 255));
		Triangle w1 = new Triangle(d, f, h, new TextColor.RGB(255, 255, 255));
		Triangle y0 = new Triangle(a, c, e, new TextColor.RGB(255, 255, 0));
		Triangle y1 = new Triangle(c, g, e, new TextColor.RGB(255, 255, 0));

		// setup the cube
		camera.setPos(new Vector(-10, 6, 0));
		camera.rotate(Vector.UNIT_Z, -Math.PI/6);
		for (int x = -2; x < 3; x += 2) {
			for (int y = -2; y < 3; y += 2) {
				for (int z = -2; z < 3; z += 2) {
					Shape cube = new Shape(Vector.ZERO, Vector.UNIT_X, Vector.UNIT_Y);
					if (y == -2) {cube.addTriangle(o0); cube.addTriangle(o1);}
					if (y == 2) {cube.addTriangle(r0); cube.addTriangle(r1);}
					if (z == -2) {cube.addTriangle(w0); cube.addTriangle(w1);}
					if (z == 2) {cube.addTriangle(y0); cube.addTriangle(y1);}
					if (x == -2) {cube.addTriangle(g0); cube.addTriangle(g1);}
					if (x == 2) {cube.addTriangle(b0); cube.addTriangle(b1);}
					cube.translate(new Vector(x, y, z));
					cube.setDir(cube.pos());
					cube.setCenter(Vector.ZERO);
					cubes.add(cube);
				}
			}
		}

		double anglebuffer = 0;
		boolean scramble = false; // when true, we scramble the cube
		Vector rot = Vector.UNIT_Z; // which axis to rotate a face around
		Vector face = Vector.ZERO; // which face to rotate. ZERO means all faces
		KeyStroke key = null;

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
				for (Shape obj : cubes) {
					if (face == Vector.ZERO || obj.dir().dotProduct(face) > 0.2)
						obj.rotate(rot, 0.005*dt);
				}
				anglebuffer -= 0.005*dt;
				if (anglebuffer < 0) {
					for (Shape obj : cubes) {
						if (face == Vector.ZERO || obj.dir().dotProduct(face) > 0.2)
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
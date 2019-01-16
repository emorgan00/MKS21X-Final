import graphics.*;
import java.util.ArrayList;
import com.googlecode.lanterna.*;

public class Rubiks extends Particle implements Renderable {

	private ArrayList<Shape> cubes;

	public Rubiks(Vector pos, double cubeRadius) {
		super(pos, Vector.UNIT_X, Vector.UNIT_Y);

		// dir points through the BLUE face
		// normal points through the RED face

		cubes = new ArrayList<>();

		// Setting up initial triangles
		Vector a = new Vector(cubeRadius, cubeRadius, cubeRadius);
		Vector b = new Vector(cubeRadius, cubeRadius, -cubeRadius);
		Vector c = new Vector(cubeRadius, -cubeRadius, cubeRadius);
		Vector d = new Vector(cubeRadius, -cubeRadius, -cubeRadius);
		Vector e = new Vector(-cubeRadius, cubeRadius, cubeRadius);
		Vector f = new Vector(-cubeRadius, cubeRadius, -cubeRadius);
		Vector g = new Vector(-cubeRadius, -cubeRadius, cubeRadius);
		Vector h = new Vector(-cubeRadius, -cubeRadius, -cubeRadius);

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

		// Set up the cube
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
					cube.translate(new Vector(x*1.1*cubeRadius, y*1.1*cubeRadius, z*1.1*cubeRadius));
					cube.setDir(cube.pos());
					cube.setCenter(Vector.ZERO);
					cube.translate(pos);
					cubes.add(cube);
				}
			}
		}
	}

	public String toString() {
		return "Rubiks(pos: "+pos()+", dir: "+dir()+")";
	}

	public void render(Camera c) {
		for (Shape cube : cubes) {
			c.render(cube);
		}
	}

	public void setPos(Vector pos) {
		super.setPos(pos);
		Vector rel = this.pos().subtract(pos);
		for (Shape cube : cubes) {
			cube.translate(rel);
		}
	}

	public void translate(Vector motion) {
		super.translate(motion);
		for (Shape cube : cubes) {
			cube.translate(motion);
		}
	}

	public void rotate(Vector axis, double angle) {
		super.rotate(axis, angle);
		for (Shape cube : cubes) {
			cube.rotate(axis, angle);
		}
	}

	enum Face { // This is what we can use to specify a face
		RED, ORANGE, YELLOW, GREEN, BLUE, WHITE
	}

	public void rotateFace(Face face, int turns) { // clockwise
		Vector rot;
		switch(face) {
			case BLUE:
				rot = normal();
				break;
			case RED:
				rot = dir();
				break;
			case GREEN:
				rot = normal().scale(-1);
				break;
			case ORANGE:
				rot = dir().scale(-1);
				break;
			case YELLOW:
				rot = dir().crossProduct(normal());
				break;
			case WHITE:	
				rot = normal().crossProduct(dir());
				break;
		}
		

	}
}
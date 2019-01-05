import java.util.*;
import com.googlecode.lanterna.*;

public class Shape extends Particle {

	private ArrayList<Triangle> faces;

	// Shape are instantiated without any faces. Add Triangles to the Shape with addTriangle()
	public Shape(Vector pos, Vector dir, Vector normal) {
		super(pos, dir, normal);
		faces = new ArrayList<>();
	}

	public String toString() {
		StringBuilder out = new StringBuilder("Shape(pos: "+pos()+", dir: "+dir()+")");
		for (Triangle face : faces) {
			out.append("\n-> ");
			out.append(face);
		}
		return new String(out);
	}

	public void addTriangle(Triangle tri) {
		faces.add(tri);
	}

	public ArrayList<Triangle> getFaces() {
		return faces;
	}

	public void setPos(Vector pos) {
		super.setPos(pos);
		Vector rel = this.pos().subtract(pos);
		for (int i = 0; i < faces.size(); i++) {
			faces.set(i, faces.get(i).translate(rel));
		}
	}

	public void translate(Vector motion) {
		super.translate(motion);
		for (int i = 0; i < faces.size(); i++) {
			faces.set(i, faces.get(i).translate(motion));
		}
	}

	public void rotate(Vector axis, double angle) {
		super.rotate(axis, angle);
		for (int i = 0; i < faces.size(); i++) {
			faces.set(i, faces.get(i).rotate(pos(), axis, angle));
		}
	}

	// Shortcuts for common shapes:

	public static Shape Cube(Vector pos, double radius, TextColor.RGB color) {

		Shape shape = new Shape(pos, Vector.UNIT_X, Vector.UNIT_Y);

		Vector a = pos.add(new Vector(radius, radius, radius));
		Vector b = pos.add(new Vector(radius, radius, -radius));
		Vector c = pos.add(new Vector(radius, -radius, radius));
		Vector d = pos.add(new Vector(radius, -radius, -radius));
		Vector e = pos.add(new Vector(-radius, radius, radius));
		Vector f = pos.add(new Vector(-radius, radius, -radius));
		Vector g = pos.add(new Vector(-radius, -radius, radius));
		Vector h = pos.add(new Vector(-radius, -radius, -radius));

		shape.addTriangle(new Triangle(a, e, b, color));
		shape.addTriangle(new Triangle(f, e, b, color));
		shape.addTriangle(new Triangle(c, d, g, color));
		shape.addTriangle(new Triangle(h, d, g, color));
		shape.addTriangle(new Triangle(f, d, b, color));
		shape.addTriangle(new Triangle(f, d, h, color));
		shape.addTriangle(new Triangle(a, c, b, color));
		shape.addTriangle(new Triangle(d, c, b, color));
		shape.addTriangle(new Triangle(e, f, g, color));
		shape.addTriangle(new Triangle(h, f, g, color));
		shape.addTriangle(new Triangle(a, c, e, color));
		shape.addTriangle(new Triangle(g, c, e, color));

		return shape;
	}

}
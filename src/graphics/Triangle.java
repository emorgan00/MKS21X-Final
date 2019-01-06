package graphics;
import com.googlecode.lanterna.*;

public class Triangle {
	
	private Vector a, b, c;
	private Vector normal, center;
	private TextColor.RGB color;

	public static final double oneThird = 1./3;

	public Triangle(Vector a, Vector b, Vector c, TextColor.RGB color) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.center = a.add(b).add(c).scale(oneThird);
		this.normal = b.subtract(a).crossProduct(c.subtract(a));
		this.color = color;
	}

	private Triangle(Vector a, Vector b, Vector c, Vector normal, Vector center, TextColor.RGB color) {
		// this is for use in Triangle.translate(): we don't want to recompute normals/centers
		this.a = a;
		this.b = b;
		this.c = c;
		this.center = center;
		this.normal = normal;
		this.color = color;
	}

	public String toString() {
		return "Triangle("+a+", "+b+", "+c+")";
	}

	public Vector normal() {
		// since the normals of Triangles will be fetched often, we precompute them and store in the Triangle object
		return this.normal;
	}

	public Vector center() {
		return this.center;
	}

	public TextColor.RGB color() {
		return this.color;
	}

	public Triangle translate(Vector r) { // slightly faster than creating a new Triangle, since we just copy over the normal vector
		return new Triangle(a.add(r), b.add(r), c.add(r), normal, center.add(r), color);
	}

	public Triangle translateInverse(Vector r) { // shortcut for subtraction instead of adding
		return new Triangle(a.subtract(r), b.subtract(r), c.subtract(r), normal, center.subtract(r), color);
	}

	public boolean contains(Vector r) { // returns true if the point lies inside the triangular prism created by extending this triangle
		Triangle moved = this.translateInverse(r);
		Vector x = moved.a.crossProduct(moved.b);
		return x.dotProduct(moved.b.crossProduct(moved.c)) > 0 && x.dotProduct(moved.c.crossProduct(moved.a)) > 0;
	}

	public Triangle rotate(Vector pivot, Vector axis, double angle) { // rotate about a pivot point
		return new Triangle(
			a.subtract(pivot).rotate(axis, angle).add(pivot), 
			b.subtract(pivot).rotate(axis, angle).add(pivot), 
			c.subtract(pivot).rotate(axis, angle).add(pivot),
			color);
	}

}
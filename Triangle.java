public class Triangle {
	
	private Vector a, b, c;
	private Vector normal, center;
	public static final double oneThird = 1./3;

	public Triangle(Vector a, Vector b, Vector c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.center = a.add(b).add(c).scale(oneThird);
		this.normal = b.subtract(a).crossProduct(c.subtract(a));
	}

	public String toString() {
		return "Triangle("+a+", "+b+", "+c+")";
	}

	public Vector normal() {
		return this.normal;
	}

	public Vector center() {
		return this.center;
	}

}
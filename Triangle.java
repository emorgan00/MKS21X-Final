public class Triangle {
	
	private Vector a,b,c;
	private Vector normal,center;

	public Triangle(Vector a, Vector b, Vector c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.center = a.add(b).add(c).scale(1./3);
		this.normal = b.subtract(a).crossProduct(c.subtract(a));
	}

	public String toString() {
		return "Triangle("+a+", "+b+", "+c+")";
	}

}
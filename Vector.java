public class Vector {

	private double x, y, z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public String toString() {
		return "<"+x+", "+y+", "+z+">";
	}

	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}

	public Vector add(Vector other) {
		return new Vector(this.x+other.x, this.y+other.y, this.z+other.z);
	}

	public Vector subtract(Vector other) {
		return new Vector(this.x-other.x, this.y-other.y, this.z-other.z);
	}

	public Vector scale(double k) {
		return new Vector(this.x*k, this.y*k, this.z*k);
	}

}
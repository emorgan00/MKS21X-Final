public class Vector {

	private double x, y, z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector v) { // note: this may be redundant
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

	public Vector scale(double k) { // return a Vector with equal direction, but magnitude multiplied by k
		return new Vector(this.x*k, this.y*k, this.z*k);
	}

	public double dotProduct(Vector other) { // returns the dot product of two Vectors
		return this.x*other.x + this.y*other.y + this.z*other.z;
	}

	public Vector crossProduct(Vector other) { // returns the cross product of two Vectors
		// Normally this would be defined using determinants/matrices. Here the x,y,z formula is just hard-coded
		return new Vector(this.y*other.z - other.y*this.z, this.z*other.x - other.z*this.x, this.x*other.y - other.x*this.y);
	}

}
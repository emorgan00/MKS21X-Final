public class Vector {

	private double x, y, z;
	public static final Vector ZERO = new Vector(0, 0, 0);

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

	// returns whether a given ray intersects a given plane within a given triangle
	// pos = origin of ray
	// dir = direction of ray
	// tri = Triangle object
	public static boolean intersectsTriangle(Vector pos, Vector dir, Triangle tri) {
		tri = tri.translate(ZERO.subtract(pos));
		double d = dir.dotProduct(tri.normal());
		if (d == 0) return false; // if the ray is parallel to the plane, just return false
		double m = tri.center().dotProduct(tri.normal())/d;
		return m > 0 && tri.contains(dir.scale(m));
	}

	public static double distanceToTriangle(Vector pos, Vector dir, Triangle tri) { // see previous method, returns -1 if does not intersect
		tri = tri.translateInverse(pos);
		double d = dir.dotProduct(tri.normal());
		if (d == 0) return -1; // if the ray is parallel to the plane, just return false
		double m = tri.center().dotProduct(tri.normal())/d;
		if (!tri.contains(dir.scale(m))) return -1;
		return m; // note: this not the true distance, it is the factor by which the dir must be scaled to hit the triangle.
	}

}
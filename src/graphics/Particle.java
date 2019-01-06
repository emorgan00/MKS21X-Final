package graphics;

public abstract class Particle { // like a vector, but mutable and has both position and direction

	private Vector pos, dir, normal;

	public Particle(Vector pos, Vector dir, Vector normal) { // the "normal" is used to specify roll, and points straight up
		this.pos = pos;
		this.dir = dir.unitize(); // particles are not instantiated all that often, so this is OK runtime-wise.
		this.normal = normal.unitize();
	}

	public String toString() {
		return "Particle(pos: "+pos+", dir: "+dir+")";
	}

	public Vector dir() {
		return dir;
	}

	public Vector pos() {
		return pos;
	}

	public Vector normal() {
		return normal;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public void setDir(Vector dir) {
		this.dir = dir.unitize();
		// note: this is not overridden by Shape/Camera.
	}

	public void translate(Vector motion) {
		pos = pos.add(motion);
	}

	public void rotate(Vector axis, double angle) {
		dir = dir.rotate(axis, angle);
		normal = normal.rotate(axis, angle);
	}

}
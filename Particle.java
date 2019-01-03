public class Particle { // like a vector, but mutable and has both position and direction

	private Vector pos, dir;

	public Particle(Vector pos, Vector dir) {
		this.pos = pos;
		this.dir = dir;
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

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public void translate(Vector motion) {
		pos = pos.add(motion);
	}

	public void translateInverse(Vector motion) {
		pos = pos.subtract(motion);
	}

}
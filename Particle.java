public class Particle { // like a vector, but mutable and has both position and direction

	private Vector pos, dir;

	public Particle(Vector pos, Vector dir) {
		this.pos = pos;
		this.dir = dir;
	}

	public String toString() {
		return "Particle(pos: "+pos+", dir: "+dir+")";
	}

}
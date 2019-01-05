import java.util.*;

public class Shape extends Particle {

	private ArrayList<Triangle> faces;

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

}
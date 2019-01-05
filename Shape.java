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

	public void addTriangle(Triangle tri) {
		faces.add(tri);
	}

	public ArrayList<Triangle> getFaces() {
		return faces;
	}

	public void setPos(Vector pos) {
		super.setPos(pos);
		Vector rel = this.pos().subtract(pos);
		for (Triangle face : faces) {
			face = face.translate(rel);
		}
	}

	public void translate(Vector motion) {
		super.translate(motion);
		for (Triangle face : faces) {
			face = face.translate(motion);
		}
	}

}
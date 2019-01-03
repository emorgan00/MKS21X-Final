import java.util.*;

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		Triangle t = new Triangle(new Vector(0, 1, 0), new Vector(0, 1, 1), new Vector(0, 0, 1));

		Vector p = new Vector(1, 0, 0);
		Vector d = new Vector(-1, 0.7, 0.7);

		System.out.println(Vector.intersectsTriangle(p, d, t));
	}

}
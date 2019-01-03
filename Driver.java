import java.util.*;

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		Triangle t = new Triangle(new Vector(2, 1, 0), new Vector(2, 1, 1), new Vector(2, 0, 1));

		Camera c = new Camera(24, 80);

		c.clearBuffer();
		System.out.println(c.dir());
	}

}
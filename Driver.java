import java.util.*;

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		Vector v0 = new Vector(0, 1, 2);
		Vector v1 = new Vector(1, 2, 0);

		System.out.println(v0.subtract(v1));
	}

}
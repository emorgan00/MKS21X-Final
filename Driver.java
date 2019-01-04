import java.util.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.util.concurrent.*;

public class Driver {

	public static void main(String[] args) throws IOException, InterruptedException {
		Screen screen = new DefaultTerminalFactory().createScreen();
		screen.startScreen();

		Camera cam = new Camera(screen);
		cam.setPos(new Vector(-3, 0, 0));

		Triangle tri = new Triangle(new Vector(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), TextColor.ANSI.GREEN);
		Triangle tri2 = new Triangle(new Vector(0, 1, 1), new Vector(0, 1, 0), new Vector(0, 0, 1), TextColor.ANSI.RED);

		for (int i = 0; i < 3000; i++) {
			cam.clearBuffer();
			cam.render(tri);
			cam.render(tri2);
			tri = tri.translate(new Vector(0, 0.0005, 0.0005));
			tri2 = tri2.translate(new Vector(0, 0.0005, 0.0005));
			cam.display();
			cam.doResizeIfNecessary();
		}
		
		screen.stopScreen();
	}

}
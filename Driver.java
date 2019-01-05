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

		Shape s = Shape.Cube(Vector.ZERO, 0.6, TextColor.ANSI.RED);

		for (int i = 0; i < 1000; i++) {
			cam.clearBuffer();
			cam.render(s);
			s.translate(new Vector(0, 0, 0.01));
			cam.display();
			cam.doResizeIfNecessary();
		}

		screen.stopScreen();
	}

}
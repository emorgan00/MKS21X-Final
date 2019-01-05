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

		Shape s = Shape.Cube(Vector.ZERO, 0.7, new TextColor.RGB(230, 230, 255));

		for (int i = 0; i < 1000; i++) {
			cam.clearBuffer();
			cam.render(s);
			s.rotate(new Vector(3, 5, 3).unitize(), 0.05);
			cam.display();
			cam.doResizeIfNecessary();
		}

		screen.stopScreen();
	}

}
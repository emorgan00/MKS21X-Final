package graphics;
import java.util.*;

// in the actual graphics package, only Shape implements Renderable.
// this class is included to allow the user to define their own object which the Camera can render.
public interface Renderable {
	public void render(Camera c);
}
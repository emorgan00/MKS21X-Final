import com.googlecode.lanterna.*;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;

public class Camera extends Particle {

	private int width,height;
	private double scale; // represents how far to the left/right you look

	private Vector[][] cast; // an array of vectors which are casted from pos, each representing one pixel.
	private double[][] zbuffer; // for z-buffering
	private TextCharacter[][] displaybuffer; // holds what will be printed
	private Screen screen;

	public static final double TERMINAL_RATIO = 2; // value of (height / width) for a character on the terminal
	public static final char[] BRIGHTNESS_MAP = {'\u2591', '\u2591', '\u2591', '\u2592', '\u2592', '\u2593', '\u2593', '\u2588'}; // length can be anything
	public static final TextCharacter VOID_CHARACTER = new TextCharacter(' ');

	// the Camera accepts a Screen object on which it will operate

	public Camera(Screen screen) {
		super(Vector.ZERO, Vector.UNIT_X, Vector.UNIT_Y);
		TerminalSize size = screen.getTerminalSize();

		this.scale = 0.5; // arbitrarily 45 degrees in each direction
		this.height = size.getRows();
		this.width = size.getColumns();
		this.cast = new Vector[height][width];
		this.zbuffer = new double[height][width];
		this.displaybuffer = new TextCharacter[height][width];
		this.screen = screen;
		recast();
	}

	public boolean doResizeIfNecessary() {
		// returns true if the Screen was resized
		// will update the Screen which the Camera holds
		TerminalSize size = screen.doResizeIfNecessary();
		if (size == null) return false;
		this.height = size.getRows();
		this.width = size.getColumns();
		this.cast = new Vector[height][width];
		this.zbuffer = new double[height][width];
		this.displaybuffer = new TextCharacter[height][width];
		recast();
		return true;
	}

	public String toString() {
		return "Camera(pos: "+pos()+", dir: "+dir()+")";
	}

	public void setFOV(double scale) {
		this.scale = scale;
		recast();
	}

	public void setPos(Vector pos) {
		super.setPos(pos);
		clearBuffer();
	}

	public void translate(Vector motion) {
		super.translate(motion);
		clearBuffer();
	}

	public void rotate(Vector axis, double angle) {
		super.rotate(axis, angle);
		recast();
	}

	public void recast() { // update cast[][] vectors
		Vector hnorm = dir().crossProduct(normal()).unitize(); // offset unit vector in horizontal direction (vertical unit vector is just the normal)
		double woffset = width/2; // amount to shift horizontally to remain centered
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) { // yes, dividing w/height is intentional, we want to keep the proportions consistent =)
				Vector offset = hnorm.scale(((w-woffset)/height)*scale).add(normal().scale((0.5-(double)h/height)*scale*TERMINAL_RATIO));
				cast[h][w] = dir().add(offset).unitize();
			}
		}
		clearBuffer();
	}

	public void clearBuffer() { // clear the z-buffer and display-buffer
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				zbuffer[h][w] = Double.POSITIVE_INFINITY;
				displaybuffer[h][w] = VOID_CHARACTER;
			}
		}
	}

	// returns the character which should be put on the screen, given a brightness and color
	private TextCharacter displayChar(double dot, TextColor color) { // dot is the dot product result indicating brightness
		return new TextCharacter(
			BRIGHTNESS_MAP[(int)(dot == 1 ? BRIGHTNESS_MAP.length-1 : dot*BRIGHTNESS_MAP.length)],
			color,
			TextColor.ANSI.BLACK // temporary. This should use ASCII blocks later on
		);
	}

	// renders a triangle to the displaybuffer and zbuffer
	public void render(Triangle tri) {
		double d = Math.abs(dir().dotProduct(tri.normal().unitize())); // number from 0 to 1, representing how much we are "facing" the triangle
		// 1 means head-on, 0 means barely looking down the side

		TextCharacter displaychar = displayChar(d, tri.color());

		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				double dist = Vector.distanceToTriangle(pos(), cast[h][w], tri);
				if (dist > 0 && dist < zbuffer[h][w]) {
					zbuffer[h][w] = dist;
					displaybuffer[h][w] = displaychar;
				}
			}
		}
	}

	// renders a shape by iteratively rendering its triangles
	public void render(Shape shape) {
		for (Triangle face : shape.getFaces()) {
			render(face);
		}
	}

	// updates the terminal screen with the displaybuffer
	public void display() throws IOException {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				TextCharacter ch = displaybuffer[h][w];
				screen.setCharacter(w, h, ch);
			}
		}
		screen.refresh();
	}

}
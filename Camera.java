public class Camera extends Particle {

	private int width,height;
	private double scale; // represents how far to the left/right you look

	private Vector[][] cast; // an array of vectors which are casted from pos, each representing one pixel.
	private double[][] zbuffer; // for z-buffering
	private String[][] displaybuffer; // holds what will be printed

	public static final double TERMINAL_RATIO = 2; // value of (height / width) for a character on the terminal

	public Camera(int height, int width) {
		super(Vector.ZERO, Vector.UNIT_X);
		this.scale = 0.5; // arbitrarily 45 degrees in each direction
		this.height = height;
		this.width = width;
		this.cast = new Vector[height][width];
		this.zbuffer = new double[height][width];
		this.displaybuffer = new String[height][width];
	}

	public String toString() {
		return "Camera(pos: "+pos()+", dir: "+dir()+")";
	}

}
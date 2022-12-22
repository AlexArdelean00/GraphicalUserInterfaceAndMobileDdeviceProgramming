package utils;

public class Color {

	// NOTA: questa classe potrebbe essere definita come estensione di java.awt.Color

	// ********************************************************************
	// This class is used to represent an rgb color.
	// To be used in a BufferedImage the float r,g,b values must be
	// converted in a unique int variable, can be done by casting it to
	// the java awt Color class and use the getRGB() method.
	// ********************************************************************

	// Instance Attribute
	private float r,g,b;

	// Static Variables
	public static Color WHITE = new Color(1f,1f,1f);
	public static Color BLACK = new Color(0f,0f,0f);
	public static Color RED = new Color(1f,0f,0f);
	public static Color GREEN = new Color(0f,1f,0f);
	public static Color BLUE = new Color(0f,0f,1f);

	// Constructor
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color(){
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}

	// Color Operations
	public Color add(Color c) {
		return new Color(this.r+c.r, this.g+c.g, this.b+c.b);
	}

	public Color product(Color c) {
		return new Color(this.r*c.r, this.g*c.g, this.b*c.b);
	}

	public Color scale(float d) {
		return new Color(this.r*d, this.g*d, this.b*d);
	}

	public Color average(Color c, int samples){
		return this.add(c.scale(1f/samples));
	}

	// Setter-Getter
	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}

	// Cast to awt Color class and return correspondent int value
	private float normalize(float value) {
		if(value>1f)return 1f;
		if(value<0f)return 0f;
		return value;
	}

	public int getRGB() {
		java.awt.Color c = new java.awt.Color(normalize(this.r), normalize(this.g), normalize(this.b));
		return c.getRGB();
	}

	public java.awt.Color getAWTColor() {
		return new java.awt.Color(normalize(this.r), normalize(this.g), normalize(this.b));
	}

	// To String
	@Override
	public String toString() {
		return "Color{" +
				"r=" + r +
				", g=" + g +
				", b=" + b +
				'}';
	}
}

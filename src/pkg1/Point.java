package pkg1;
import java.awt.Graphics;
import java.awt.Color;

public class Point {

	private int x;
	private int y;
	private Integer index;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean equals(Point p) {
		return (p.x == this.x && p.y == this.y);
	}

	public boolean isRightOf(Point p) {
		return this.x >= p.x;
	}

	public void draw(Graphics g) {
		g.fillOval(x-4, y-4, 7, 7);
	}

	public String toString() {
		return "P" + (index==null ? "":index) + "(" + x + "," + y + ")";
	}

	public void drawLine(Graphics g, Point other) {
		g.setColor(Color.black);
		g.drawLine(x, y, other.x, other.y);
		if (index != null)
			g.drawString("" + index, x+10, y-10);	
	//	System.out.println("x="+x + "y="+y + "other.x="+other.x + "other.y="+other.y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
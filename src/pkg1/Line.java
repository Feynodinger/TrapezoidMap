package pkg1;
import java.awt.Color;
import java.awt.Graphics;

public class Line {

	protected Point start;
	protected Point end;

	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		start.draw(g);
		end.draw(g);
		start.drawLine(g, end);
	}
	
	public String toString() {
		return "\tL [" + start + "--" + end + "]\n";
	}
}
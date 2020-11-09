package pkg1;
import java.awt.Color;
import java.awt.Graphics;

public class TrapezoidFace {
	private TrapezoidLine topLine;
	private TrapezoidLine botmLine;

	private String index = "";

	public TrapezoidFace(TrapezoidLine top, TrapezoidLine bottom) {
		this.topLine = top;
		this.botmLine = bottom;
	}
	
	public TrapezoidLine getTopLine() {
		return topLine;
	}
	
	public TrapezoidLine getBotmLine() {
		return botmLine;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void draw(Graphics g) {

//		Graphics2D g2d = (Graphics2D)g;
//		g2d.setColor(Color.getColor("", index));
		
		Point p1 = topLine.getStart();
		Point p2 = botmLine.getEnd();
		
		g.setColor(Color.BLUE);
		g.drawRect(p1.getX(), 
				   p1.getY(), 
				   p2.getX() - p1.getX(),  // width 
				   p2.getY() - p1.getY()); // height		
	}
	
	public String toString() {
		return "T" + index + "["+ topLine + botmLine + "]";
	}
}
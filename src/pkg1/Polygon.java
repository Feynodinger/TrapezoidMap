package pkg1;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

	private List<Point> points;
	private int pIndex = 0;

	public Polygon() {
		this.points = new ArrayList<Point>();
	}

	public Polygon(List<Point> points) {
		this.points = points;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void addPoint(Point p) {
		p.setIndex(++pIndex);
		points.add(p);
	}

	/*
	 * public Point getFirstPoint() { // return points.get(0).x >
	 * points.get(points.size() -1).x ? points.get(0) : points.get(points.size() -1)
	 * ; return points.get(0); }
	 * 
	 * public Point getLastPoint() { // return points.get(0).x <=
	 * points.get(points.size() -1).x ? points.get(0) : points.get(points.size() -1)
	 * ; return points.get(points.size() -1); }
	 */
	
	/*
	 * public boolean isAboveOf(Point p) { if(points.contains(p)) return false;
	 * return (p.y < lineAprox(getFirstPoint().x, getFirstPoint().y,
	 * getLastPoint().x, getLastPoint().y, p.x)); }
	 * 
	 * public boolean below(Point p) { if(points.contains(p)) return false; return
	 * (p.y > lineAprox(getFirstPoint().x, getFirstPoint().y, getLastPoint().x,
	 * getLastPoint().y, p.x)); }
	 */
	private float lineAprox(int x1, int y1, int x2, int y2, int x){
		//    return (float)(((y2-y1)/(x2-x1))*(x - x1)+y1);
		return (float)((float)(((float)(y2-y1)/(x2-x1))*(x-x1))+y1);
	}

	// Check whether a is between b and c or not.
	private boolean consider(Point a, Point b, Point c) {
		return (   (b.getX() < a.getX() && a.getX() < c.getX() )      // whether b < a < c
				|| (c.getX() < a.getX() && a.getX() < b.getX())       //   or    c < a < b
			/* && (a.y > b.y || a.y > c.y) */ );
	}

	/*
	 * public int intersect(Point pt) { return
	 * Math.round(lineAprox(getFirstPoint().x, getFirstPoint().y, getLastPoint().x,
	 * getLastPoint().y, pt.x) ); }
	 */
	
	// Minimum Y of lines in a shape along the Trapezoid line.
	/*
	public float intersect(TrapezoidLine t, float max) {
		for(int i=1; i<points.size(); i++) {
			Point pt = points.get(i);
			Point prevpt = points.get(i-1);
			if(consider(t.getStart(), pt, prevpt)) {
				max = Math.min(max, (float)(pt.getY() - prevpt.getY())/(pt.getX()-prevpt.getX())*(t.getStart().getX()-prevpt.getX())+prevpt.getY());
			}
		}
		return max;
	} */

	public boolean equals(Polygon s) {
		return s==null ? points==null : s.getPoints().equals(points);
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);									// Set color
		for(int i = 0; i<points.size(); i++) {
			points.get(i).draw(g);								// draw point
			if(i>0) 
				points.get(i).drawLine(g, points.get(i-1));	// draw line
		}
		points.get(0).drawLine(g, points.get(points.size()-1));	// draw last line
	}
	
	public String toString() {
		String s = "";
		for(Point point: points)
			s += point + "\n";
		return s;
	}
}
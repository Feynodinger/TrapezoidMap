package pkg1;

public class TrapezoidLine extends Line implements Comparable<TrapezoidLine> {

	public TrapezoidLine(Point start, Point end) {
		super(start, end);
	}

	public int compareTo(TrapezoidLine o) {
		if (this.start.getY() == o.start.getY() ) return this.end.getY() - o.end.getY();
		else return this.start.getY() - o.start.getY();
	}
}
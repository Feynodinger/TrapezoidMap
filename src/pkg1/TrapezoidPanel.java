package pkg1;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class TrapezoidPanel extends JPanel implements MouseListener{

	private Set<Polygon> polygons = new HashSet<>();
	public List<List<TrapezoidFace>> tFaces;

	public TrapezoidPanel() {
		addMouseListener(this);
	}

	public void addPolygon(Polygon p) {
		polygons.add(p);
	}

	public Set<Polygon> getPolygons() {
		return polygons; 
	}

	public void setTFaces(List<List<TrapezoidFace>> f) {
		this.tFaces = f;
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (tFaces != null)
			for(List<TrapezoidFace> lf : tFaces) 
				if (lf != null)
					for(TrapezoidFace tf: lf)
						tf.draw(g);
	
		for(Polygon p : polygons)
			p.draw(g);
	
		
		//if(queryPoint != null) 
		//	queryPoint.draw(g);
	}

	public void clear() {
		if (tFaces != null)
			tFaces.clear();
		this.repaint();
	}

	public void mousePressed (MouseEvent e) {
	}

	public void mouseReleased (MouseEvent e) {
	}

	public void mouseEntered (MouseEvent e) {
	}

	public void mouseExited (MouseEvent e) {
	}

	public void mouseClicked (MouseEvent e) {
	}

	public void printFreeSpace() {		
		if (tFaces != null) {
			int count = 1;
			for(List<TrapezoidFace> lf : tFaces) {
				System.out.println("Vertical list " + count++);
				if (lf != null)
					for(TrapezoidFace tf: lf)
						System.out.println(tf);
			}
		}
	}
}
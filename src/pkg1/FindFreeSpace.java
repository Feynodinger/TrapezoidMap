package pkg1;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FindFreeSpace implements ActionListener{

	private JFrame frame 		  = new JFrame("Trapezoidal Map Project");
	private JPanel btnPanel 	  = new JPanel(new GridLayout(0,1));
	private TrapezoidPanel tPanel = new TrapezoidPanel();
	private JButton readBtn 	  = new JButton("Read Input");
	private JButton algoBtn1 	  = new JButton("Initial Trapezoid");
	private JButton algoBtn2 	  = new JButton("Find Free Space");
	private List<List<TrapezoidFace>> freeSpace = new ArrayList<>();
	
	public FindFreeSpace() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tPanel.setPreferredSize(new Dimension(500, 500));

		btnPanel.add(readBtn,BorderLayout.LINE_END);
		btnPanel.add(algoBtn1,BorderLayout.LINE_END);
		btnPanel.add(algoBtn2,BorderLayout.LINE_END);

		readBtn.addActionListener(this);
		algoBtn1.addActionListener(this);
		algoBtn2.addActionListener(this);

		frame.getContentPane().add(btnPanel, BorderLayout.LINE_END);
		frame.getContentPane().add(tPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == readBtn) {
			tPanel.clear();
			
			try {
				for (int i=1; i<=2; i++) {
					Path path = Paths.get("C:/Users/basua/Downloads/Polygon/Polygon/src/input" + i + ".txt"); 
					Polygon polygon = new Polygon();
					Files.lines(path).forEach(s -> {
						int x = Integer.parseInt(s.split(",")[0]);
						int y = Integer.parseInt(s.split(",")[1]);
						polygon.addPoint(new Point(x, y));
					});
				
					tPanel.addPolygon(polygon);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if(e.getSource() == algoBtn1) {
				
			// Find distinct x values.
			List<Integer> xValues = new ArrayList<>(); 
			for(Polygon polygon: tPanel.getPolygons()) {
				for(Point p: polygon.getPoints())
					if (!xValues.contains(p.getX())) 
						xValues.add(p.getX());
			}
						
			Collections.sort(xValues);
			int x=0;
			TrapezoidLine topLine;
			TrapezoidLine botmLine;
			List<TrapezoidFace> vList;
			
			// Construct initial trapezoids.
			for(int i=0; i<xValues.size(); i++) {
				vList = new ArrayList<>();
				
				topLine  = new TrapezoidLine(new Point(x,   0), new Point(xValues.get(i),   0));
				botmLine = new TrapezoidLine(new Point(x, 500), new Point(xValues.get(i), 500));
				
				TrapezoidFace tFace = new TrapezoidFace(topLine, botmLine);
				tFace.setIndex(i+1+"");
				vList.add(tFace);
				freeSpace.add(vList);
				x = xValues.get(i);
			}

			// Construct final trapezoid.
			vList = new ArrayList<>();
			
			topLine  = new TrapezoidLine(new Point(x,   0), new Point(500,   0));
			botmLine = new TrapezoidLine(new Point(x, 500), new Point(500, 500));

			TrapezoidFace tFace = new TrapezoidFace(topLine, botmLine);
			tFace.setIndex(xValues.size() + 1 + "");
			vList.add(tFace);
			freeSpace.add(vList);
			
			tPanel.setTFaces(freeSpace);
			
			System.out.println("Initial Trapezoids:");
			tPanel.printFreeSpace();
			SlabStructure s= new SlabStructure(tPanel);
			s.findSlabs();
			
			
		}
		else if(e.getSource() == algoBtn2) {

			List<Line> allPolyLines = new ArrayList<>();
			
			// traverse through polygons and create a list of all lines.
			for(Polygon poly: tPanel.getPolygons()) {
				int polySize = poly.getPoints().size();
				
				for(int j=0; j<polySize; j++) {
					Point p1 = poly.getPoints().get(j);
					Point p2 = poly.getPoints().get( (j+1)%polySize );
					allPolyLines.add( p1.getX() < p2.getX() ? new Line(p1, p2) : new Line(p2, p1));
				}
			}

			Point leftP, rightP;

			// No splitting needed on first and last trapezoid.
			for(int i=1; i<freeSpace.size()-1; i++) {
				
				List<TrapezoidLine> allTrapezLines = new ArrayList<TrapezoidLine>();
				
				// Fetch one initial trapezoid and their x-values
				List<TrapezoidFace> vList = freeSpace.get(i);
				TrapezoidFace initTrapez = vList.get(0);
			
				int tx1 = initTrapez.getTopLine().getStart().getX();
				int tx2 = initTrapez.getTopLine().getEnd().getX();
				
				leftP = null;
				rightP = null;
				
				for(Line polyLine: allPolyLines) {
					
					// Current line divides current trapezoid.
					if (polyLine.getStart().getX() <= tx1 && tx2 <= polyLine.getEnd().getX()) {
						
						// current line starts on trapezoid boundary.
						if(polyLine.getStart().getX() == tx1) 
							leftP = polyLine.getStart();
						else {
							int x1 = polyLine.start.getX();
							int y1 = polyLine.start.getY();
							int x2 = polyLine.end.getX();
							int y2 = polyLine.end.getY();
							float slope = (float) (y2-y1) / (x2-x1); 
							 
							int y =  (int)((tx1 - x1) * slope) + y1;
						    leftP = new Point(tx1, y);
						 }

						// current line ends on trapezoid boundary.
						if(polyLine.getEnd().getX() == tx2) 
							rightP = polyLine.getEnd();
						else {
							int x1 = polyLine.start.getX();
							int y1 = polyLine.start.getY();
							int x2 = polyLine.end.getX();
							int y2 = polyLine.end.getY();
							float slope = (float) (y2-y1) / (x2-x1); 
							 
							int y =  (int)((tx2 - x1) * slope) + y2;
						    rightP = new Point(tx2, y);
						 }
						
						allTrapezLines.add(new TrapezoidLine(leftP, rightP));
					}
					
				} // Process one initial trapezoid.
				
				Collections.sort(allTrapezLines);
				
				TrapezoidLine topLine = initTrapez.getTopLine();
				
				vList.clear();  // Remove initial trapezoid
				for(int j=0; j<allTrapezLines.size(); j++) {
					TrapezoidLine tLine = allTrapezLines.get(j);
					TrapezoidFace tf = new TrapezoidFace(topLine, tLine);
					tf.setIndex( (i+1) + "-" + (j+1));
					vList.add(tf);
					topLine = tLine;
				}
				
				TrapezoidFace tf = new TrapezoidFace(topLine, initTrapez.getBotmLine());
				tf.setIndex( (i+1) + "-" + (allTrapezLines.size()+1));
				vList.add(tf);
			} // Initial Trapezoid loop ends.

			System.out.println("Final Trapezoids:");
			tPanel.printFreeSpace();
			SlabStructure s= new SlabStructure(tPanel);
			HashMap<Integer,SortedSet<String>>slab =s.findSlabs();
			List<FreeBlock> lfblock = new ArrayList<FreeBlock>();
			int cnt =0;
			for (Map.Entry<Integer, SortedSet<String>> r : slab.entrySet())
			{
				List<String> as= new ArrayList<String>(r.getValue());
				lfblock.add(new FreeBlock(r.getKey(), as, Math.max((tPanel.tFaces.get(cnt)).get(0).getTopLine().getStart().getY(),(tPanel.tFaces.get(cnt)).get(0).getTopLine().getEnd().getY()),Math.min((tPanel.tFaces.get(cnt)).get(tPanel.tFaces.get(cnt).size()-1).getBotmLine().getStart().getY(),(tPanel.tFaces.get(cnt)).get(tPanel.tFaces.get(cnt).size()-1).getBotmLine().getEnd().getY())));
			cnt++;
			}
			
		}  // Algorithm Button 2.
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FindFreeSpace driver = new FindFreeSpace();
			}
		});
	}
}
package pkg1;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InitialTrapezoid implements ActionListener {

	private JFrame frame 		  = new JFrame("Trapezoidal Map Project");
	private JPanel btnPanel 	  = new JPanel(new GridLayout(0,1));
	private TrapezoidPanel tPanel = new TrapezoidPanel();
	private JButton readBtn 	  = new JButton("Read Input");
	private JButton algo1Btn 	  = new JButton("Algorithm 1");
	
	public InitialTrapezoid() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tPanel.setPreferredSize(new Dimension(500, 500));

		btnPanel.add(readBtn,BorderLayout.LINE_END);
		btnPanel.add(algo1Btn,BorderLayout.LINE_END);

		readBtn.addActionListener(this);
		algo1Btn.addActionListener(this);

		frame.getContentPane().add(btnPanel, BorderLayout.LINE_END);
		frame.getContentPane().add(tPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==readBtn) {
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
		else if(e.getSource() == algo1Btn) {

			// Find distinct x values.
			List<Integer> xValues = new ArrayList<>(); 
			for(Polygon polygon: tPanel.getPolygons()) {
				for(Point p: polygon.getPoints())
					if (!xValues.contains(p.getX())) 
						xValues.add(p.getX());
			}
						
			Collections.sort(xValues);
			List<List<TrapezoidFace>> freeSpace = new ArrayList<>();
			int x=0;
			Point topLeft;
			Point botmRight;
			TrapezoidLine topLine;
			TrapezoidLine botmLine;
			List<TrapezoidFace> vList;
			
			// Construct initial trapezoids.
			for(int i=0; i<xValues.size(); i++) {
				vList = new ArrayList<>();
				
				topLeft = new Point(x, 0);
				botmRight = new Point(xValues.get(i), 500);
				
				topLine = new TrapezoidLine(topLeft, new Point(xValues.get(i), 0));
				botmLine = new TrapezoidLine(new Point(x, 500), botmRight);
				
				TrapezoidFace tFace = new TrapezoidFace(topLine, botmLine);
				tFace.setIndex(i+"");
				vList.add(tFace);
				freeSpace.add(vList);
				x = xValues.get(i);
			}

			// Construct initial-final trapezoid.
			vList = new ArrayList<>();
			topLeft = new Point(x, 0);
			botmRight = new Point(500, 500);
			
			topLine = new TrapezoidLine(topLeft, new Point(500, 0));
			botmLine = new TrapezoidLine(new Point(x, 500), botmRight);

			TrapezoidFace tFace = new TrapezoidFace(topLine, botmLine);
			tFace.setIndex(xValues.size() + "");
			vList.add(tFace);
			freeSpace.add(vList);
			
			
			tPanel.setTFaces(freeSpace);
			tPanel.printFreeSpace();
			SlabStructure s= new SlabStructure(tPanel);
			s.findSlabs();
			
			// Calculate free space
			
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InitialTrapezoid driver = new InitialTrapezoid();
			}
		});
	}
}
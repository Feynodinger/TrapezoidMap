package pkg1;
import java.util.*;
import java.io.*;
public class SlabStructure {
	TrapezoidPanel tPanel = new TrapezoidPanel();
	HashMap<Integer, SortedSet<String>> slab = new HashMap<>();
	
	public SlabStructure(TrapezoidPanel tPanel)
	{
		this.tPanel= tPanel;
	}
	public SlabStructure(SlabStructure s)
	{
		this.slab= s.slab;
	}
	public SlabStructure()
	{
		
	}
	public HashMap<Integer, SortedSet<String>> findSlabs()
	{
		if (tPanel.tFaces != null) {
			int count =1;
			
			for(List<TrapezoidFace> lf : tPanel.tFaces) 
			{
				SortedSet<String> temp= new TreeSet<String>();
				if (lf != null)
				{
					
					
					if (lf != null)
						for(TrapezoidFace tf: lf)
						{
							
							temp.add(Integer.toString(count));
							slab.put((lf.get(0)).getTopLine().start.getX(),temp);
							count++;
						}
					//slab.put((lf.get(0)).getTopLine().start.getX(),temp);
					
				}	
			}
			System.out.println(slab);
			
		}
		
		return slab;
		
	}
}

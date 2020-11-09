package pkg1;
import java.util.*;

public class FreeBlock {
 int slab;
 List<String> ftrap= new ArrayList<String>();
 int lup;
 int hlo;
 public FreeBlock(int slab, List<String> ftrap,int lup,int hlo)
 {
	 this.slab=slab;
	 this.ftrap=ftrap;
	 this.lup=lup;
	 this.hlo=hlo;
	 
 }
 
}

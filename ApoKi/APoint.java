/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

public class APoint implements Comparable<APoint>{
	private int _x,_y,_F;
	
	/*
	 * Creates a new Instance of an APoint (for A* Algorithm), which contains the Position and the heuristic value F
	 */
	public APoint(int x, int y, int F){
		_x=x;
		_y=y;
		_F=F;
	}
	
	public APoint(int x, int y){
		_x=x;
		_y=y;
		_F=0;
	}
	
	public APoint(APoint point, int amount_x, int amount_y){
		_x=point.Getx()+amount_x;
		_y=point.Gety()+amount_y;
	}
	
	public APoint(){
		
	}
	
	public int Getx(){
		return _x;
	}
	public int Gety(){
		return _y;
	}
	public int GetF(){
		return _F;
	}
	public void SetF(int value){
		_F=value;
	}
	@Override
	public String toString(){
		return "["+_x+","+_y+"]";
	}
	
	@Override
	public int compareTo(APoint arg0) {
		return GetF()-arg0.GetF();
	}
	
	 @Override 
	 public boolean equals(Object arg0 ) 
	 {
		 if (arg0==null)
			 return false;
		 if ( !(arg0 instanceof APoint) ) return false;
		 APoint temp=(APoint)arg0;
		 if (_x==temp.Getx()&&_y==temp.Gety())
			 return true;
		 return false;
	 }
	 
	 public static APoint waitPoint(){
		 return new APoint(-1,-1);
	 }
	 
	 /*
	  * Static Point to use when an error accures while getting an APoint from a list, ...
	  */
	 public static APoint errorPoint(){
		 return new APoint(-1,-2);
	 }
}

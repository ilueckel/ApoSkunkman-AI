/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

import java.util.ArrayList;
import java.util.Collections;

public class PathList {
	private ArrayList<APoint> pathpointlist;
	private int pathposition;
	
	/* ### Info for special APoints ###
	 * 
	 * # X-Value ### Y-Value ###    Note                     #
	 * |----------|-----------|-------------------------------|
	 * |    -1    |     -1    |    Waiting                    |
	 * |----------|-----------|-------------------------------|
	 * |    -1    |     -2    |    Error getting NextPoint    |
	 * |          |           |    e.g. now looking for new   |
	 * |          |           |    path in main class.        |
	 * |----------|-----------|-------------------------------|
	*/
	
	public PathList(){
		pathpointlist=new ArrayList<APoint>();
		pathposition=0;
	}
	
	public void AddPoint(APoint point){
		pathpointlist.add(point);
	}
	
	public void InsertPoint(APoint point){
		pathpointlist.add(pathposition, point);
	}
	
	public void AddPoints(ArrayList<APoint> points){
		pathpointlist.addAll(points);
	}
	
	public void InsertPoints(ArrayList<APoint> points){
		pathpointlist.addAll(pathposition,points);
	}
	
	public int PathPosition(){
		return pathposition;
	}
	
	public String toString(){
		String temp="";
		for (APoint p : pathpointlist){
			temp+=p.toString()+" ";
		}
		temp+="\nActual Position: "+pathposition;
		return temp;
	}
	
	public APoint NextPoint(){
		APoint temp;
		if (pathposition<=pathpointlist.size()-1){
			temp=pathpointlist.get(pathposition);
			pathposition+=1;
		}else{
			temp=APoint.errorPoint();
		}
		return temp;
	}
	
	public APoint GetPoint(int index){
		return pathpointlist.get(index);
	}
	
	public APoint GetActualPoint(){
		if (pathposition-1>0){
			return pathpointlist.get(pathposition-1);
		}else{
			return APoint.errorPoint();
		}
	}
	
	public boolean IsEmpty(){
		return pathpointlist.isEmpty();
	}
	
	public int Size(){
		return pathpointlist.size();
	}
	
	public void ReverseList(){
		Collections.reverse(pathpointlist);
	}
}
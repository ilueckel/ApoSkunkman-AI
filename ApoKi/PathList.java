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
		pathpointlist.add(pathposition,point);
	}
	
	public void AddPoints(ArrayList<APoint> points){
		pathpointlist.addAll(points);
	}
	
	public void InsertPoints(ArrayList<APoint> points){
		pathpointlist.addAll(pathposition,points);
	}
	
	public void ClearList(){
		pathpointlist=new ArrayList<APoint>();
		pathposition=0;
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
		if (index<pathpointlist.size())
		{
			return pathpointlist.get(index);
		}else{
			return APoint.errorPoint();
		}
		
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
	
	/*
	 * Returns true, if the given Point is not more than 5 Tiles away from the last Point in the list
	 */
	public boolean IsTargetNearPoint(APoint target){
		if (pathpointlist.size()>0)
		{
			APoint last=pathpointlist.get(pathpointlist.size()-1);
			int difx=Math.abs(last.Getx()-target.Getx());
			int dify=Math.abs(last.Gety()-target.Gety());
			if (difx <=5 && dify<=5)
				return true;
		}
		return false;
	}
	
	public void SetPositionOneBack(){
		pathposition=Math.max(0, pathposition-1);
	}
	
	public boolean Contains(APoint point){
		return pathpointlist.contains(point);
	}
}
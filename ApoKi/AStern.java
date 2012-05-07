/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */


import java.util.ArrayList;
import java.util.Collections;

import apoSkunkman.ai.ApoSkunkmanAIConstants;
import apoSkunkman.ai.ApoSkunkmanAILevel;
import apoSkunkman.ai.ApoSkunkmanAIPlayer;


public class AStern {
	ApoSkunkmanAILevel level;
	ApoSkunkmanAIPlayer player;
	StopWatch stopwatch=new StopWatch();
	
	public AStern(ApoSkunkmanAILevel _level, ApoSkunkmanAIPlayer _player)
	{
		setProperties(_level,_player);
	}
	
	public void setProperties(ApoSkunkmanAILevel _level, ApoSkunkmanAIPlayer _player){
		level=_level;
		player=_player;
	}
	
	/*
	 * Path from PlayerPosition to GoalXPoint
	 */
	public ArrayList<APoint> FindPath(){
		ArrayList<APoint> list=new ArrayList<APoint>();
		APoint pp=new APoint(player.getPlayerX(),player.getPlayerY());
		APoint target=new APoint(level.getGoalXPoint().x,level.getGoalXPoint().y);
		ASternPath(level,pp,target,list);
		//list.remove(0);
		stopwatch=new StopWatch();
		return list;
	}
	
	/*
	 * Path from start to target
	 */
	public ArrayList<APoint> FindPath(APoint start, APoint target){
		ArrayList<APoint> list=new ArrayList<APoint>();
		ASternPath(level,start,target,list);
		//list.remove(0);
		stopwatch=new StopWatch();
		return list;
	}
	
	/*
	 * Path from start to target, but just the walk-able part
	 */
	public ArrayList<APoint> FindPath(APoint start, APoint target, boolean throughbushes){
		ArrayList<APoint> list=new ArrayList<APoint>();
		ASternPath(level,new ArrayList<APoint>(),start,target,list,throughbushes,0);
		//Collections.reverse(list);
		//list.remove(0);
		stopwatch=new StopWatch();
		return list;
	}
	
	private boolean ASternPath(ApoSkunkmanAILevel level, APoint start, APoint target, ArrayList<APoint> list){
		return ASternPath(level,new ArrayList<APoint>(),start,target,list,true,0);
	}
	
	private boolean ASternPath(ApoSkunkmanAILevel level,ArrayList<APoint> previouspoints, APoint point, APoint target, ArrayList<APoint> list, boolean movethroughbush,int n){
		if (stopwatch.getElapsedTime()>200){
			stopwatch.stop();
			return false;
			
		}
		if (stopwatch.isRunning()==false)
			stopwatch.start();
		
		if (target.Getx()==point.Getx() && point.Gety()==target.Gety()){
			list.addAll(previouspoints);
			list.add(target);
			return true;
		}
		ArrayList<APoint> pointlist = new ArrayList<APoint>();
		int nx;
		int ny;
		
		try{
			//Point above
			nx=point.Getx();
			ny=point.Gety()-1;
			int above = level.getLevelAsByte()[ny][nx];
			if (previouspoints.contains(new APoint(nx,ny))==false){
				if(above==ApoSkunkmanAIConstants.LEVEL_BUSH && movethroughbush==true){
					int F=(int) (3*(Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
				if(above==ApoSkunkmanAIConstants.LEVEL_FREE||above==ApoSkunkmanAIConstants.LEVEL_GOODIE||above==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN){
					int F=(int) ((Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
			}
		}catch (Exception e){
		}
		
		
		try{
			//Point left
			nx=point.Getx()-1;
			ny=point.Gety();
			int left = level.getLevelAsByte()[ny][nx];
			if (previouspoints.contains(new APoint(nx,ny))==false){
				if(left==ApoSkunkmanAIConstants.LEVEL_BUSH && movethroughbush==true){
					int F=(int) (3*(Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
				if(left==ApoSkunkmanAIConstants.LEVEL_FREE||left==ApoSkunkmanAIConstants.LEVEL_GOODIE||left==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN){
					int F=(int) ((Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
			}
		}catch (Exception e){
		}
		
		try{
			//Point right
			nx=point.Getx()+1;
			ny=point.Gety();
			int right = level.getLevelAsByte()[ny][nx];
			if (previouspoints.contains(new APoint(nx,ny))==false){
				if(right==ApoSkunkmanAIConstants.LEVEL_BUSH && movethroughbush==true){
					int F=(int) (3*(Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
				if(right==ApoSkunkmanAIConstants.LEVEL_FREE||right==ApoSkunkmanAIConstants.LEVEL_GOODIE||right==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN){
					int F=(int) ((Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
			}
		}catch (Exception e){
		}
		
		try{
			//Point bottom
			nx=point.Getx();
			ny=point.Gety()+1;
			int bottom = level.getLevelAsByte()[ny][nx];
			if (previouspoints.contains(new APoint(nx,ny))==false){
				if(bottom==ApoSkunkmanAIConstants.LEVEL_BUSH && movethroughbush==true){
					int F=(int) (3*(Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
				if(bottom==ApoSkunkmanAIConstants.LEVEL_FREE||bottom==ApoSkunkmanAIConstants.LEVEL_GOODIE||bottom==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN){
					int F=(int) ((Math.round(Math.sqrt(Math.pow(nx-target.Getx(), 2)+Math.pow(ny-target.Gety(), 2))))) ;
					APoint temp=new APoint(nx,ny,F);
					pointlist.add(temp);
				}
			}
		}catch (Exception e){
			
		}
		
		try{
			
			//if (n<100){
				Collections.sort(pointlist);
				while(pointlist.size()>2)
					pointlist.remove(2);
				int i=0;
				for (APoint temp:pointlist){
					previouspoints.add(temp);
					i+=1;
					if (ASternPath(level,previouspoints,temp,target,list,movethroughbush,n+i)==true){
						return true;
					}else{
						previouspoints.remove(temp);
					}
				}
			//}
		}catch (Exception e){
			return false;
		}
		return false;
	}

}

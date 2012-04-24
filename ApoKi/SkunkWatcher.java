/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

import java.util.ArrayList;
import java.util.Collections;

import apoSkunkman.ai.ApoSkunkmanAIConstants;
import apoSkunkman.ai.ApoSkunkmanAIEnemy;
import apoSkunkman.ai.ApoSkunkmanAILevel;
import apoSkunkman.ai.ApoSkunkmanAILevelSkunkman;
import apoSkunkman.ai.ApoSkunkmanAIPlayer;


public class SkunkWatcher {
	ArrayList<APoint> skunklist;
	AStern pathprovider;
	
	public SkunkWatcher()
	{
		skunklist=new ArrayList<APoint>();
	}
	
	public boolean CheckSkunksInList(ApoSkunkmanAILevel level)
	{
		for (APoint skunk:skunklist){
			if (level.getLevelAsByte()[skunk.Gety()][skunk.Getx()]!=ApoSkunkmanAIConstants.LEVEL_SKUNKMAN)
			{
				skunklist.remove(skunk);
				CheckSkunksInList(level);
				return true;
			}
		}
		return false;
	}
	
	public void watch(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player,creeperman main){
		ArrayList<APoint> skunkarea = new ArrayList<APoint>();
		if (CheckSkunksInList(level))
		{
			main.newPath(level, player);
		}
		
		int maxskunkwidth=0;
		
		for (int x=0;x<=12;x++){
			for (int y=0;y<=12;y++){
				if (level.getLevelAsByte()[y][x]==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN){
					ApoSkunkmanAILevelSkunkman skunk=level.getSkunkman(y, x);
					if (skunk.getTimeToExplosion()>player.getMSForOneTile() && ((main.GetPathList().GetPoint(main.GetPathList().PathPosition()).equals(new APoint(x,y))) || (main.GetCurrentPosition(player).equals(new APoint(x,y))))){
						ArrayList<APoint> temp = new ArrayList<APoint>();
						temp.add(new APoint(x,y,4000-skunk.getTimeToExplosion()));
						//Direction Top
						for (int i=1; i<=(skunk.getSkunkWidth());i++){
							if (level.getLevelAsByte()[y-i][x]==ApoSkunkmanAIConstants.LEVEL_FREE){
								temp.add(new APoint(x,y-i,0));
							}else{
								i=skunk.getSkunkWidth();
							}
						}
						//Direction Down
						for (int i=1; i<=(skunk.getSkunkWidth());i++){
							if (level.getLevelAsByte()[y+i][x]==ApoSkunkmanAIConstants.LEVEL_FREE){
								temp.add(new APoint(x,y+i,0));
							}else{
								i=skunk.getSkunkWidth();
							}
						}
						//Direction Left
						for (int i=1; i<=(skunk.getSkunkWidth());i++){
							if (level.getLevelAsByte()[y][x-i]==ApoSkunkmanAIConstants.LEVEL_FREE){
								temp.add(new APoint(x-i,y,0));
							}else{
								i=skunk.getSkunkWidth();
							}
						}
						//Direction Right
						for (int i=1; i<=(skunk.getSkunkWidth());i++){
							if (level.getLevelAsByte()[y][x+i]==ApoSkunkmanAIConstants.LEVEL_FREE){
								temp.add(new APoint(x+i,y,0));
							}else{
								i=skunk.getSkunkWidth();
							}
						}
						//Skunk selbst
						temp.add(new APoint(x,y,9));
						
						//Pr�fen, ob Skunk auf unseren aktuellen oder n�chsten Punkt zielt
						if (temp.contains(main.GetCurrentPosition(player))){
							skunkarea.addAll(temp);
							skunklist.add(new APoint(x,y));
						}
						if (temp.contains(main.GetPathList().GetPoint(main.GetPathList().PathPosition()))){
							if (skunkarea.isEmpty()){
								skunkarea.addAll(temp);
							}
							if (!skunklist.contains(new APoint(x,y)))
								skunklist.add(new APoint(x,y));
						}
						maxskunkwidth=skunk.getSkunkWidth();
					}
				}
			}
		}
		if (skunkarea.isEmpty()==false){
			pathprovider=new AStern(level,player);
			main.GetPathList().InsertPoints(ASternSkunk(level,new APoint(player.getPlayerX(),player.getPlayerY(),0),skunkarea,maxskunkwidth));
		}
		
		if (skunklist.isEmpty()){
			if (main.DontMove()==true){
				main.SetMovement(false);
			}			
		}
	}
	
	private ArrayList<APoint> ASternSkunk(ApoSkunkmanAILevel level,APoint current,ArrayList<APoint> affected,int skunkwidth)
	{
		ArrayList<APoint> list=new ArrayList<APoint>();
		//List of best Point
		ArrayList<APoint> possible= new ArrayList<APoint>();
		int suchumfeld=0;
		while (possible.size()==0){
			suchumfeld+=1;
			for (int x=(suchumfeld)*-1;x<=suchumfeld;x++){
				for (int y=(suchumfeld)*-1;y<=suchumfeld;y++){
					try{
						if (level.getLevelAsByte()[current.Gety()+y][current.Getx()+x]==ApoSkunkmanAIConstants.LEVEL_FREE && affected.contains(new APoint(current.Getx()+x,current.Gety()+y))==false){
							//possible.add(new APoint(current.Getx()+x,current.Gety()+y,0));
							APoint possiblepoint=(new APoint(current.Getx()+x,current.Gety()+y,0));
							ArrayList<APoint> temp= new ArrayList<APoint>();
							temp=pathprovider.FindPath(current, possiblepoint, false);
							Collections.reverse(temp);
							temp.remove(0);
							if (temp.size()<=(skunkwidth+2)*2)
							{
								list.clear();
								list.addAll(temp);
								//list.add(0,possiblepoint);
								possible.add(new APoint(0,0,0));//Nur zum Abbruch der Schleife
								y=suchumfeld;
								x=suchumfeld;
							}
						}
					}catch(Exception e){
						y+=1;
					}
				}
			}
		}
		
		if (list.size()>0){
			ArrayList<APoint> output=new ArrayList<APoint>();
			Collections.reverse(list);
			output.addAll(list);
			output.add(new APoint(-1,-1,0));
			Collections.reverse(list);
			output.addAll(list);
			output.add(current);
			return output;
		}
		return new ArrayList<APoint>();
	}

}

/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

import java.util.ArrayList;

import apoSkunkman.ai.*;

public class creeperman extends ApoSkunkmanAI {
	private boolean waiting=false;
	private AStern pathprovider;
	private SkunkWatcher skunkwatcher;
	private PathList path;
	private GoodieWatcher goodiewatcher;
	
	@Override
	public String getPlayerName() {
		return "The Creeper";
	}

	@Override
	public String getAuthor() {
		return "Igor Lückel";
	}
	
	@Override
	public void think(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player) {
		// herausbekommen welcher Leveltypus gespielt wird
		int type = level.getType();
		if (type == ApoSkunkmanAIConstants.LEVEL_TYPE_GOAL_X) {
			//Don't care about the enemy
			if (level.getStartTime()==level.getTime()){
				pathprovider=new AStern(level,player);
				skunkwatcher=new SkunkWatcher();
				goodiewatcher=new GoodieWatcher();
				newPath(level,player);
			}
			if(player.isMoving()==false){
				pathprovider.setProperties(level, player);
				nextMove(level,player);
			}
			
		} else {
			//Make yourself the target
			if (level.getStartTime()==level.getTime()){
				skunkwatcher=new SkunkWatcher();
				pathprovider=new AStern(level,player);
				//pathpointlist=pathprovider.FindPath(new APoint(level.getGoalXPoint().x,level.getGoalXPoint().y), new APoint(player.getPlayerX(),player.getPlayerY()));
			}
			ArrayList<APoint> enemyList = new ArrayList<APoint>();
			ApoSkunkmanAIEnemy[] gegner=level.getEnemies();
			for (ApoSkunkmanAIEnemy enemy:gegner){
				
			}
			nextMove(level,player);
		}
		/*
		// erstmal das Tile bekommen was überprüft werden soll (x und y bestimmt die Position im Array)
		int tile = level.getLevelAsByte()[y][x];
		// wenn es ein Goodie ist ...
		if (tile == ApoSkunkmanAIConstants.LEVEL_GOODIE) {
		// gib mir das Goodie als Objekt zurück
		ApoSkunkmanAILevelGoodie goodie = level.getGoodie(y, x);
		// falls dort wirklich ein Goodie ist, dann
		if (goodie != null) {
		int goodieInt = goodie.getGoodie();
		if (goodieInt == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
		// es ist ein Goodie, wodurch sich der Feuerradius der nächsten gelegten Bomben um 1 erhöht
		}
		// Zeit in Millisekunden, wie lang das Goodie noch sichtbar ist
		int timeInMs = goodie.getTimeLeftVisible();
		}
		 Gegner
		}*/
	}
	
	public void nextMove(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player){
		//try{
			skunkwatcher.watch(level, player, this);
			goodiewatcher.watch(level, this);
			if (!path.IsEmpty() && !waiting){
								
				APoint next=path.NextPoint();
				int px=player.getPlayerX();
				int py=player.getPlayerY();
				int nx=next.Getx();
				int ny=next.Gety();
				
				while (px==nx && py==ny){
					next=path.NextPoint();
					nx=next.Getx();
					ny=next.Gety();			
				}
				
				if (next.equals(APoint.waitPoint()))//Wait
				{
					waiting=true;
				}else if(next.equals(APoint.errorPoint())){
					newPath(level,player);
				}else{
					int point = level.getLevelAsByte()[ny][nx];		
					if (point==ApoSkunkmanAIConstants.LEVEL_FREE || point==ApoSkunkmanAIConstants.LEVEL_GOODIE){
						if (px-nx==0 && ny-py==1)
							player.movePlayerDown();
						if (ny-py==0 && px-nx==+1)
							player.movePlayerLeft();
						if (ny-py==0 && px-nx==-1)
							player.movePlayerRight();
						if (px-nx==0 && ny-py==-1)
							player.movePlayerUp();
					}else{
						if (point==ApoSkunkmanAIConstants.LEVEL_BUSH)
							player.laySkunkman();
					if (point==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN);
					}
				}
			}else{
				newPath(level,player);
			}
		/*}catch (Exception e){
			System.out.println(e.toString()+" -by NextMove");
		}*/
	}
	
	public void newPath(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player){
		pathprovider=new AStern(level,player);
		PathList temp1=new PathList();
		PathList temp2=new PathList();
		
		temp1.AddPoints(pathprovider.FindPath());
		temp2.AddPoints(pathprovider.FindPath(new APoint(level.getGoalXPoint().x,level.getGoalXPoint().y), new APoint(player.getPlayerX(),player.getPlayerY())));
		
		if (temp2.Size()<=temp1.Size()-1){
			temp2.ReverseList();
			path=temp2;
		}else{
			path=temp1;
		}
		if (path.Size()>0){
			path.AddPoint(new APoint(level.getGoalXPoint().x,level.getGoalXPoint().y));
		}
	}

	public boolean DontMove(){
		return waiting;
	}
	public void SetMovement(boolean value)
	{
		waiting=value;
	}
	
	public APoint GetCurrentPosition(ApoSkunkmanAIPlayer player){
		return new APoint(player.getPlayerX(),player.getPlayerY());
	}
	
	public PathList GetPathList(){
		return path;
	}

}

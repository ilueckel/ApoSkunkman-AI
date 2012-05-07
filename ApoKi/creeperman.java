/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

/*
 * ### Notes ###
 * This source code may not follow conventions for java. But mostly the ones for the .NET Framework
 */

import java.util.ArrayList;

import apoSkunkman.ai.*;

public class creeperman extends ApoSkunkmanAI {
	private boolean waiting=false;
	private AStern pathprovider;
	private SkunkWatcher skunkwatcher;
	private PathList path=new PathList();
	private GoodieWatcher goodiewatcher;
	private EnemyWatcher enemywatcher;
	
	private ArrayList<IWatcher> watcherlist =new ArrayList<IWatcher>();
	
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
				watcherlist.clear();
				pathprovider=new AStern(level,player);
				skunkwatcher=new SkunkWatcher();
				goodiewatcher=new GoodieWatcher();
				watcherlist.add(goodiewatcher);
				watcherlist.add(skunkwatcher);
				newPath(level,player);
			}
			if(player.isMoving()==false){
				pathprovider.setProperties(level, player);
				nextMove(level,player);
			}
		} else {
			//Make yourself the target
			if (level.getStartTime()==level.getTime()){
				watcherlist.clear();
				skunkwatcher=new SkunkWatcher();
				pathprovider=new AStern(level,player);
				goodiewatcher=new GoodieWatcher();
				enemywatcher=new EnemyWatcher();
				watcherlist.add(enemywatcher);
				watcherlist.add(goodiewatcher);
				watcherlist.add(skunkwatcher);
			}
			if(player.isMoving()==false){
				pathprovider.setProperties(level, player);
				nextMove(level,player);
			}
		}
	}
	
	public void nextMove(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player){
		//try{
		for (IWatcher item:watcherlist)
			item.watch(level, player, this);
		
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
						if (point==ApoSkunkmanAIConstants.LEVEL_BUSH){
							player.laySkunkman();
							path.SetPositionOneBack();
						}
							
					if (point==ApoSkunkmanAIConstants.LEVEL_SKUNKMAN);
					}
					if (point==ApoSkunkmanAIConstants.LEVEL_STONE){
						if (enemywatcher!=null)
							enemywatcher.ForceNewPath(level, player, this);
					}
				}
			}else{
				//newPath(level,player);
			}
		/*}catch (Exception e){
			System.out.println(e.toString()+" -by NextMove");
		}*/
	}
	
	public void newPath(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player){
		if (level.getType() == ApoSkunkmanAIConstants.LEVEL_TYPE_GOAL_X) {
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
		}else{
			if (enemywatcher!=null)
				enemywatcher.FindPath(level, player, this);
		}
	}

	public boolean DontMove(){
		return waiting;
	}
	
	/*
	 * true for waiting
	 * false for moving
	 */
	public void SetWaiting(boolean value)
	{
		waiting=value;
	}
	
	public APoint GetCurrentPosition(ApoSkunkmanAIPlayer player){
		return new APoint(player.getPlayerX(),player.getPlayerY());
	}
	
	public APoint GetCurrentPosition(ApoSkunkmanAIEnemy player){
		return new APoint((int)player.getX(),(int)player.getY());
	}
	
	public PathList GetPathList(){
		return path;
	}

}

/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

import java.util.ArrayList;

import apoSkunkman.ai.ApoSkunkmanAIConstants;
import apoSkunkman.ai.ApoSkunkmanAIEnemy;
import apoSkunkman.ai.ApoSkunkmanAILevel;
import apoSkunkman.ai.ApoSkunkmanAIPlayer;

public class EnemyWatcher implements IWatcher {
	ApoSkunkmanAIEnemy lastenemy;
	public EnemyWatcher(){
		
	}
	
	@Override
	public void watch(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player,	creeperman main) {
		if (main.GetPathList().IsEmpty()){
			FindPath(level,player,main);
		}
		ApoSkunkmanAIEnemy[] gegner = level.getEnemies();
		for (ApoSkunkmanAIEnemy enemy : gegner) {
			if (enemy.isVisible()) {
				//Get difference and lay skunk, if enemy is at range
				APoint current = main.GetCurrentPosition(player);
				APoint pointenemy=main.GetCurrentPosition(enemy);
				if (current.Gety()==pointenemy.Gety()){
					int difx=Math.abs(current.Getx()-pointenemy.Getx());
					if (difx<player.getSkunkWidth()){
						player.laySkunkman();
					}
				}
				if (current.Getx()==pointenemy.Getx()){
					int dify=Math.abs(current.Gety()-pointenemy.Gety());
					if (dify<player.getSkunkWidth()){
						player.laySkunkman();
					}
				}
			}
		}
	}
	
	public void FindPath(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player, creeperman main){
		// ArrayList<KeyValuePair> enemyList = new ArrayList<KeyValuePair>();
		
		/*
		 * Try to find a way, that the positions are like this: Skunk - Enemy - Bush/Stone
		 * So target is not the enemy's position 
		 */
		ApoSkunkmanAIEnemy[] gegner = level.getEnemies();
		AStern pathprovider = new AStern(level, player);
		APoint current = main.GetPathList().GetActualPoint();
		if (current.equals(APoint.errorPoint())
				|| current.equals(APoint.waitPoint()))
			current = main.GetCurrentPosition(player);
		for (ApoSkunkmanAIEnemy enemy : gegner) {
			if (enemy.isVisible()) {
				lastenemy = enemy;
				break;
			}
		}

		if (main.GetPathList().IsTargetNearPoint(new APoint((int) lastenemy.getX(), (int) lastenemy.getY())) == false && (level.getLevelAsByte()[(int) lastenemy.getY()][(int) lastenemy.getX()] != ApoSkunkmanAIConstants.LEVEL_STONE)) {
			APoint target=new APoint((int) lastenemy.getX(), (int) lastenemy.getY());
			boolean pointset=false;
			//right
			int levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()][(int) lastenemy.getX()+1];
			if (levelbyte== ApoSkunkmanAIConstants.LEVEL_STONE||levelbyte== ApoSkunkmanAIConstants.LEVEL_BUSH){
				levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()][(int) lastenemy.getX()-1];
				if (levelbyte== ApoSkunkmanAIConstants.LEVEL_FREE){
					target=new APoint(target,-1,0);
					pointset=true;
				}
			}
			
			//left
			levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()][(int) lastenemy.getX()-1];
			if (!pointset && (levelbyte== ApoSkunkmanAIConstants.LEVEL_STONE||levelbyte== ApoSkunkmanAIConstants.LEVEL_BUSH) ){
				levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()][(int) lastenemy.getX()+1];
				if (levelbyte== ApoSkunkmanAIConstants.LEVEL_FREE){
					target=new APoint(target,1,0);
					pointset=true;
				}
			}
			
			//top
			levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()-1][(int) lastenemy.getX()];
			if (!pointset && (levelbyte== ApoSkunkmanAIConstants.LEVEL_STONE||levelbyte== ApoSkunkmanAIConstants.LEVEL_BUSH) ){
				levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()+1][(int) lastenemy.getX()];
				if (levelbyte== ApoSkunkmanAIConstants.LEVEL_FREE){
					target=new APoint(target,0,+1);
					pointset=true;
				}
			}
			
			//bottom
			levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()+1][(int) lastenemy.getX()];
			if (!pointset && (levelbyte== ApoSkunkmanAIConstants.LEVEL_STONE||levelbyte== ApoSkunkmanAIConstants.LEVEL_BUSH) ){
				levelbyte=level.getLevelAsByte()[(int) lastenemy.getY()-1][(int) lastenemy.getX()];
				if (levelbyte== ApoSkunkmanAIConstants.LEVEL_FREE){
					target=new APoint(target,0,-1);
				}
			}
			
			ArrayList<APoint> path = pathprovider.FindPath(current, target);
			main.GetPathList().ClearList();
			main.GetPathList().AddPoints(path);
		}
	}
	
	public void ForceNewPath(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player,	creeperman main){
		APoint current=main.GetPathList().GetActualPoint();
		if (current.equals(APoint.errorPoint())||current.equals(APoint.waitPoint()))
			current=main.GetCurrentPosition(player);
		AStern pathprovider=new AStern(level,player);
		ArrayList<APoint> path = pathprovider.FindPath(current,new APoint((int) lastenemy.getX(), (int) lastenemy.getY()));
		if (lastenemy!=null){
			main.GetPathList().ClearList();
			main.GetPathList().AddPoints(path);
		}
	}
}

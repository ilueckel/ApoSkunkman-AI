import apoSkunkman.ai.ApoSkunkmanAIConstants;
import apoSkunkman.ai.ApoSkunkmanAILevel;
import apoSkunkman.ai.ApoSkunkmanAIPlayer;

/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

public class GoodieWatcher implements IWatcher {
	public GoodieWatcher() {

	}

	public void watch(ApoSkunkmanAILevel level,ApoSkunkmanAIPlayer player, creeperman main) {
		APoint current = main.GetPathList().GetActualPoint();
		// Just check one field nearby
		// Byte between 1 and 4 is good; 5 to 8 is bad

		if (current != APoint.errorPoint()) {
			try {
				if (level.getLevelAsByte()[Math.max(current.Gety() + 1, 1)][Math.max(current.Getx(),1)]==ApoSkunkmanAIConstants.LEVEL_GOODIE){
					// bottom field
					byte field = level.getLevelAsByte()[current.Gety() + 1][current.Getx()];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						if (!main.GetPathList().Contains(new APoint(current, 0, 1))){
							main.GetPathList().InsertPoint(current);
							main.GetPathList().InsertPoint(new APoint(current, 0, 1));
						}
					}

					// top field
					field = level.getLevelAsByte()[current.Gety() - 1][current.Getx()];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						if (!main.GetPathList().Contains(new APoint(current, 0, -1))){
							main.GetPathList().InsertPoint(current);
							main.GetPathList().InsertPoint(new APoint(current, 0, -1));
						}
					}

					// right field
					field = level.getLevelAsByte()[current.Gety()][current.Getx() + 1];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						if (!main.GetPathList().Contains(new APoint(current, 1, 0))){
							main.GetPathList().InsertPoint(current);
							main.GetPathList().InsertPoint(new APoint(current, 1, 0));
						}
					}

					// left field
					field = level.getLevelAsByte()[current.Gety()][current.Getx() - 1];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						if (!main.GetPathList().Contains(new APoint(current, -1, 0))){
							main.GetPathList().InsertPoint(current);
							main.GetPathList().InsertPoint(new APoint(current, -1, 0));
						}
					}
				}
			} catch (Exception e){
			}
		}
	}
}

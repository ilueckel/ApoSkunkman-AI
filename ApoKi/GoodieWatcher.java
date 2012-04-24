import apoSkunkman.ai.ApoSkunkmanAIConstants;
import apoSkunkman.ai.ApoSkunkmanAILevel;

/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

public class GoodieWatcher {
	public GoodieWatcher() {

	}

	public void watch(ApoSkunkmanAILevel level, creeperman main) {
		APoint current = main.GetPathList().GetActualPoint();
		// Just check one field nearby
		// Byte between 1 and 4 is good; 5 to 8 is bad

		if (current != APoint.errorPoint()) {
			try {
				if (level.getLevelAsByte()[current.Gety() + 1][current.Getx()]==ApoSkunkmanAIConstants.LEVEL_GOODIE){
					// bottom field
					byte field = level.getLevelAsByte()[current.Gety() + 1][current.Getx()];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						main.GetPathList().InsertPoint(current);
						main.GetPathList().InsertPoint(new APoint(current, 0, 1));
					}

					// top field
					field = level.getLevelAsByte()[current.Gety() - 1][current.Getx()];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						main.GetPathList().InsertPoint(current);
						main.GetPathList().InsertPoint(new APoint(current, 0, -1));
					}

					// right field
					field = level.getLevelAsByte()[current.Gety()][current.Getx() + 1];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						main.GetPathList().InsertPoint(current);
						main.GetPathList().InsertPoint(new APoint(current, 1, 0));
					}

					// left field
					field = level.getLevelAsByte()[current.Gety()][current.Getx() - 1];
					if (field == ApoSkunkmanAIConstants.GOODIE_GOOD_FAST
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_GOD
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN
							|| field == ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH) {
						main.GetPathList().InsertPoint(current);
						main.GetPathList().InsertPoint(new APoint(current, -1, 0));
					}

				}
			} catch (Exception e){
			}
		}
	}

}

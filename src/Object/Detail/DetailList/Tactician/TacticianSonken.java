package Object.Detail.DetailList.Tactician;

import Application.Application;
import Application.Define;
import Object.Detail.DetailBase;

public class TacticianSonken extends DetailBase{

	// コンストラクタ
	public TacticianSonken( boolean isMy ){

		super( isMy );
	}


	// プレイ
	public void play(){

		int num = mHP > 15 ? 1 : 2;

		mSelectCharacter.damage( num );
	}

	// 回復
	public void care( int c ){

		mHP = c;
	}

	// 条件
	public boolean useCondition(){

		if( !condition() ){

			Application.getStringLabel().setType( Define.STRING_TYPE.SELECT_ENEMY );
			Application.getStringLabel().setPos();
			return false;
		}

		return true;
	}

	// 条件
	private boolean condition(){

		// 敵を1体選択しているか
		return getConditionTorS( false );
	}
}

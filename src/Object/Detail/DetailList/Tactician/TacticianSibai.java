package Object.Detail.DetailList.Tactician;

import Object.Detail.DetailBase;

public class TacticianSibai extends DetailBase{

	// コンストラクタ
	public TacticianSibai( boolean isMy ){

		super( isMy );
	}

	// プレイ
	public void play(){

		int num = mHP > 15 ? 1 : 2;

		mSelectCharacter.powerChange( -num );
	}

	// 回復
	public void care( int c ){

		mHP = c;
	}

	// 条件
	public boolean useCondition(){

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

package Object.Detail.DetailList.Go;

import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GoTerror extends DetailBase{

	// コンストラクタ
	public GoTerror( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// プレイ
	public void play(){

		if( mIsPlay ) return;

		mIsPlay = true;

		mSelectCharacter.damage( 99999 );
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

package Object.Detail.DetailList.Syoku;

import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class SyokuGigantic extends DetailBase{

	// コンストラクタ
	public SyokuGigantic( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// プレイ
	public void play(){

		mSelectCharacter.care( 3 );

		mSelectCharacter.powerChange( 3 );
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 味方兵士を1体選択しているか
		return getConditionS( true );
	}
}

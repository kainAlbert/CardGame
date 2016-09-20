package Object.Detail.DetailList.Gi;

import Application.GSvector2;
import Object.Card.Card;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GiWeaken extends DetailBase{

	// コンストラクタ
	public GiWeaken( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// プレイ
	public void play(){

		DetailBase detail = ((Card)mSelectCharacter).getDetail();

		((DetailBase)detail).setRevisionAttack( -mAttack );
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

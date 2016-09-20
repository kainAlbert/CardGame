package Object.Detail.DetailList.Gi;

import Application.Define;
import Application.GSvector2;
import Object.Card.Card;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GiKakoton extends DetailBase{

	// コンストラクタ
	public GiKakoton( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.BATTLECRY;
	}

	// プレイ
	public void play(){

		if( mIsPlay ) return;

		mPlayTimer--;

		if( mPlayTimer > 0 ) return;

		mIsPlay = true;

		DetailBase detail = ((Card)mSelectCharacter).getDetail();

		((DetailBase)detail).setRevisionAttack( -5 );
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

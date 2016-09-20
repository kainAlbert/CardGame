package Object.Detail.DetailList.Go;

import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GoSonsyoko extends DetailBase{

	// コンストラクタ
	public GoSonsyoko( boolean isMy ) {

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

		mSelectCharacter.damage( mAttack );
	}


	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵を1体選択しているか
		return getConditionTorS( false );
	}
}

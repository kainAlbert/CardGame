package Object.Detail.DetailList.Go;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GoSekihekiBigFire extends DetailBase{

	// コンストラクタ
	public GoSekihekiBigFire( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// プレイ
	public void play(){

		if( mIsPlay ) return;

		mIsPlay = true;

		// タイプ
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// 呪文ダメージ+1の数だけ威力を上げる
		int revision = Application.getObj().getCardManager( mIsMy ).searchAbilityNum( Define.CARD_ABILITY.SPELL, type );

		mSelectCharacter.damage( mAttack + revision );
	}


	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵を1体選択しているか
		return getConditionTorS( false );
	}
}

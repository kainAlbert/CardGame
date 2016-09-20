package Object.Detail.DetailList.Gi;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GiAblation extends DetailBase{

	// コンストラクタ
	public GiAblation( boolean isMy ) {

		super( isMy );

		mSelectCharacter = null;
	}

	// プレイ
	public void play(){

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

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

package Object.Detail.DetailList.Other;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Card.CardManager;
import Object.Card.DeckCard;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class OtherArrowShot extends DetailBase{

	// コンストラクタ
	public OtherArrowShot( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		// タイプ
		Define.CARD_TYPE fieldType = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// 呪文ダメージ+1の数だけ威力を上げる
		int revision = Application.getObj().getCardManager( mIsMy ).searchAbilityNum( Define.CARD_ABILITY.SPELL, fieldType );

		mSelectCharacter.damage( mAttack + revision );

		if( !mIsMy ) return;

		// タイプ取得
		Define.CARD_TYPE handType = mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// 手札が最大なら終了
		int handNum = cm.searchTypeNum( handType );

		if( handNum >= Define.MAX_HAND_CARD ) return;

		// カードを引く
		((DeckCard)cm.getDeck()).drawCard();
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵を1体選択しているか
		return getConditionTorS( false );
	}
}

package Object.Detail.DetailList.Tactician;

import Application.Application;
import Application.Define;
import Object.Card.CardManager;
import Object.Card.DeckCard;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Detail.DetailBase;

public class TacticianSyokaturyo extends DetailBase{

	// コンストラクタ
	public TacticianSyokaturyo( boolean isMy ){

		super( isMy );
	}


	// プレイ
	public void play(){

		int damage = mHP > 15 ? 2 : 0;

		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		// ダメージを受ける
		((Tactician)tactician).damage( damage );

		if( !mIsMy ) return;

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// 手札が最大なら終了
		int handNum = cm.searchTypeNum( type );

		if( handNum >= Define.MAX_HAND_CARD ) return;

		// カードを引く
		((DeckCard)cm.getDeck()).drawCard();
	}

	// 回復
	public void care( int c ){

		mHP = c;
	}
}

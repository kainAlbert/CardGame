package Object.Detail.DetailList.Go;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Card.CardManager;
import Object.Card.DeckCard;
import Object.Detail.DetailBase;

public class GoRikuson extends DetailBase{


	// コンストラクタ
	public GoRikuson( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.BATTLECRY;
	}

	// プレイ
	public void play(){

		if( !mIsMy ) return;

		if( mIsPlay ) return;

		mPlayTimer--;

		if( mPlayTimer > 0 ) return;

		mIsPlay = true;

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
}

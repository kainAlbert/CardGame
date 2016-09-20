package Object.Detail.DetailList.Go;

import Application.Application;
import Application.Define;
import Object.Card.CardManager;
import Object.Card.DeckCard;
import Object.Detail.DetailBase;

public class GoIntellect extends DetailBase{


	// コンストラクタ
	public GoIntellect( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		if( !mIsMy ) return;

		if( mIsPlay ) return;

		mIsPlay = true;

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		for( int i=0; i<2; i++ ){

			// 手札が最大なら終了
			int handNum = cm.searchTypeNum( type );

			if( handNum >= Define.MAX_HAND_CARD ) return;

			// カードを引く
			((DeckCard)cm.getDeck()).drawCard();
		}
	}
}

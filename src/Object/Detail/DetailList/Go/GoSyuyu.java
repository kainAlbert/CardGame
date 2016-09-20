package Object.Detail.DetailList.Go;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Card.CardManager;
import Object.Card.HandCard;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GoSyuyu extends DetailBase{


	// コンストラクタ
	public GoSyuyu( boolean isMy ) {

		super( isMy );
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

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		for( int i=0; i<2; i++ ){

			// 手札が最大なら終了
			int handNum = cm.searchTypeNum( type );

			if( handNum >= Define.MAX_HAND_CARD ) return;

			// 手札に赤壁の大火を加える
			CharacterBase card = new HandCard( mIsMy );
			((HandCard)card).initialize( DefineCardID.SEKIHEKI_LARGE_FIRE, new GSvector2( Define.WINDOW_SIZE.x / 2, Define.WINDOW_SIZE.y / 2 ) );

			cm.addCardList( card );
		}
	}
}

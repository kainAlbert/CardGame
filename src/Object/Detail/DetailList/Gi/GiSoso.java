package Object.Detail.DetailList.Gi;

import java.util.List;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Card.Card;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GiSoso extends DetailBase{

	// コンストラクタ
	public GiSoso( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.BATTLECRY;
		mAbility2 = Define.CARD_ABILITY.CHARGE;
	}

	// プレイ
	public void play(){

		if( mIsPlay ) return;

		mPlayTimer--;

		if( mPlayTimer > 0 ) return;

		mIsPlay = true;

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// リスト取得
		List<CharacterBase> list = Application.getObj().getCardManager( mIsMy ).getCardList();

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != type.ordinal() ) continue;

			DetailBase detail = ((Card)list.get(i)).getDetail();

			((DetailBase)detail).setRevisionAttack( 2 );
		}
	}
}

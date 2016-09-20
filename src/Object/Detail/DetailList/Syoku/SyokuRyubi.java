package Object.Detail.DetailList.Syoku;

import java.util.List;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Card.Card;
import Object.Card.CardManager;
import Object.Card.DeckCard;
import Object.Card.SoldierCard;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class SyokuRyubi extends DetailBase{

	// コンストラクタ
	public SyokuRyubi( boolean isMy ) {

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

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		putField( cm.getCardList(), type, true );
		putField( ((DeckCard)cm.getDeck()).getList(), type, false );
	}

	// リストから場に出す
	private void putField( List<CharacterBase> list, Define.CARD_TYPE type, boolean isCardPos ){

		for( int i=0; i<list.size(); i++ ){

			CharacterBase card = list.get(i);
			DetailBase detail = ((Card)card).getDetail();
			GSvector2 pos = isCardPos ? new GSvector2( card.getPos().x, card.getPos().y ) : new GSvector2( mPos.x, mPos.y );

			// すでに出ていたら何もしない
			if( card.getType() == type.ordinal() ) continue;

			if( detail.getCardID() == DefineCardID.KANU || detail.getCardID() == DefineCardID.TYOHI ){

				CharacterBase c = new SoldierCard( mIsMy );

				((SoldierCard)c).initialize( detail, pos );

				// リストに追加
				Application.getObj().getCardManager( mIsMy ).addCardList( c );

				list.remove(i);
				continue;
			}
		}
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// タイプ
		Define.CARD_TYPE myHand = mIsMy ? Define.CARD_TYPE.MYHAND : Define.CARD_TYPE.ENEMYHAND;

		// カード管理者取得
		CardManager cm = Application.getObj().getCardManager( mIsMy );

		int callNum = getNum( cm.getCardList(), myHand ) + getNum( ((DeckCard)cm.getDeck()).getList(), Define.CARD_TYPE.NONE );

		// タイプ取得
		Define.CARD_TYPE myField = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		int fieldNum = Application.getObj().getCardManager( mIsMy ).searchTypeNum( myField );

		return fieldNum <= Define.MAX_FIELD_CARD - callNum - 1;
	}

	// リスト内の関羽と張飛の数を返す
	private int getNum( List<CharacterBase> list, Define.CARD_TYPE type ){

		int num = 0;

		for( int i=0; i<list.size(); i++ ){

			CharacterBase c = list.get(i);
			DetailBase d = ((Card)c).getDetail();

			if( c.getType() != type.ordinal() ) continue;

			if( d.getCardID() == DefineCardID.KANU || d.getCardID() == DefineCardID.TYOHI ){

				num++;
			}
		}

		return num;
	}
}

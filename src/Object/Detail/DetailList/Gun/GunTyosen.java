package Object.Detail.DetailList.Gun;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Card.DeckCard;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GunTyosen extends DetailBase{

	// コンストラクタ
	public GunTyosen( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.DEATHRATTLE;
		mAbility2 = Define.CARD_ABILITY.TAUNT;
	}

	// 死亡
	public void finish(){

		// 呂布を引く
		CharacterBase deck = Application.getObj().getCardManager( mIsMy ).getDeck();

		((DeckCard)deck).drawCard( DefineCardID.RYOHU );
	}
}

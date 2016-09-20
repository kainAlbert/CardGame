package Object.Detail.DetailList.Gun;

import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Object.Detail.DetailBase;

public class GunKojun extends DetailBase{

	// コンストラクタ
	public GunKojun( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.DEATHRATTLE;
	}

	// 死亡
	public void finish(){

		// 中級歩兵を召喚
		createSoldier( DefineCardID.MIDIUM_INFANTRY, mIsMy);
	}
}

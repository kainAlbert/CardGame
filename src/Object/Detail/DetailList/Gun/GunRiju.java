package Object.Detail.DetailList.Gun;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GunRiju extends DetailBase{

	// コンストラクタ
	public GunRiju( boolean isMy ) {

		super( isMy );
	}

	// 初期化
	public void initialize( int cardID, GSvector2 pos, GSvector2 size, int type ){

		super.initialize(cardID, pos, size, type);

		mAbility1 = Define.CARD_ABILITY.DEATHRATTLE;
	}

	// 死亡
	public void finish(){

		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		tactician.care( 3 );
	}
}

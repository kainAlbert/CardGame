package Object.Detail.DetailList.Syoku;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Detail.DetailBase;

public class SyokuGien extends DetailBase{

	// コンストラクタ
	public SyokuGien( boolean isMy ) {

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

		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		((Tactician)tactician).care( 5 );
	}
}

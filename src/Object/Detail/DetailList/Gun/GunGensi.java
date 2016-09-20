package Object.Detail.DetailList.Gun;

import java.util.List;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;
import Object.Detail.DetailBase;

public class GunGensi extends DetailBase{

	// コンストラクタ
	public GunGensi( boolean isMy ) {

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

		mSelectCharacter.damage( 99999 );

		// タイプ取得
		Define.CARD_TYPE type = mIsMy ? Define.CARD_TYPE.MYFIELD : Define.CARD_TYPE.ENEMYFIELD;

		// リストを取得
		List<CharacterBase> list = Application.getObj().getCardManager( mIsMy ).getCardList();

		for( int i=0; i<list.size(); i++ ){

			if( list.get(i).getType() != type.ordinal() ) continue;

			list.get(i).damage( 2 );
		}
	}

	// 条件
	public boolean useCondition( GSvector2 mousePos, CharacterBase tactician, boolean isHand  ){

		// 親クラス条件
		if( !super.useCondition(mousePos, tactician, isHand) ) return false;

		// 敵兵士を1体選択しているか
		return getConditionS( false );
	}
}

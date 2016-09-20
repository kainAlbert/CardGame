package Object.Character;

import Application.Define;
import Application.GSvector2;

public class NumLabel extends CharacterBase{

	// コンストラクタ
	public NumLabel(){

		super();
	}

	// 初期化
	public void initialize( int type, double size ){

		String[] fileName = {
			"numHP", "numCost", "numAttack", "numHP"
		};

		super.initialize(
				fileName[ type ],
				new GSvector2( -1000, -1000 ),
				new GSvector2( size, size ),
				new GSvector2( Define.CARD_NUM_IMAGE_RESIZE.x, Define.CARD_NUM_IMAGE_RESIZE.y ),
				0, type);
	}

	// 更新
	public void updateNum( int num, GSvector2 pos ){

		mPos = pos;

		mReSize.x = ( num + 1 ) * Define.CARD_NUM_IMAGE_RESIZE.x;
		mReSize.x = Math.min( mReSize.x, Define.CARD_NUM_IMAGE_RESIZE.x * 32 );
	}
}

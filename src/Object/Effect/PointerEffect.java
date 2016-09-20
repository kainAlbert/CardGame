package Object.Effect;

import Application.Define;
import Application.Direction;
import Application.GSvector2;
import Object.Character.CharacterBase;

public class PointerEffect extends CharacterBase{

	private GSvector2 mFirstPos;
	private GSvector2 mTargetPos;
	final private GSvector2 RESET_POS = new GSvector2( -1000, -1000 );

	// コンストラクタ
	public PointerEffect(){

		super();
	}

	// 初期化
	public void initialize(){

		super.initialize("pointer",
				new GSvector2(),
				new GSvector2(),
				new GSvector2( Define.POINTER_EFFECT_RESIZE.x, Define.POINTER_EFFECT_RESIZE.y ),
				0, 0 );

		mFirstPos = new GSvector2( RESET_POS.x, RESET_POS.y );
		mTargetPos = new GSvector2( RESET_POS.x, RESET_POS.y );
	}

	// 更新
	public void update(){

		// 初期位置およびターゲットの設定がなければ小さくしておく
		if( mFirstPos.x == RESET_POS.x || mTargetPos.x == RESET_POS.x ){

			mSize.x = 0;
			return;
		}

		// サイズ設定
		mSize.x = Define.POINTER_EFFECT_SIZE_X;
		mSize.y = Direction.getDistance( mFirstPos, mTargetPos );

		// 位置設定
		double revisionX = ( mFirstPos.x - mTargetPos.x ) / 2;
		double revisionY = ( mFirstPos.y - mTargetPos.y ) / 2;

		mPos.x = mTargetPos.x - Define.POINTER_EFFECT_SIZE_X / 2 + revisionX;
		mPos.y = mTargetPos.y - mSize.y / 1.9 + revisionY;


		// 角度設定
		GSvector2 vel = Direction.getToVelocity( mFirstPos, mTargetPos );

		mAngle = Direction.getToVelocity( vel ) + 90;
	}

	// 初期位置設定
	public void setFirstPos( GSvector2 pos ){

		mFirstPos = pos;
	}

	// リセット
	public void reset(){

		mFirstPos = new GSvector2( RESET_POS.x, RESET_POS.y );
		mTargetPos = new GSvector2( RESET_POS.x, RESET_POS.y );
	}

	// 指定先設定
	public void setTargetPos( GSvector2 pos ){

		mTargetPos = pos;
	}
}

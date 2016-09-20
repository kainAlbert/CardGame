package Object.Character;

import Application.Define;
import Application.GSvector2;

public class StringLabel extends CharacterBase{

	private int mTimer;

	// コンストラクタ
	public StringLabel(){

		super();
	}

	// 初期化
	public void initialize(){

		super.initialize(
				Define.STRING_FILE_NAME,
				new GSvector2( -1000, -1000 ),
				new GSvector2( Define.STRING_SIZE.x, Define.STRING_SIZE.y ),
				new GSvector2( Define.STRING_RESIZE.x, Define.STRING_RESIZE.y ),
				0, 0 );

		mTimer = 0;
	}

	// 更新
	public void update(){

		if( mTimer <= 0 ) return;

		mTimer--;

		if( mTimer <= 0 ) mPos.x = -1000;
	}

	// タイプ設定
	public void setType( Define.STRING_TYPE type ){

		mReSize.y = Define.STRING_RESIZE.y * ( type.ordinal() + 1 );
	}

	// 位置を移動
	public void movePosX( double moveX ){

		mPos.x += moveX;
	}

	// 位置を設定
	public void setPos( GSvector2 pos ){

		mPos = pos;

		mTimer = Define.STRING_TIME;
	}

	// 真ん中に位置を設定
	public void setPos(){

		mPos = new GSvector2( Define.WINDOW_SIZE.x / 2 - mSize.x / 2, Define.WINDOW_SIZE.y / 2 - mSize.y / 2 );

		mTimer = Define.STRING_TIME;
	}
}

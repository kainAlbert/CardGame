package Application;

import Object.Collision;
import Object.Character.CharacterBase;

public class SelectTactician {

	private CharacterBase[] mTactician;
	private CharacterBase mBack;
	private boolean mIsSelect;
	private int mSelectID;

	// コンストラクタ
	public SelectTactician(){

		mTactician = new CharacterBase[4];

		GSvector2[] pos = {
				new GSvector2( ( Define.WINDOW_SIZE.x / 2 - Define.TACTICIAN_SIZE.x ) / 2, Define.WINDOW_SIZE.y / 2 - Define.TACTICIAN_SIZE.y - 50 ),
				new GSvector2( ( Define.WINDOW_SIZE.x / 2 - Define.TACTICIAN_SIZE.x ) / 2 + Define.WINDOW_SIZE.x / 2, Define.WINDOW_SIZE.y / 2 - Define.TACTICIAN_SIZE.y - 50 ),
				new GSvector2( ( Define.WINDOW_SIZE.x / 2 - Define.TACTICIAN_SIZE.x ) / 2, Define.WINDOW_SIZE.y / 2 + 30 ),
				new GSvector2( ( Define.WINDOW_SIZE.x / 2 - Define.TACTICIAN_SIZE.x ) / 2 + Define.WINDOW_SIZE.x / 2, Define.WINDOW_SIZE.y / 2 + 30 )
		};

		for( int i=0; i<mTactician.length; i++ ){

			mTactician[i] = new CharacterBase();
			mTactician[i].initialize(
					Define.TACTICIAN_IMAGE_NAME[i],
					pos[i],
					new GSvector2( Define.TACTICIAN_SIZE.x, Define.TACTICIAN_SIZE.y ),
					new GSvector2( Define.TACTICIAN_RESIZE.x, Define.TACTICIAN_RESIZE.y ),
					0, 0);
		}

		mBack = new CharacterBase();
		mBack.initialize(
				"selectTactician",
				new GSvector2(),
				new GSvector2( Define.WINDOW_SIZE.x, Define.WINDOW_SIZE.y ),
				new GSvector2( Define.WINDOW_SIZE.x, Define.WINDOW_SIZE.y ),
				0, 0 );

		mIsSelect = false;
	}

	// クリック
	public void click(){

		// マウスの位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		for( int i=0; i<mTactician.length; i++ ){

			if( Collision.isCollisionSquareDot( mTactician[i].getPos(), mTactician[i].getSize(), mousePos ) ){

				Define.TACTICIAN_ID[] id = {
						Define.TACTICIAN_ID.SONKEN, Define.TACTICIAN_ID.SYOKATURYO, Define.TACTICIAN_ID.SIBAI, Define.TACTICIAN_ID.TOTAKU
				};

				mIsSelect = true;

				mSelectID = i;

				Application.getStringLabel().setType( Define.STRING_TYPE.LOAD);
				Application.getStringLabel().setPos();

				Application.getObj().getCharacterManager().setMyTactician( id[i] );

				return;
			}
		}
	}

	// ゲッター
	public CharacterBase getTactician( int i ){ return mTactician[i]; }
	public CharacterBase getBack(){ return mBack; }
	public boolean getIsSelect(){ return mIsSelect; }
	public int getSelectID(){ return mSelectID; }
}

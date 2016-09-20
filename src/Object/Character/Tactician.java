package Object.Character;

import Application.Application;
import Application.Define;
import Application.DefineCardID;
import Application.GSvector2;
import Application.MesgRecvThread;
import Object.Collision;
import Object.Detail.DetailBase;
import Object.Detail.DetailReader;
import Object.Effect.PointerEffect;

public class Tactician extends CharacterBase{

	private DetailBase mDetail;
	private NumLabel mManaLabel;
	private NumLabel mHPLabel;
	private int mMaxHP;
	private int mHP;
	private int mID;
	private int mMaxMana;
	private int mMana;
	private boolean mUsePower;

	// コンストラクタ
	public Tactician( boolean isMy ){

		mManaLabel = new NumLabel();
		mHPLabel = new NumLabel();

		mIsMy = isMy;
	}

	// 初期化
	public void initialize( Define.TACTICIAN_ID id ){

		mHP = Define.TACTICIAN_MAX_HP;
		mMaxHP = Define.TACTICIAN_MAX_HP;
		mID = id.ordinal();
		mMaxMana = 1;
		mMana = 1;
		mUsePower = false;

		GSvector2 pos = mIsMy ? new GSvector2( Define.TACTICIAN_MYPOS.x, Define.TACTICIAN_MYPOS.y ) :
			new GSvector2( Define.TACTICIAN_ENEMYPOS.x, Define.TACTICIAN_ENEMYPOS.y );

		super.initialize(
				Define.TACTICIAN_IMAGE_NAME[ id.ordinal() ],
				pos,
				new GSvector2( Define.TACTICIAN_SIZE.x, Define.TACTICIAN_SIZE.y ),
				new GSvector2( Define.TACTICIAN_RESIZE.x, Define.TACTICIAN_RESIZE.y ),
				0, 0 );

		int[] cardID = {
				DefineCardID.TACTICIAN_SONKEN,
				DefineCardID.TACTICIAN_SYOKATURYO,
				DefineCardID.TACTICIAN_SIBAI,
				DefineCardID.TACTICIAN_TOTAKU
		};

		// Detail生成
		mDetail = DetailReader.getDetail( cardID[ id.ordinal() ], mIsMy );

		// Detail初期化
		mDetail.initialize( cardID[ id.ordinal() ] , mPos, mSize, 0 );

		mManaLabel.initialize( Define.CARD_NUM_TYPE.COST.ordinal(), Define.TACTICIAN_NUM_IMAGE_SIZE );
		mHPLabel.initialize( Define.CARD_NUM_TYPE.HP.ordinal(), Define.TACTICIAN_NUM_IMAGE_SIZE );

		mFieldNumber = Define.MAX_FIELD_CARD;
	}

	// 更新
	public void update(){

		try{

			super.update();

			double hpPosX = mPos.x;
			double manaPosX = mPos.x + mSize.x - Define.TACTICIAN_NUM_IMAGE_SIZE * 1.5;
			double posY = mIsMy ? mPos.y + mSize.y : mPos.y - Define.TACTICIAN_NUM_IMAGE_SIZE;

			mManaLabel.updateNum( mMana, new GSvector2( manaPosX, posY ) );
			mHPLabel.updateNum( mHP, new GSvector2( hpPosX, posY ) );

		}catch( Exception e ){

		}
	}

	// ターン開始時の処理
	public void startTurn(){

		mMaxMana = Math.min( mMaxMana + 1, 10 );
		mMana = mMaxMana;
	}

	// マナ加算
	public void addMaxMana(){

		mMaxMana++;
	}

	// ダメージ
	public void damage( int d ){

		mHP = Math.max( mHP - d, 0 );

		mDamageTimer = Define.DAMAGE_TIME;

		super.damage(d);

		if( mHP <= 0 ) Application.getObj().setEnd();
	}

	// 回復
	public void care( int c ){

		mHP = Math.min( mHP + c, Define.TACTICIAN_MAX_HP );

		super.care(c);
	}

	// マナを消費
	public void useMana( int m ){

		mMana = Math.max( mMana - m, 0 );
	}

	// クリック
	public void click(){

		if( !Collision.isCollisionSquareDot( mPos, mSize, Application.getObj().getMousePos() ) ) return;

		GSvector2 pos = new GSvector2( mPos.x + mSize.x, Define.CARD_EXPLANATION_Y );

		Application.getObj().getCardManager( true ).createExplanation( mDetail.getCardID(), pos, 1 );
	}

	// 選択
	public void select(){

		if( !Collision.isCollisionSquareDot( mPos, mSize, Application.getObj().getMousePos() ) ) return;

		super.select();

		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		GSvector2 pos = new GSvector2( mPos.x + mSize.x / 2, mPos.y + mSize.y / 2 );

		((PointerEffect)p).setFirstPos( pos );

		String msg = Application.getID() + Define.MSG + Define.MSG_POINTER_FIRST + Define.MSG + pos.x + Define.MSG + pos.y;
		MesgRecvThread.outServer( msg );
	}

	// 選択解除
	public void release(){

		if( !mIsSelect ) return;

		super.release();

		// ポインターリセット
		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		((PointerEffect)p).reset();

		String msgPointer = Application.getID() + Define.MSG + Define.MSG_POINTER_RESET;
		MesgRecvThread.outServer( msgPointer );

		// ヒーローパワー使用条件
		if( !useCondition() ) return;

		// ヒーローパワー使用を送信
		CharacterBase selectCharacter = mDetail.getSelectCharacter();

		// 選択先のID
		String fieldNumber = "null";

		// 選択先の敵か味方か
		String isMy = "null";

		if( selectCharacter != null ){

			fieldNumber = String.valueOf( selectCharacter.getFieldNumber() );
			isMy = selectCharacter.getIsMy() ? "false" : "true";
		}

		String msgPower = Application.getID() + Define.MSG + Define.MSG_TACTICIAN_POWER + Define.MSG + fieldNumber + Define.MSG + isMy;
		MesgRecvThread.outServer( msgPower );

		playPower();
	}

	// ヒーローパワー条件
	public boolean useCondition(){

		// マウスが自分と被っていればfalse
		if( Collision.isCollisionSquareDot( mPos, mSize, Application.getObj().getMousePos() ) ) return false;

		// 使用済み
		if( mUsePower ){

			Application.getStringLabel().setType( Define.STRING_TYPE.ALREADY_ATTACK );
			Application.getStringLabel().setPos();
			return false;
		}

		// マナ不足
		if( mMana < Define.TACTICIAN_POWER_MANA ){

			Application.getStringLabel().setType( Define.STRING_TYPE.NOT_MANA );
			Application.getStringLabel().setPos();
			return false;
		}

		return mDetail.useCondition();
	}

	// ヒーローパワープレイ
	public void playPower(){

		// マナを消費
		useMana( Define.TACTICIAN_POWER_MANA );

		// 使用済みにする
		mUsePower = true;

		// 体力設定
		mDetail.care( mHP );

		// プレイ
		mDetail.play();
	}

	// ドラッグ
	public void drag(){

		if( !mIsSelect ) return;

		// マウス位置を取得
		GSvector2 mousePos = Application.getObj().getMousePos();

		CharacterBase p = Application.getObj().getEffectManager().getPointer();

		((PointerEffect)p).setTargetPos( new GSvector2( mousePos.x, mousePos.y ) );

		String msg = Application.getID() + Define.MSG + Define.MSG_POINTER_TARGET + Define.MSG + mousePos.x + Define.MSG + mousePos.y;
		MesgRecvThread.outServer( msg );
	}

	// ヒーローパワーを使用可能にする
	public void usePower(){ mUsePower = false; }

	// 見えなくする
	public void notShow(){ mSize.x = 0; }

	// ゲッター
	public DetailBase getDetail(){ return mDetail; }
	public NumLabel getManaLabel(){ return mManaLabel; }
	public NumLabel getHPLabel(){ return mHPLabel; }
	public int getHP(){ return mHP; }
	public int getID(){ return mID; }
	public int getMana(){ return mMana; }
}

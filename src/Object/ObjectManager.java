package Object;


import java.awt.Point;

import Application.Application;
import Application.Define;
import Application.GSvector2;
import Application.MesgRecvThread;
import Object.Card.CardManager;
import Object.Card.EnemyManager;
import Object.Character.CharacterBase;
import Object.Character.CharacterManager;
import Object.Character.Tactician;
import Object.Effect.EffectManager;
import Object.Effect.ImpactEffect;
import Object.Effect.WinLoseEffect;

public class ObjectManager {

	private Application mApp;
	private CharacterBase mBackGround;
	private CharacterManager mCharacterManager;
	private CardManager mMyCardManager;
	private CardManager mEnemyCardManager;
	private EnemyManager mEnemyManager;
	private Collision mCollision;
	private EffectManager mEffectManager;

	private GSvector2 mMousePos;
	private boolean mIsStart;
	private int mStartTimer;
	private boolean mIsEnd;
	private int mEndTimer;

	// コンストラクタ
	public ObjectManager( Application app ){

		mApp = app;
		mBackGround = new CharacterBase();
		mCharacterManager = new CharacterManager();
		mMyCardManager = new CardManager( true );
		mEnemyCardManager = new CardManager( false );
		mEnemyManager = new EnemyManager();
		mCollision = new Collision();
		mEffectManager = new EffectManager();
	}

	// 初期化
	public void initialize(){

		mBackGround.initialize(
				"backGround",
				new GSvector2(),
				new GSvector2( Define.WINDOW_SIZE.x, Define.WINDOW_SIZE.y ),
				new GSvector2( Define.WINDOW_SIZE.x, Define.WINDOW_SIZE.y ),
				0, 0);

		mCharacterManager.initialize();
		mMyCardManager.initialize();
		mEffectManager.initialize();

		mIsStart = false;
		mStartTimer = 0;
		mIsEnd = false;
		mEndTimer = 0;
	}

	// 更新
	public void update(){

		if( !mIsStart ){

			isNotStart();
			return;
		}

		mCharacterManager.update();
		mMyCardManager.update();
		mEnemyCardManager.update();
		mCollision.update();
		mEffectManager.update();

		// 終了処理
		end();
	}

	// 開始するまで
	private void isNotStart(){

		if( mIsStart ) return;

		mStartTimer++;

		if( mStartTimer < 60 ) return;

		mStartTimer = 0;

		// 軍師を送る
		String msgTactician = Application.getID() + Define.MSG + Define.MSG_SET_TACTICIAN + Define.MSG + Application.getSelectTactician().getSelectID();
		MesgRecvThread.outServer( msgTactician );

		// 準備中の文字
		Application.getStringLabel().setType( Define.STRING_TYPE.PREPARATION);
		Application.getStringLabel().setPos();

		if( Application.getID() != 1 ) return;

		// 開始ターンを送る
		String msgTurn = Application.getID() + Define.MSG + Define.MSG_START_TURN + Define.MSG + ( Application.getTurn().getIsMyTurn() ? "false" : "true" );
		MesgRecvThread.outServer( msgTurn );
	}

	// 開始処理
	public void setStart(){

		mIsStart = true;

		if( Application.getTurn().getIsMyTurn() ){

			Application.getTurn().changeTurnStr();
		}
	}

	// マウス位置を設定
	public void setMousePos( Point mousePos ){

		mMousePos = new GSvector2( mousePos.x, mousePos.y );
	}

	// 終了を設定
	public void setEnd(){

		mEndTimer = Define.END_TIMER;
	}

	// 終了処理
	private void end(){

		if( mIsEnd || mEndTimer <= 0 ) return;

		mEndTimer--;

		for( int i=0; i<Define.END_IMPACT_TIMER.length; i++ ){

			if( mEndTimer == Define.END_IMPACT_TIMER[i] ){

				impactTactician( i );
				impactTactician( i );
				break;
			}
		}

		if( mEndTimer > 0 ) return;

		mIsEnd = true;

		CharacterBase tactician = mCharacterManager.getTactician( true );

		CharacterBase e = new WinLoseEffect( ((Tactician)tactician).getHP() > 0 );

		mEffectManager.addEffectList( e );

		if( ((Tactician)tactician).getHP() > 0 ){

			tactician = mCharacterManager.getTactician( false );
		}

		((Tactician)tactician).notShow();
	}

	// 軍師に爆発を出す
	private void impactTactician( int index ){

		CharacterBase tactician = mCharacterManager.getTactician( true );

		if( ((Tactician)tactician).getHP() > 0 ){

			tactician = mCharacterManager.getTactician( false );
		}

		GSvector2 pos = new GSvector2( Math.random() * tactician.getSize().x + tactician.getPos().x, Math.random() * tactician.getSize().y + tactician.getPos().y );

		double size = Define.TACTICIAN_SIZE.x / 2;

		if( index == Define.END_IMPACT_TIMER.length - 1 ){

			size = Define.TACTICIAN_SIZE.x * 2;

			pos = new GSvector2( tactician.getPos().x + tactician.getSize().x / 2 - size / 2, tactician.getPos().y + tactician.getSize().y / 2 - size / 2 );
		}

		CharacterBase e = new ImpactEffect( pos, size );

		mEffectManager.addEffectList( e );
	}

	// ゲッター
	public Application getApplication(){ return mApp; }
	public CharacterBase getBackGround(){ return mBackGround; }
	public CharacterManager getCharacterManager(){ return mCharacterManager; }
	public CardManager getCardManager( boolean isMy ){

		return isMy ? mMyCardManager : mEnemyCardManager;
	}
	public EnemyManager getEnemyManager(){ return mEnemyManager; }
	public Collision getCollision(){ return mCollision; }
	public EffectManager getEffectManager(){ return mEffectManager; }
	public GSvector2 getMousePos(){ return mMousePos; }
	public boolean getIsStart(){ return mIsStart; }
	public boolean getIsEnd(){ return mIsEnd || mEndTimer > 0; }

}

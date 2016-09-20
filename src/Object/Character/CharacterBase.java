package Object.Character;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Application.Application;
import Application.GSvector2;
import Object.Effect.DamageCareEffect;

public class CharacterBase {

	protected BufferedImage mImage;
	protected String mFileName;
	protected GSvector2 mPos;
	protected GSvector2 mLastPos;
	protected GSvector2 mSize;
	protected GSvector2 mFirstReSize;
	protected GSvector2 mReSize;
	protected double mSpeed;
	protected double mAngle;
	protected int mID;
	protected boolean mIsDead;
	protected int mType;
	protected boolean mIsSelect;
	protected int mDamageTimer;
	protected boolean mIsMy;
	protected int mFieldNumber;

	// コンストラクタ
	public CharacterBase(){

	}

	// 初期化(設定無し)
	public void initialize(){}

	// 初期化
	public void initialize( String fileName, GSvector2 pos, GSvector2 size, GSvector2 resize,  int id, int type ){

		// 画像読み込み
		try{
			mImage = ImageIO.read(new File("img/" + fileName + ".png"));
		}catch( IOException e ){
			e.printStackTrace();
		}

		// メンバ変数の設定
		mFileName = fileName;
		mPos = pos;
		mLastPos = new GSvector2( pos.x, pos.y );
		mSize = new GSvector2( size.x, size.y );
		mFirstReSize = new GSvector2( resize.x, resize.y );
		mReSize = new GSvector2( resize.x, resize.y );
		mSpeed = 1;
		mAngle = 0;
		mID = id;
		mIsDead = false;
		mType = type;
		mIsSelect = false;
		mDamageTimer = 0;
		mFieldNumber = -1;
	}

	// 更新
	public void update(){

		mDamageTimer--;
	}

	// クリック
	public void click(){}

	// 選択
	public void select(){

		mIsSelect = true;
	}

	// 選択解除
	public void release(){

		mIsSelect = false;
	}

	// ドラッグ
	public void drag(){}

	// 衝突
	public void collision(){}

	// 死亡させる
	public void doDead(){ mIsDead = true; }

	// 死亡処理
	public void finish(){}

	// ダメージ
	public void damage( int d ){

		CharacterBase e = new DamageCareEffect( new GSvector2( mPos.x + mSize.x / 2, mPos.y + mSize.y / 2 ), d, true );

		Application.getObj().getEffectManager().addEffectList( e );
	}

	// 回復
	public void care( int c ){

		CharacterBase e = new DamageCareEffect( new GSvector2( mPos.x + mSize.x / 2, mPos.y + mSize.y / 2 ), c, false );

		Application.getObj().getEffectManager().addEffectList( e );
	}

	// パワー変更
	public void powerChange( int changePower ){}

	// リサイズ変更
	public void changeReSizeX( double reSizeX ){

		mReSize.x = reSizeX;
	}

	// フィールド番号設定
	public void setFieldNumber( int number ){ mFieldNumber = number; }

	// ゲッター
	public BufferedImage getImage(){ return mImage; }
	public String getFileName(){ return mFileName; }
	public GSvector2 getPos(){ return mPos; }
	public GSvector2 getLastPos(){ return mLastPos; }
	public GSvector2 getSize(){ return mSize; }
	public GSvector2 getFirstReSize(){ return mFirstReSize; }
	public GSvector2 getReSize(){ return mReSize; }
	public double getAngle(){ return mAngle; }
	public int getID(){ return mID; }
	public boolean getIsDead(){ return mIsDead; }
	public int getType(){ return mType; }
	public boolean getIsSelect(){ return mIsSelect; }
	public int getDamageTimer(){ return mDamageTimer; }
	public boolean getIsMy(){ return mIsMy; }
	public int getFieldNumber(){ return mFieldNumber; }

}

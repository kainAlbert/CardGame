package Object.Effect;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;

public class DamageCareEffect extends CharacterBase{

	private int mTimer;

	// コンストラクタ
	public DamageCareEffect( GSvector2 pos, int num, boolean isDamage ){

		super();

		mTimer = 0;

		super.initialize(
				isDamage ? "numDamage" : "numHP",
				new GSvector2( pos.x - Define.DAMAGECARE_NUM_SIZE / 2, pos.y - Define.DAMAGECARE_NUM_SIZE / 2),
				new GSvector2( Define.DAMAGECARE_NUM_SIZE, Define.DAMAGECARE_NUM_SIZE ),
				new GSvector2( Define.CARD_NUM_IMAGE_RESIZE.x, Define.CARD_NUM_IMAGE_RESIZE.y ),
				0, 0 );

		mReSize.x = ( num + 1 ) * Define.CARD_NUM_IMAGE_RESIZE.x;
		mReSize.x = Math.min( mReSize.x, Define.CARD_NUM_IMAGE_RESIZE.x * 32 );

		mAngle = 45;
	}

	// 更新
	public void update(){

		mTimer ++;

		if( mTimer < Define.DAMAGECARE_NUM_TIME ) return;

		mIsDead = true;
	}
}

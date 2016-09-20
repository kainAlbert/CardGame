package Object.Effect;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;

public class AttackEffect extends CharacterBase{

	private int mTimer;

	// コンストラクタ
	public AttackEffect( GSvector2 pos ){

		super();

		mTimer = 0;

		super.initialize(
				"attackEffect",
				new GSvector2( pos.x - Define.ATTACK_EFFECT_SIZE.x / 2, pos.y - Define.ATTACK_EFFECT_SIZE.y / 2),
				new GSvector2( Define.ATTACK_EFFECT_SIZE.x, Define.ATTACK_EFFECT_SIZE.y ),
				new GSvector2( Define.ATTACK_EFFECT_RESIZE.x, Define.ATTACK_EFFECT_RESIZE.y ),
				0, 0 );
	}

	// 更新
	public void update(){

		mTimer ++;

		if( mTimer < Define.ATTACK_EFFECT_TIME ) return;

		mTimer = 0;

		mReSize.x += Define.ATTACK_EFFECT_RESIZE.x;

		if( mReSize.x > Define.ATTACK_EFFECT_RESIZE.x * 5 ) mIsDead = true;
	}
}

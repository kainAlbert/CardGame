package Object.Character;

import java.util.List;

import Application.Define;

public class CharacterManager {

	private CharacterBase mMyTactician;
	private CharacterBase mEnemyTactician;

	// コンストラクタ
	public CharacterManager(){

		mMyTactician = new Tactician( true );
		mEnemyTactician = new Tactician( false );
	}

	// 初期化
	public void initialize(){

	}

	// 自分の軍師設定
	public void setMyTactician( Define.TACTICIAN_ID id ){

		((Tactician)mMyTactician).initialize( id );
	}

	// 敵の軍師設定
	public void setEnemyTactician( Define.TACTICIAN_ID id ){

		if( ((Tactician)mEnemyTactician).getHP() == Define.TACTICIAN_MAX_HP ) return;

		((Tactician)mEnemyTactician).initialize( id );
	}

	// 更新
	public void update(){

		mMyTactician.update();
		mEnemyTactician.update();
	}

	// リスト更新
	private void updateList( List<CharacterBase> list ){

		for( int i=0; i<list.size(); i++ ){

			list.get(i).update();

			if( !list.get(i).getIsDead() ) continue;

			list.remove(i);
		}
	}

	// ゲッター
	public CharacterBase getTactician( boolean isMy ){

		return isMy ? mMyTactician : mEnemyTactician;
	}

}

package Object.Effect;

import java.util.ArrayList;
import java.util.List;

import Object.Character.CharacterBase;

public class EffectManager {

	private List<CharacterBase> mEffectList;
	private CharacterBase mPointer;

	// コンストラクタ
	public EffectManager(){

		mEffectList = new ArrayList<CharacterBase>();

		// 各エフェクト生成
		mPointer = new PointerEffect();
	}

	// 初期化
	public void initialize(){

		mPointer.initialize();
	}

	// 更新
	public void update(){

		for( int i=0; i<mEffectList.size(); i++ ){

			mEffectList.get(i).update();

			// 死亡処理
			if( !mEffectList.get(i).getIsDead() ) continue;

			mEffectList.get(i).finish();

			mEffectList.remove(i);
		}

		mPointer.update();
	}

	// リストに追加
	public void addEffectList( CharacterBase e ){ mEffectList.add( e ); }

	// ゲッター
	public List<CharacterBase> getEffectList(){ return mEffectList; }
	public CharacterBase getPointer(){ return mPointer; }
}

package Object.Effect;
import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;

public class WinLoseEffect extends CharacterBase{

	// コンストラクタ
	public WinLoseEffect( boolean isWin ){

		super();

		super.initialize(
				isWin ? "win" : "lose",
				new GSvector2( ( Define.WINDOW_SIZE.x - Define.WINLOSE_EFFECT_SIZE.x ) / 2, ( Define.WINDOW_SIZE.y - Define.WINLOSE_EFFECT_SIZE.y ) / 2),
				new GSvector2( Define.WINLOSE_EFFECT_SIZE.x, Define.WINLOSE_EFFECT_SIZE.y ),
				new GSvector2( Define.WINLOSE_EFFECT_RESIZE.x, Define.WINLOSE_EFFECT_RESIZE.y ),
				0, 0 );
	}

	// 更新
	public void update(){}
}

package Object.Detail.DetailList.Syoku;

import Application.Application;
import Object.Character.CharacterBase;
import Object.Character.Tactician;
import Object.Detail.DetailBase;

public class SyokuNature extends DetailBase{

	// コンストラクタ
	public SyokuNature( boolean isMy ) {

		super( isMy );
	}

	// プレイ
	public void play(){

		CharacterBase tactician = Application.getObj().getCharacterManager().getTactician( mIsMy );

		((Tactician)tactician).addMaxMana();

		tactician.care( 2 );
	}
}

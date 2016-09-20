package Object.Card;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Application.Define;
import Application.GSvector2;
import Object.Character.CharacterBase;

public class DeckReader {

	public static List<CharacterBase> readDeck( int tacticianID, boolean isMy, GSvector2 pos ){

		BufferedReader br = null;

		try{
			File file = new File( "txt/" + Define.DECK_FILE_NAME[ tacticianID ] + ".txt" );
			br = new BufferedReader(new FileReader(file));

		}catch( FileNotFoundException e ){
			e.printStackTrace();
		}

		// 読み込んで返す
		return readText( br, tacticianID, isMy, pos );
	}

	// テキストファイルの読み込み
	private static List<CharacterBase> readText( BufferedReader br, int tacticianID, boolean isMy, GSvector2 pos ){

		if( br == null ) return null;

		String str = "";
		String[] item = null;
		List<CharacterBase> list = new ArrayList<CharacterBase>();

		try{
			str = br.readLine();
		}catch( Exception e ){}

		// 最期まで
		while( str != null ){

			try{
				str = br.readLine();

				item = str.split("\t");

				if( Integer.parseInt(item[0]) == -1 ){
					break;
				}

				CharacterBase card = new HandCard( isMy );
				((HandCard)card).initialize( Integer.parseInt(item[0]), new GSvector2( pos.x, pos.y ) );

				list.add( card );

			}catch( Exception e ){

				e.printStackTrace();
			}


		}

		return getRandomList( list );
	}

	// ランダムな順番にする
	private static List<CharacterBase> getRandomList( List<CharacterBase> list ){

		List<CharacterBase> rndList = new ArrayList<CharacterBase>();
		Random rnd = new Random();
		int rndNum = 0;

		while( list.size() != 0 ){

			rndNum = rnd.nextInt( list.size() );

			rndList.add( list.get( rndNum ) );

			list.remove( rndNum );
		}

		return rndList;
	}
}

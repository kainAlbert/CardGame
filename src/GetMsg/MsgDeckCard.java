package GetMsg;

import Application.Application;
import Object.Card.CardManager;
import Object.Card.DeckCard;

public class MsgDeckCard {

	// カードを引く
	public static void drawCard( String[] t ){

		int cardID = Integer.parseInt( t[2] );

		CardManager cm = Application.getObj().getCardManager( false );

		((DeckCard)cm.getDeck()).drawCard( cardID );
	}
}

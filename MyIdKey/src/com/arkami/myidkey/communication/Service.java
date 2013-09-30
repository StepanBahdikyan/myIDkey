/**
 * 
 */
package com.arkami.myidkey.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;

import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.model.KeyCardTypeEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Service {
	private static List<KeyCard> keyCards;
	private static Service service;
	private static Context context;

	/**
	 *  
	 */
	private Service() {
	}

	/**
	 * @return service instance
	 */
	public static Service getInstance(Context context) {
		if (service == null) {
			service = new Service();
			Service.context = context;
		}
		return service;
	}

	/**
	 * 
	 * @return Key cards of the user
	 */
	public List<KeyCard> getKeyCards() {
		// if (keyCards != null) {
		// return keyCards;
		// }

		KeyCardDataSource keyCardDataSource = new KeyCardDataSource(context);
		// if (keyCardDataSource.isEmpty(KeyCard.TABLE_NAME)) {
		//
		// keyCards = new ArrayList<KeyCard>();
		// String[] items = context.getResources().getStringArray(
		// R.array.array_mockup);
		// Random random = new Random();
		// for (String itemName : items) {
		// // KeyCardType type = getRandomType();
		// KeyCard keyCard = new KeyCard();
		// keyCard.setName(itemName);
		// keyCard.setFavourite(getRandomFavourite());
		// keyCard.setKeyCardTypeId((long) random.nextInt(6) + 1);
		// Log.w("key card", keyCard.toString());
		// keyCardDataSource.insert(keyCard);
		// }
		// Log.w("key cards",
		// "......................added..........................");
		// }
		keyCards = keyCardDataSource.getKeyCards();
		return keyCards;
	}

	/**
	 * @return favourite key cards
	 */
	public List<KeyCard> getFavouriteKeyCards() {
		ArrayList<KeyCard> favourites = new ArrayList<KeyCard>();
		for (KeyCard keyCard : getKeyCards()) {
			if (keyCard.isFavourite()) {
				favourites.add(keyCard);
			}
		}
		return favourites;
	}

	/**
	 * @return random type
	 */
	private static KeyCardTypeEnum getRandomType() {
		Random random = new Random();
		switch (random.nextInt(7)) {
		case 1:
			return KeyCardTypeEnum.site;
		case 2:
			return KeyCardTypeEnum.folder;
		case 3:
			return KeyCardTypeEnum.email;
		case 4:
			return KeyCardTypeEnum.photo;
		case 5:
			return KeyCardTypeEnum.note;
		case 6:
			return KeyCardTypeEnum.credit_card;
		default:
			return KeyCardTypeEnum.credit_card;
		}
	}

	/**
	 * @return true or false randomly
	 */
	private static boolean getRandomFavourite() {
		Random random = new Random();
		switch (random.nextInt(3)) {
		case 1:
			return true;
		case 2:
			return false;
		default:
			return true;
		}
	}

	/**
	 * Returns a key card by given id
	 * 
	 * @param id
	 *            long id of the key card
	 * @return key card of that id
	 */
	public KeyCard getKeyCard(long id) {
		for (KeyCard keyCard : getKeyCards()) {
			if (keyCard.getId() == id) {
				return keyCard;
			}
		}
		throw new IllegalArgumentException("No such element with id: " + id);
	}
}

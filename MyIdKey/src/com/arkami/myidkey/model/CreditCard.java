/**
 * 
 */
package com.arkami.myidkey.model;

import java.util.List;

/**
 * @author sbahdikyan
 * @deprecated
 */
public class CreditCard extends KeyCardModel {

	/**
	 * id
	 */
	private static final long serialVersionUID = 1685095604714349528L;

	private List<Long> photos;
	private List<Long> audios;
	private String creditCardName;
	private Long number;
	private String cardVerificationValue;
	private Long expireDate;

	/**
	 * @return the photos
	 */
	public List<Long> getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *            the photos to set
	 */
	public void setPhotos(List<Long> photos) {
		this.photos = photos;
	}

	/**
	 * @return the audios
	 */
	public List<Long> getAudios() {
		return audios;
	}

	/**
	 * @param audios
	 *            the audios to set
	 */
	public void setAudios(List<Long> audios) {
		this.audios = audios;
	}

	/**
	 * @return the creditCardName
	 */
	public String getCreditCardName() {
		return creditCardName;
	}

	/**
	 * @param creditCardName
	 *            the creditCardName to set
	 */
	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	/**
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * @return the cardVerificationValue
	 */
	public String getCardVerificationValue() {
		return cardVerificationValue;
	}

	/**
	 * @param cardVerificationValue
	 *            the cardVerificationValue to set
	 */
	public void setCardVerificationValue(String cardVerificationValue) {
		this.cardVerificationValue = cardVerificationValue;
	}

	/**
	 * @return the expireDate
	 */
	public Long getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(Long expireDate) {
		this.expireDate = expireDate;
	}
}

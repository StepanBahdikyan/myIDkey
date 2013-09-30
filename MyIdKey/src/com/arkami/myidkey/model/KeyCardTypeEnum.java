/**
 *
 */
package com.arkami.myidkey.model;

/**
 * @author sbahdikyan
 */
public enum KeyCardTypeEnum {

    //default
    site("Site"),
    //
    note("Notes"),
    //
    bank_account("Bank Account"),
    //
    clothing("Clothing Sizes"),
    //
    credit_card("Credit Card"),
    //
    database("Database"),
    //
    drivers_license("Driver's License"),
    //
    email("Email Account"),
    //
    form_fill("Form Fill"),
    //
    instant_messenger("Instant Messenger"),
    //
    insurance("Insurance"),
    //
    membership("Membership"),
    //
    passport("Passport"),
    //
    prescription("Prescription"),
    //
    reward_program("Rewards Program"),
    //
    server("Server"),
    //
    social_security("Social Security"),
    //
    software_licence("Software License"),
    //
    vehicle("Vehicle"),
    // Wi-Fi Password
    wifi("Wi-Fi Password"),
    //
    folder("Folder"),
    //
    photo("photo");
    //--------------------


    private String title;

    /**
     * XXX fix titles
     *
     * @param title type of the key card in last pass csv file.
     */
    private KeyCardTypeEnum(String title) {
        this.title = title;
    }

    /**
     * @return CharSequence[] with the names of the values
     */
    public static CharSequence[] getStringValues() {
        CharSequence[] types = new CharSequence[KeyCardTypeEnum.values().length];
        KeyCardTypeEnum[] values = KeyCardTypeEnum.values();
        for (int i = 0; i < types.length; i++) {
            types[i] = values[i].name();
        }
        return types;
    }

    /**
     * Returns a key card with specific title, or key card if there is not such
     * title.
     *
     * @param title
     * @return key card with that title
     */
    public static KeyCardTypeEnum getValueByTitle(String title) {
        KeyCardTypeEnum[] values = KeyCardTypeEnum.values();
        for (KeyCardTypeEnum keyCardTypeEnum : values) {
            if (keyCardTypeEnum.title.equals(title)) {
                return keyCardTypeEnum;
            }
        }
        return KeyCardTypeEnum.site;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

}

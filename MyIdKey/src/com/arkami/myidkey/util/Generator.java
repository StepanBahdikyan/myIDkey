package com.arkami.myidkey.util;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-23
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
/**
 *
 */

import java.util.Random;

/**
 * @author Stepan Bahdikyan
 */
public class Generator {
    /**
     * Default character symbols.
     */

    private static final char[] symbolsAz = "qwertyuiopasdfghjklzxcvbnm"
            .toCharArray();
    private static final char[] symbolsAZ = "qwertyuiopasdfghjklzxcvbnm"
            .toUpperCase().toCharArray();
    private static final char[] symbols09 = "1234567890".toCharArray();
    private static final char[] symbolsSpecial = "~!@#$%^&*()".toCharArray();
    private static final char[] symbolsHexadecimal = "0123456789ABCDEF".toCharArray();
    /**
     *
     * @param hasSpecialSymbols
     * @param hasNumbers
     * @param hasAz
     * @param hasAZ
     * @param length
     * @return
     */
    public static String generate(boolean hasSpecialSymbols, boolean hasNumbers, boolean hasAz,
                                  boolean hasAZ,boolean hasHexadecimal, int length) {
        Random random = new Random();
        StringBuffer returnString = new StringBuffer();

        if(hasHexadecimal){
            while (returnString.length() < length) {
                returnString.insert(0,
                        symbolsHexadecimal[random.nextInt(symbolsHexadecimal.length)]);
            }
            return returnString.toString();
        }

        int numberOfOptions = 0;
        if(hasSpecialSymbols){
           numberOfOptions++;
        }
        if(hasAz){
            numberOfOptions++;
        }
        if(hasAZ){
            numberOfOptions++;
        }
        if(hasNumbers){
            numberOfOptions++;
        }
        if(length<numberOfOptions){
            return returnString.toString();
        }

        boolean reallyHasSpecialSymbols = false;
        boolean reallyHasNumbers = false;
        boolean reallyhasAz = false;
        boolean reallyHasAZ = false;

        while ((reallyHasSpecialSymbols != hasSpecialSymbols)
                ||(reallyHasAZ != hasAZ)
                ||(reallyhasAz != hasAz)
                ||(reallyHasNumbers != hasNumbers)) {

            reallyHasSpecialSymbols = false;
            reallyHasNumbers = false;
            reallyhasAz = false;
            reallyHasAZ = false;
            returnString = new StringBuffer();

            while (returnString.length() < length) {
                switch (random.nextInt(4)) {
                    case 0:

                        if (!hasSpecialSymbols) {
                            break;
                        }
                        returnString.insert(0,
                                symbolsSpecial[random.nextInt(symbolsSpecial.length)]);
//                    specialSymbols;
                        reallyHasSpecialSymbols = true;
                        break;
                    case 1:
                        if (!hasNumbers) {
                            break;
                        }
                        returnString.insert(0,
                                symbols09[random.nextInt(symbols09.length)]);
//                    numbers--;
                        reallyHasNumbers = true;
                        break;
                    case 2:
                        if (!hasAz) {
                            break;
                        }
                        returnString.insert(0,
                                symbolsAz[random.nextInt(symbolsAz.length)]);
//                    az--;
                        reallyhasAz = true;
                        break;
                    case 3:
                        if (!hasAZ) {
                            break;
                        }
                        returnString.insert(0,
                                symbolsAZ[random.nextInt(symbolsAZ.length)]);
//                    AZ--;
                        reallyHasAZ = true;
                        break;
                }
            }
        }
        return returnString.toString();
    }

}

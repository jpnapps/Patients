package com.jpndev.patients.utils

import android.content.res.AssetManager
import java.io.InputStream

/**
 * Common constants which can be used throughout the app
 */
object Common {
    const val JSON_FILE_EXTENSION = ".json"
    const val PDF_FILE_EXTENSION = ".pdf"
    const val SELECTED_ITEM = "SELECTED_ITEM"
    const val BACKUP_FILE = "patients"+ JSON_FILE_EXTENSION
    const val INTENT_TYPE = "text/plain"
    const val INTENT_MAIL_TYPE = "message/rfc822"
    const val BLANK = ""
    const val DELAY_2000 = 2000L
    const val DELAY_500 = 500L
    const val SEMICOLON = ":"
    const val SPACE = " "
    const val SEMICOLON_SPACE= SEMICOLON+ SPACE
}
/**
 * Constant class for Parser Constants
 */
object ParserConstants {
    //Constant for p data file name
    const val P_DATA_FILE = "pdata"
    const val KEY_1 = "key_1"
    const val VALUE_1 = "value_1"
    const val KEY_2 = "key_2"
    const val VALUE_2 = "value_2"
    const val SEMI_COLON = " : "
    const val COMMA = ","
    const val NEWLINE = "\n"
    const val OPEN_BRACES = "{"
    const val END_BRACES = "}"
    const val OPEN_ARRAY_BRACES = "["
    const val END_ARRAY_BRACES = "]"
}

/**
* Constants for digits
*/
object DIGITS {
    //Constant for digits 0
    const val ZERO = 0
    //Constant for digits -1
    const val NEGATIVE_ONE = -1
    //Constant for digits 1
    const val ONE = 1
    //Constant for digits 2
    const val TWO = 2
    //Constant for digits 3
    const val THREE = 3
    //Constant for digits 4
    const val FOUR = 4
    //Constant for digits 5
    const val FIVE = 5
    //Constant for digits 6
    const val SIX = 6
    //Constant for digits 7
    const val SEVEN = 7
    //Constant for digits 8
    const val EIGHT = 8
    //Constant for digits 10
    const val TEN = 10
    //Constant for digits 12
    const val TWELVE = 12
    //Constant for digits 16
    const val SIXTEEN = 16
    // Constant for digits 18
    const val EIGHTEEN = 18
    // Constant for digits 75
    const val SEVENTY_FIVE = 75
    //Constant for digits 2000
    const val TWO_THOUSAND = 2000
    //Constant for digits 10000
    const val TEN_THOUSAND = 10000
    //Constant for digits 0f
    const val ZERO_FLOAT = 0f
    //Constant for digits 0L
    const val ZERO_LONG = 0L
    //Constant for digits 0L
    const val FOURTEEN_LONG = 14L
    //Constant for digits 64
    const val SIXTY_FOUR = 64
    //Constant for digits 50
    const val FIFTY = 50
    // Constant for digit 70
    const val SEVENTY = 70
    //Constant for digits 100
    const val ONE_HUNDRED = 100
    //Constant for digits 300L
    const val THREE_HUNDRED_LONG = 300L
    //Constant for digits 1500L
    const val WELCOME_PAGER_TIMER = 1500L
}

package encryption;
import java.io.PrintWriter; // shorten future calls to PrintWriter

public class ViginereCipher {
  /** Apply the viginere cipher with the given text and key. */
  static char[] viginere (char[] text, char[] key) {
    // check if the user entered an empty string as a key
    if (key.length == 0) return text;
    
    // declare a string to add decrypted characters to
    char[] returnString = new char[text.length];
    
    // loop through the string, shifting values with the key
    for (int i = 0; i < text.length; i ++) {
      // convert the character to base-26, then offset by n
      int n = key[i % key.length] - 'a';
      int convertedCh = ((text[i] - 'a') + n) % 26;

      // put the converted char is in the valid range
      if (convertedCh < 0) convertedCh = convertedCh + 26;
      
      // store the decrypted character in the string
      returnString[i] = (char) (convertedCh + 'a');
    } // for (int i = 0; i < text.length; i ++)

    return returnString;
  } // viginere (char[] text, char[] key)
  
  /** Returns the string given, encrypted/shifted by the key n. */
  static char[] encrypt (char[] plainText, char[] key) { 
    return viginere(plainText, key);
  } // encrypt (char[], char[])
  
  /** Returns the string given, decrypted/shifted by the key n. */
  static char[] decrypt (char[] cipherText, char[] key) {
    // Change the key to the appropriate decryption key
    for (int i = 0; i < key.length; i ++) {
      key[i] = (char) ('a' + (26 - (key[i] - 'a')));
    } // for (each character in the key)
    
    return viginere(cipherText, key);
  } // decrypt (char[], char[])

  public static void main (String[] args) throws Exception {
    // check if user entered the correct number of inputs
    if (args.length == 3) {

      // check if the user entered a valid option
      if ((args[0].equals("encode"))
        || (args[0].equals("decode"))) {

        // define a print writer object directed to stdout
        PrintWriter penOut = new PrintWriter(System.out, true);
        
        // keep track of the encode/decode mode
        Boolean mode = args[0].equals("encode");
        
        // determine the new converted string of the given key
        char[] newText = (mode)
          ? encrypt(args[1].toCharArray(), args[2].toCharArray())
          : decrypt(args[1].toCharArray(), args[2].toCharArray());

        // print out the converted string
        penOut.println(newText);

      } else errorMsg(1); // print out option error message
    } else errorMsg(2); // print out parameter error message
  } // main (String[]) throws Exception

  /** 
   * Prints an error message to stderr according to the errorCode given,
   * then exits with the same error code.
   * @param errorCode a code corresponding to an user input error */
  static void errorMsg (int errorCode) {
    // define a print writer object directed to stderr
    PrintWriter penErr = new PrintWriter(System.err, true);

    // determine the correct error message and output it
    if (errorCode == 1) {
      penErr.println("Valid options are \"encode\" or \"decode\"'");
    } else if (errorCode == 2) {
      penErr.println("Incorrect number of parameters");
    }

    System.exit(errorCode); // exit the program with the error code
  } // errorMsg (int)
} // class ViginereCipher
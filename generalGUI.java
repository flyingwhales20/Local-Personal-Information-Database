//This centralizes reused methods that can be reused in each window
package infodatabase;

public class generalGUI {

    generalGUI() {

    }

    //This method captalizes any words after spaces or if word is at beginning of text!
    //str inherits the string value, strLength gets the length of the text, and
    //dataType determines whether numbers or letters are allowed.
    //      -1 : no preference
    //      0 : nums only
    //      1 : letters only
    public String capitalizeProper(String str, int strLength, int dataType) {
        char[] temp = str.toCharArray();

        //Turn first char (if a letter) and every char after space (if letter)
        //to be upper cased! YAY
        if (strLength > 0) {
            for (int i = 0; i < strLength; i++) {
                if (i == 0) {
                    temp[0] = Character.toUpperCase(temp[0]);
                }
                if (i > 0) {
                    if (temp[i - 1] == ' ') {
                        temp[i] = Character.toUpperCase(temp[i]);
                    }
                }
            }
        }
        if (dataType == 1) {
            return String.valueOf(temp).replaceAll("[^a-z A-Z]", "");
        }
        if (dataType == 0) {
            return String.valueOf(temp).replaceAll("[^0-9]", "");
        }
        return String.valueOf(temp);
    }
    
    //Limit length of textfield input based on parameters
    public String limitLength(String str, int len) {
        String newText;

        if (str.length() >= len) {
            int length = str.length() - len;
            newText = str.substring(0, str.length() - length);
            return newText;
        }
        return str;
    }
}

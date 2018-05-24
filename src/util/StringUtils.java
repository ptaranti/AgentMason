package util;



public class StringUtils {

    /**
     * Check that the given String is neither <code>null</code> nor of length 0.
     * Note: Will return <code>true</code> for a String that purely consists of
     * whitespace.
     * <p>
     * 
     * <pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength(&quot;&quot;) = false
     * StringUtils.hasLength(&quot; &quot;) = true
     * StringUtils.hasLength(&quot;Hello&quot;) = true
     * </pre>
     * 
     * @param str
     *            the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check whether the given String has actual text. More specifically,
     * returns <code>true</code> if the string not <code>null<code>,
     * its length is greater than 0, and it contains at least one non-whitespace character.
     * <p><pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText(&quot;&quot;) = false
     * StringUtils.hasText(&quot; &quot;) = false
     * StringUtils.hasText(&quot;12345&quot;) = true
     * StringUtils.hasText(&quot; 12345 &quot;) = true
     * </pre>
     * 
     * @param str
     *            the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not <code>null</code>, its
     *         length is greater than 0, and is does not contain whitespace only
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }



    /**
     * Verifica se o valor passado combina com a regex.
     * 
     * @param value
     *            string a ser testada.
     * @param regex
     *            express?o regular para testar.
     * @return caso a string passada combine com a express?o regular retorna
     *         <code>true</code>, caso a string ou a express?o sejam nulas ou a
     *         string n?o combine com a express?o retorna <code>false</code>.
     */
    public static boolean matches(String value, String regex) {
        if (value == null || regex == null)
            return false;

        return value.matches(regex);
    }
}
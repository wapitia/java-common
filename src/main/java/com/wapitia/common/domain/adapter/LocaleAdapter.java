package com.wapitia.common.domain.adapter;


import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Convert from XML String to Locale as a Locale language tag.
 *  The language tag should not exceed 35 characters,
 *  according to
 *  <a href=https://tools.ietf.org/html/bcp47#section-4.4.1>
 *    The bcp47 specification
 *  </a>.
 *  As the Locale is converted to the database string, it is systematically
 *  truncated to fit in 35 characters.
 */
public class LocaleAdapter extends XmlAdapter<String, Locale>
{
    /** Maximum length of the marshal string is 35. */
    public static final int LocaleLength = 35;

    /** Convert an Locale to a local language tag String via a call
     *  to {@link Locale#toLanguageTag()}. Allow for null.
     *  The resultant string will not exceed 35 characters.
     *  The result from toLanguageTag will be truncated according to
     *  <a href="https://tools.ietf.org/html/bcp47#section-4.4.2">
     *    bcp47 section 4.4.2</a>
     */
    @Override
    public String marshal(Locale locale) {
        return print(locale);
    }

    /** Convert a String to a Locale via a call to
     *  {@link Locale#forLanguageTag(String)} . Allow for null.
     */
    @Override
    public Locale unmarshal(String localeLanguageTag) {
        return parse(localeLanguageTag);
    }

    /** Truncate language tag according to
     *  <a href="https://tools.ietf.org/html/bcp47#section-4.4.2">
     *    bcp47 section 4.4.2</a>.
     *  This says to truncate the language tag down to some maximum length
     *  by repeatedly stripping off trailing sections that are separated by
     *  "-" until it fits into the {@code maxLength}.
     *  At a {@code maxLength} of 35 none of this should happen.
     *
     *  @param langTag Locale language tag of the form "en-US"
     *  @param maxLength maximum length of return string
     *  @return the incoming {@code langTag}, truncated if too long.
     */
    public static String trunc(String langTag, int maxLength) {
        final String result;
        if (langTag.length() <= maxLength)
            // okay, no truncation necessary.
            result = langTag;
        else if (langTag.contains("-")) {
            // strip away the last dash and everything after it
            String truncated = langTag.substring(0, langTag.lastIndexOf('-'));
            // then truncate single character suffixes, like "-x"
            int lastDash = truncated.lastIndexOf("-");
            if (lastDash == truncated.length() - 2) {
                truncated = truncated.substring(0, lastDash);
            }
            // recurse with truncated value
            result = trunc(truncated, maxLength);
        } else
            // no dash in long length, force truncate. This shouldn't happen.
            result = langTag.substring(0, maxLength);
        return result;
    }

    private static class Holder {
        static LocaleAdapter instance = new LocaleAdapter();
    }

    /** @return the LocaleAdapter singleton instance
     */
    public static LocaleAdapter instance() {
        return Holder.instance;
    }

    /** Parse a xs:string string into its corresponding Locale.
     *  @param localeLanguageTag string in xs:string format.
     *  @return Locale matching localeLanguageTag
     */
    public static Locale parse(String localeLanguageTag) {
        return localeLanguageTag == null ? (Locale) null
            : Locale.forLanguageTag(localeLanguageTag);
    }

    /** Convert the Locale to a xs:string string.
     *  @param locale Locale to covert to string. returns null if this is null
     *  @return the corresponding String, or null if instant is null
     */
    public static String print(Locale locale) {
        return locale == null ? (String) null
            : trunc(locale.toLanguageTag(), LocaleLength);
    }

}

package com.wapitia.common.domain.adapter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class InstantAdapter extends XmlAdapter<String,Instant> {

    private final DateTimeFormatter formatter;

    public InstantAdapter() {

        this.formatter = DateTimeFormatter.ISO_INSTANT;
    }

    @Override
    public Instant unmarshal(String stringValue) {
        return stringValue != null ? formatter.parse(stringValue, Instant::from) : null;
    }

    @Override
    public String marshal(Instant value) {
        return value != null ? formatter.format(value) : null;
    }

    private static class Holder {
        static InstantAdapter instance = new InstantAdapter();
    }

    public static InstantAdapter instance() {
        return Holder.instance;
    }

    public static Instant parse(String stringValue) {
        return instance().unmarshal(stringValue);
    }

    public static String print(Instant value) {
        return instance().marshal(value);
    }
}
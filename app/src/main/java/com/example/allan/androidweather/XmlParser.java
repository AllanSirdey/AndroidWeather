package com.example.allan.androidweather;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Custom XML parser made to list all available Namespaces.
 */


public class XmlParser {

    // We don't use namespaces
    private static final String ns = null;

    public Meteo parse(InputStream in) throws IOException {
        Meteo weather = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readCurrent(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return weather;
    }

    private Meteo readCurrent(XmlPullParser parser) throws XmlPullParserException, IOException {
        double latitude = 0.0;
        double longitude = 0.0;
        double currentTemp = 0.0;
        double maxTemp = 0.0;
        double minTemp = 0.0;
        String description = null;
        String icone = null;

        parser.require(XmlPullParser.START_TAG, ns, "current");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();

            if (tagName.equals("city")) {
                parser.require(XmlPullParser.START_TAG, ns, "city");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String cityChildName = parser.getName();

                    if (cityChildName.equals("coord")) { // Parse coord data
                        //Read coord
                        parser.require(XmlPullParser.START_TAG, ns, "coord");

                        latitude = readDoubleAttribute(parser, "lat");
                        longitude = readDoubleAttribute(parser, "lon");

                        parser.nextTag();
                        parser.require(XmlPullParser.END_TAG, ns, "coord");

                    } else {
                        skip(parser);
                    }
                }

            } else if (tagName.equals("temperature")) { // Parse temperature data
                //Read temperature
                parser.require(XmlPullParser.START_TAG, ns, "temperature");

                currentTemp = readDoubleAttribute(parser, "value");
                maxTemp = readDoubleAttribute(parser, "max");
                minTemp = readDoubleAttribute(parser, "min");

                parser.nextTag();
                parser.require(XmlPullParser.END_TAG, ns, "temperature");

            } else if (tagName.equals("weather")) { // Parse temperature data
                //Read weather
                parser.require(XmlPullParser.START_TAG, ns, "weather");

                description = parser.getAttributeValue(null, "value");
                icone = parser.getAttributeValue(null, "value");

                parser.nextTag();
                parser.require(XmlPullParser.END_TAG, ns, "weather");
            } else {
                skip(parser);
            }
        }

        return new Meteo(latitude, longitude, currentTemp, maxTemp, minTemp, description, icone);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private double readDoubleAttribute(XmlPullParser parser, String attribute) {
        double value = 0.0;
        String strValue = parser.getAttributeValue(null, attribute);
        if(strValue != null) {
            value = Double.parseDouble(strValue);
        }
        return value;
    }

}

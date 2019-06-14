package com.example.newminiproject.Parser;

import com.example.newminiproject.Constances.Constances;
import com.example.newminiproject.Model.News;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser extends DefaultHandler {
    private List<News> arrNews = new ArrayList<>();
    private StringBuilder builder;
    private News item;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (qName == Constances.ITEM){
            item = new News();
        }
        if (qName.equals(Constances.IMAGE)){
            String img = attributes.getValue("url");
            item.setImage(img);
        }

        builder = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        builder.append(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (item == null) return;
        switch (qName){
            case Constances.TITLE:
                item.setTitle(builder.toString());
                break;
            case Constances.DESC:
                String s = builder.toString();
                String v = "target=\"_blank\">";
                int index = s.indexOf(v)+v.length();
                s = s.substring(index);
                s = s.substring(0, s.indexOf("</a>"));
                item.setDesc(s);
                break;
            case Constances.LINK:
                item.setLink(builder.toString());
                break;
            case Constances.PUBDATE:
                item.setPubDate(builder.toString());
                break;
            case Constances.ITEM:
                arrNews.add(item);
                break;
        }
    }

    public List<News> getArrNews() {
        return arrNews;
    }
}

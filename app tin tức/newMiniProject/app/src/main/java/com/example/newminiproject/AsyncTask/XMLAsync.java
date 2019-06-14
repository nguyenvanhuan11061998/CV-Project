package com.example.newminiproject.AsyncTask;

import android.os.AsyncTask;

import com.example.newminiproject.Model.News;
import com.example.newminiproject.Parser.XMLParser;

import org.xml.sax.SAXException;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLAsync extends AsyncTask<String,Void, List<News>> {

    private ParserXMLCallback callback;

    public XMLAsync(ParserXMLCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<News> doInBackground(String... strings) {
        String api = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=";
        String keySearch = strings[0];
        api = api + keySearch;

        try {
            XMLParser XMLparser = new XMLParser();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(api,XMLparser);

            return XMLparser.getArrNews();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<News> news) {
        super.onPostExecute(news);
        callback.onParserFinish(news);
    }

    public interface ParserXMLCallback{
        void onParserFinish(List<News> arr);
    }
}

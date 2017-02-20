package org.androidtown.demo2;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by dldnj on 2017-02-17.
 */

public class SubwayInfo {

    private static Document parseXML(InputStream stream) throws Exception{

        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;

        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(stream);
        }catch(Exception ex){
            throw ex;
        }

        return doc;
    }

    private Document getXmlInfo(String stationName) throws Exception{

        String encodedStationName = "";
        URL url = null;
        URLConnection connection = null;
        Document doc = null;

        //try{
            encodedStationName = URLEncoder.encode(stationName.trim(),"UTF-8");
            url = new URL("http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/realtimeStationArrival/1/10/"+encodedStationName+"/");
            connection = url.openConnection();
            doc = parseXML(connection.getInputStream());

        //예외 발생 클래스들을 이 메소드를 쓰는 곳 에서 처리
        /*} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }*/
        return doc;
    }

    public void getSubwayInfo(String stationName, String direct) throws Exception{

            Document doc = getXmlInfo(stationName); // 예외발생하면 이걸 쓰는곳에서 처리
            NodeList arrivalInfomation =  doc.getElementsByTagName("arvlMsg2");
            NodeList subwayDirection =  doc.getElementsByTagName("trainLineNm");
            NodeList subwayNumber = doc.getElementsByTagName("subwayId");
            NodeList totalNumber = doc.getElementsByTagName("total");
            System.out.println(stationName);
            for(int i=0; i<Integer.parseInt(totalNumber.item(0).getTextContent());i++){
                if(subwayDirection.item(i).getTextContent().contains("공릉방면")){
                    System.out.println(subwayNumber.item(i).getTextContent());
                    System.out.println(subwayDirection.item(i).getTextContent());
                    System.out.println(arrivalInfomation.item(i).getTextContent());
                    System.out.println("--------------------------------------------");
                }
            }


    }
}

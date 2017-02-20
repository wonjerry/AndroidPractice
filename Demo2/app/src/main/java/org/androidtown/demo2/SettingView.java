package org.androidtown.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by dldnj on 2017-02-19.
 */

public class SettingView extends Activity {

    EditText stationName;
    EditText direction;
    TextView result;
    Button okBtn;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        stationName = (EditText) findViewById(R.id.stationName);
        direction = (EditText) findViewById(R.id.stationName);
        result = (TextView) findViewById(R.id.result);
        okBtn = (Button) findViewById(R.id.okBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectThread thread = new ConnectThread(stationName.getText().toString().trim(),direction.getText().toString().trim());
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class ConnectThread extends Thread {
        String stationName;
        String direct;

        public ConnectThread (String stationName, String direct){
            this.stationName = stationName;
            this.direct = direct;
        }

        @Override
        public void run() {
            try{
                final String output = getSubwayInfo();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(output);
                    }
                });
            }catch (Exception e){

            }
        }

        private  Document parseXML(InputStream stream) throws Exception{

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
            HttpURLConnection connection = null;
            Document doc = null;

            //try{
            encodedStationName = URLEncoder.encode(stationName.trim(),"UTF-8");
            url = new URL("http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/realtimeStationArrival/1/10/"+encodedStationName+"/");
            connection = (HttpURLConnection) url.openConnection();
            if(connection != null){
                connection.setConnectTimeout(1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    doc = parseXML(connection.getInputStream());
                }
                connection.disconnect();
            }
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

        public String getSubwayInfo() throws Exception{

            Document doc = getXmlInfo("하계"); // 예외발생하면 이걸 쓰는곳에서 처리
            NodeList arrivalInfomation =  doc.getElementsByTagName("arvlMsg2");
            NodeList subwayDirection =  doc.getElementsByTagName("trainLineNm");
            NodeList subwayNumber = doc.getElementsByTagName("subwayId");
            NodeList totalNumber = doc.getElementsByTagName("total");
            StringBuilder resultString = new StringBuilder();
            for(int i=0; i<Integer.parseInt(totalNumber.item(0).getTextContent());i++){
                if(subwayDirection.item(i).getTextContent().contains("공릉방면")){
                    resultString.append(subwayNumber.item(i).getTextContent() + '\n'+
                            subwayDirection.item(i).getTextContent() + '\n' +
                            arrivalInfomation.item(i).getTextContent() + '\n' +
                            "--------------------------------------------" + '\n'
                    );
                }
            }
            Toast.makeText(getApplicationContext(),"됨" , Toast.LENGTH_LONG).show();
            return resultString.toString();
        }
    }
}

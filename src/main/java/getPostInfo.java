import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class getPostInfo {
	public static void main(String []args){
		httpUrlConnection();
	}
	private static void httpUrlConnection() {   
		try {   
		String pathUrl = "http://www.cdggzy.com:8147/newsite/Notice/CgIndex1.aspx?classid=2&typeid=1";   
		// ��������   
		URL url = new URL(pathUrl);   
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();   
		  
		   
		// //������������   
		httpConn.setDoOutput(true);// ʹ�� URL ���ӽ������   
		httpConn.setDoInput(true);// ʹ�� URL ���ӽ�������   
		httpConn.setUseCaches(false);// ���Ի���   
		httpConn.setRequestMethod("POST");// ����URL���󷽷�   
		String requestString = "pageindex=4&pagesize=10";   
		  String hostString="www.cdggzy.com:8147";
		  String referer="http://www.cdggzy.com:8147/newsite/Notice/GGList.aspx?classid=2&typeid=1";
		   String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
		// ������������   
		// ��������ֽ����ݣ������������ı��룬���������������˴����������ı���һ��   
		byte[] requestStringBytes = requestString.getBytes("utf-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   
		httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����   
		httpConn.setRequestProperty("Charset", "UTF-8");   
		//   
		//String name = URLEncoder.encode("������", "utf-8");   
		httpConn.setRequestProperty("pageindex", "4");
		httpConn.setRequestProperty("pagesize", "10");
		  
		   
		// �������������д������   
		OutputStream outputStream = httpConn.getOutputStream();   
		outputStream.write(requestStringBytes);   
		outputStream.close();   
		// �����Ӧ״̬   
		int responseCode = httpConn.getResponseCode();   
		  
		   
		if (HttpURLConnection.HTTP_OK == responseCode) {// ���ӳɹ�   
		// ����ȷ��Ӧʱ��������   
		StringBuffer sb = new StringBuffer();   
		String readLine;   
		BufferedReader responseReader;   
		// ������Ӧ�����������������Ӧ������ı���һ��   
		 responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));   
		while ((readLine = responseReader.readLine()) != null) {
			if(readLine.equals(""))continue;
			System.out.println(readLine);
		sb.append(readLine).append("\n");   
		}   
		responseReader.close();   
		System.out.println(sb.toString());   
		}   
		} catch (Exception ex) {   
		ex.printStackTrace();   
		}   
		  
		   
		} 
	
	

}

package com.Suirui.net;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.RandomProxyGenerator;
import cn.edu.hfut.dmic.webcollector.util.Config;
import java_cup.internal_error;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.experimental.theories.Theories;

import com.google.common.base.CaseFormat;

/**
 *use muti-proxy to crawler data from website
 *
 *override visitAndGetNextLinks to add links to queue
 *
 * @author sharon.w
 */
public  class ProxyCrawler extends BreadthCrawler {
public String filePath;
	AtomicInteger id = new AtomicInteger(0);

	/**
	 * @param crawlPath exp->/sharon/crawler/pCrawler
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param autoParse
	 *            if autoParse is true,BreadthCrawler will auto extract links
	 *            which match regex rules from pag
	 */
	public ProxyCrawler(String crawlPath,List<String>seedsURL,List<String>regexs) {
		
		super(crawlPath,true);
		filePath="D:"+crawlPath;
		Config.TIMEOUT_CONNECT = 5000;

		Config.TIMEOUT_READ = 20000;

		Config.MAX_RETRY = 500;

		Config.requestMaxInterval = 1000 * 60 * 2;

		for(String seed:seedsURL){
			this.addSeed(seed);
		
		}
		if(regexs!=null)
		for(String regex:regexs){
			this.addRegex(regex);
		}
		HttpRequesterImpl requester = (HttpRequesterImpl) this.getHttpRequester();
		for (int i = 35; i <= 70; i++) {

			try {
				addProxy("http://www.kuaidaili.com/free/inha/" + i + "/", proxyGenerator);
				addProxy("http://www.kuaidaili.com/free/ouha/" + i + "/", proxyGenerator);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		requester.setProxyGenerator(proxyGenerator);
	}

	
	

	/**
	 * 
	 *
	 * @param url
	 * 
	 * @param proxyGenerator
	 * 
	 * @throws Exception
	 */
	public static void addProxy(String url, RandomProxyGenerator proxyGenerator) throws Exception {
		// webcrawler need 2.07+
		@SuppressWarnings("unused")
		HttpRequest request = new HttpRequest(url);
		int thecaseNum=3;
		if(thecaseNum==1){
		List<String>proxyUrls=new ArrayList<>();
		List<Integer>ports=new ArrayList<>();
		proxyUrls.add("218.202.137.102");ports.add(81);
		proxyUrls.add("120.90.6.92");ports.add(8080);
		proxyUrls.add("120.24.227.21");ports.add(8118);
		proxyUrls.add("115.29.170.58");ports.add(8118);
		proxyUrls.add("218.106.205.145");ports.add(8080);
		proxyUrls.add("202.111.9.106");ports.add(23);
		proxyUrls.add("119.130.114.122");ports.add(8888);
		proxyUrls.add("61.143.158.238");ports.add(808);
		proxyUrls.add("183.230.53.133");ports.add(8123);
		proxyUrls.add("202.75.210.45");ports.add(7777);
		proxyUrls.add("125.115.94.237");ports.add(8998);
		proxyUrls.add("117.135.250.71");ports.add(83);
		proxyUrls.add("218.106.205.145");ports.add(8080);
		proxyUrls.add("223.13.108.216");ports.add(8118);
		proxyUrls.add("27.37.134.85");ports.add(8118);
		proxyUrls.add("218.207.102.107");ports.add(81);
		proxyUrls.add("183.149.195.146");ports.add(8998);			
		proxyUrls.add("210.5.149.43");ports.add(8090);	
		proxyUrls.add("124.202.223.202");ports.add(8118);
		
		proxyUrls.add("122.5.24.100");ports.add(9529);
		for(int j=0;j<proxyUrls.size();j++){
			proxyGenerator.addProxy(proxyUrls.get(j),ports.get(j));
		}
		}
		else if(thecaseNum==2)
		for (int i = 0; i <= 3; i++) {
			
			try {

				Document doc = Jsoup.connect(url).get();
				org.jsoup.select.Elements IP = doc.getElementsByAttributeValue("data-title", "IP");
				org.jsoup.select.Elements PORT = doc.getElementsByAttributeValue("data-title", "PORT");

				if (IP.size() == PORT.size()) {
					for (int index = 0; index < IP.size(); index++) {
						proxyGenerator.addProxy(IP.get(index).text(), Integer.valueOf(PORT.get(index).text()));

						System.out.println("addPorxy: IP" + IP.get(index).text() + " Port" + PORT.get(index).text());
					}

				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
			
	}
		else if(thecaseNum==3){
			
			String proxyFilePath="D:/sharon/crawler/wpCrawlerForChina/proxy.txt";
			List<String[]> proxyList=ProxyReader.getproxy(proxyFilePath);
			for(int j=0;j<proxyList.size();j++){
				proxyGenerator.addProxy(proxyList.get(j)[0],Integer.valueOf(proxyList.get(j)[1]));
			}
		}

	}
	public static RandomProxyGenerator proxyGenerator = new RandomProxyGenerator() {

		@Override
		public void markGood(Proxy proxy, String url) {
			proxyGenerator.addProxy(proxy);
			InetSocketAddress address = (InetSocketAddress) proxy.address();
			System.out.println("--------->>>>Good Proxy:" + address.toString() + "   " + url);
		
		}

		@Override
		public void markBad(Proxy proxy, String url) {
			InetSocketAddress address = (InetSocketAddress) proxy.address();
		//System.out.println("--------->>>>Bad Proxy:" + address.toString() + "   " + url);
			//removeProxy(proxy);

		}

	};

	
		@Override
		public void visit(Page page, Links nextLinks) {
			
			Document document=Jsoup.parse(page.getHtml());			
			File filedir = new File(filePath);
			 filedir.mkdirs();
			File file=new File(filePath+MD5Util.MD5(document.data()+" "+page.getUrl())); 
			Elements elements=document.getElementsByAttribute("href");
			Links links = new Links();
			//System.out.println("-----links-----------"+this.threads+"----"+elements.size());
			for(Element aElement:elements){				
				links.add(GetUrl.addSupandHref(aElement.attr("href"),page.getUrl()));	
				
			//	System.out.println(GetUrl.addSupandHref(aElement.attr("href"),page.getUrl()));
			}
			
			//System.out.println("-----links-end----------"+this.threads+"----"+elements.size());
			
			nextLinks.addAll(links);
			if(file.exists())return;
			BufferIOFile.save(filePath+MD5Util.MD5(document.data()+" "+page.getUrl()),page.getUrl()+'\n'+document.text());
			
	
		}
/*
	public static void main(String[] args) throws Exception {
		
			List<String>seedsURL=new ArrayList<>(),regexs=new ArrayList<>();
			for (int i = 0; i < 22; i++)
				seedsURL.add(
						"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=shiji_cggg1&rp=25&page="
								+ i);
			
		ProxyCrawler crawler = new ProxyCrawler("/sharon/crawler/crawl_dazhong/", seedsURL, null);

		crawler.setThreads(50);
		crawler.setTopN(100);

	
		crawler.setTopN(100000);

		// crawler.setResumable(true);
		// start crawl with depth of 4 
		crawler.start(4);
	

	}*/

	

}
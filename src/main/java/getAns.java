

public class getAns {
	public static void main(String [] args) throws Exception{
	
		String className = "ForC";
		//0-》欧式空间        1-》tfidf
		int type=1;
		String Url="http://www.ccgp.gov.cn/cggg/dfgg/";
		//从url爬取文件
		//UrlCrawler.getAns(Url,10000);
		//从xls得到内容到文件
		//GetInfoFromXls.getAns(className);
		//将文件分词放入对应文件夹并完成merged以及对merged的词频统计
		LexicalAnalyzer.getAns(className);
		//LexicalAnalyzer.getAns("ForNo");
		//LexicalAnalyzer.getAns("ForHebei");
		//转化成特征值
		compareKeys.getAns(className,type);
		//根据-seg得到整个文件夹内的文件的TF值
		freqToTF.getAns(className);
	}
	

}

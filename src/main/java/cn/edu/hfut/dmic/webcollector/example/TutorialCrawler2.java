/*
 * Copyright (C) 2015 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package cn.edu.hfut.dmic.webcollector.example;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.example.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import org.jsoup.nodes.Document;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * WebCollector 2.x�汾��tutorial
 * 2.x�汾���ԣ�
 *   1���Զ���������ԣ�����ɸ�Ϊ���ӵı���ҵ�������ҳ��AJAX
 *   2������Berkeley DB����URL�����Դ��������������ҳ
 *   3������selenium�����Զ�javascript������Ϣ���г�ȡ
 *   4��ֱ��֧�ֶ��������л�
 *   5������spring jdbc��mysql connection���������ݳ־û�
 *   6������json������
 *   7��ʹ��slf4j��Ϊ��־����
 *   8���޸�http����ӿڣ��û��Զ���http������ӷ���
 * 
 * ����cn.edu.hfut.dmic.webcollector.example�����ҵ�����(Demo)
 * 
 * @author hu
 */
public class TutorialCrawler2 extends BreadthCrawler {

    /**
     * �û��Զ����ÿ��ҳ��Ĳ�����һ�㽫��ȡ���־û��Ȳ���д��visit�����С�
     * @param page
     * @param nextLinks ��Ҫ������ȡ��URL�����autoParseΪtrue��������Զ���ȡ������������Ӳ�����nextLinks��
     */
    @Override
    public void visit(Page page, Links nextLinks) {
        Document doc = page.getDoc();
        String title = doc.title();
        System.out.println("URL:" + page.getUrl() + "  ����:" + title);

        /*�����ݲ���mysql*/
        if (jdbcTemplate != null) {
            int updates=jdbcTemplate.update("insert into tb_content (title,url,html) value(?,?,?)",
                    title, page.getUrl(), page.getHtml());
            if(updates==1){
                System.out.println("mysql����ɹ�");
            }
        }
        
        /*
        //��ӵ�nextLinks�����ӻ�����һ�����x�㱻��ȡ��������Զ���URL����ȥ�أ������û��ڱ�д����ʱ��ȫ���ؿ��������ظ�URL�����⡣
        //���������ӵ������Ѿ�����ȡ���������Ӳ����ں��������б���ȡ
        //�����Ҫǿ���������ȡ�������ӣ�ֻ�������������������ϵ�������ʱ��ͨ��Crawler.addForcedSeedǿ�Ƽ���URL��
         nextLinks.add("http://www.csdn.net");
        */
    }
    

    JdbcTemplate jdbcTemplate = null;

    /*���autoParse����Ϊtrue�����������Զ�����ҳ���з�����������ӣ����������ȡ���񣬷����Զ��������ӡ�*/
    public TutorialCrawler2(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        
        /*BreadthCrawler����ֱ�����URL�������*/
        this.addRegex("http://.*zhihu.com/.*");
        this.addRegex("-.*jpg.*");
        
        /*����һ��JdbcTemplate����,"mysql1"���û��Զ�������ƣ��Ժ����ͨ��
         JDBCHelper.getJdbcTemplate("mysql1")����ȡ�������
         �����ֱ��ǣ����ơ�����URL���û��������롢��ʼ�������������������
        
         �����JdbcTemplate�����Լ����Դ������ӳأ����������ڶ��߳��У����Թ���
         һ��JdbcTemplate����(ÿ���߳���ͨ��JDBCHelper.getJdbcTemplate("����")
         ��ȡͬһ��JdbcTemplate����)             
         */
        try {
            jdbcTemplate = JDBCHelper.createMysqlTemplate("mysql1",
                    "jdbc:mysql://localhost/testdb?useUnicode=true&characterEncoding=utf8",
                    "root", "password", 5, 30);

            /*�������ݱ�*/
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS tb_content ("
                    + "id int(11) NOT NULL AUTO_INCREMENT,"
                    + "title varchar(50),url varchar(200),html longtext,"
                    + "PRIMARY KEY (id)"
                    + ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
            System.out.println("�ɹ��������ݱ� tb_content");
        } catch (Exception ex) {
            jdbcTemplate = null;
            System.out.println("mysqlδ������JDBCHelper.createMysqlTemplate�в������ò���ȷ!");
        }
        
    }

   
   

    

    public static void main(String[] args) throws Exception {
        /*
           ��һ�������������crawlPath��crawlPath��ά��URL��Ϣ���ļ��е�·�������������Ҫ�ϵ���ȡ��ÿ����ѡ����ͬ��crawlPath
           �ڶ���������ʾ�Ƿ��Զ���ȡ������������Ӳ������������
        */
        TutorialCrawler2 crawler = new TutorialCrawler2("/home/hu/data/wb",true);
        crawler.setThreads(50);
        crawler.addSeed("http://www.zhihu.com/");
        crawler.setResumable(false);

        /*
        //requester�Ǹ�����http����Ĳ��������ͨ��requester�еķ�����ָ��http/socks����
        HttpRequesterImpl requester=(HttpRequesterImpl) crawler.getHttpRequester();    
       
        //������
        requester.setProxy("127.0.0.1", 1080,Proxy.Type.SOCKS);
        
        //��������
        RandomProxyGenerator proxyGenerator=new RandomProxyGenerator();
        proxyGenerator.addProxy("127.0.0.1",8080,Proxy.Type.SOCKS);
        requester.setProxyGenerator(proxyGenerator);
        */


        /*�����Ƿ�ϵ���ȡ*/
        crawler.setResumable(false);
        /*����ÿ����ȡ��ȡ�����URL����*/
        crawler.setTopN(100);

        /*���ϣ�������ܵ���ȡ�������������һ���ܴ�������������û�д���ȡURLʱ�Զ�ֹͣ*/
        crawler.start(5);
    }

    

}

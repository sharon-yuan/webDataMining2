/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 鏉ㄥ皻宸�, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.SegmentationFactory;
import org.apdplat.word.recognition.StopWord;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 涓枃鍒嗚瘝鍩虹鍏ュ彛
 * 榛樿浣跨敤鍙屽悜鏈�澶у尮閰嶇畻娉�
 * 涔熷彲鎸囧畾鍏朵粬鍒嗚瘝绠楁硶
 * @author 鏉ㄥ皻宸�
 */
public class WordSegmenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordSegmenter.class);    
    /**
     * 瀵规枃鏈繘琛屽垎璇嶏紝淇濈暀鍋滅敤璇�
     * 鍙寚瀹氬叾浠栧垎璇嶇畻娉�
     * @param text 鏂囨湰
     * @param segmentationAlgorithm 鍒嗚瘝绠楁硶
     * @return 鍒嗚瘝缁撴灉
     */
    public static List<Word> segWithStopWords(String text, SegmentationAlgorithm segmentationAlgorithm){
        return SegmentationFactory.getSegmentation(segmentationAlgorithm).seg(text);
    }
    /**
     * 瀵规枃鏈繘琛屽垎璇嶏紝淇濈暀鍋滅敤璇�
     * 浣跨敤鍙屽悜鏈�澶у尮閰嶇畻娉�
     * @param text 鏂囨湰
     * @return 鍒嗚瘝缁撴灉
     */
    public static List<Word> segWithStopWords(String text){
        return SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching).seg(text);
    }
    /**
     * 瀵规枃鏈繘琛屽垎璇嶏紝绉婚櫎鍋滅敤璇�
     * 鍙寚瀹氬叾浠栧垎璇嶇畻娉�
     * @param text 鏂囨湰
     * @param segmentationAlgorithm 鍒嗚瘝绠楁硶
     * @return 鍒嗚瘝缁撴灉
     */
    public static List<Word> seg(String text, SegmentationAlgorithm segmentationAlgorithm){        
        List<Word> words = SegmentationFactory.getSegmentation(segmentationAlgorithm).seg(text);
        return filterStopWords(words);
    }
    /**
     * 瀵规枃鏈繘琛屽垎璇嶏紝绉婚櫎鍋滅敤璇�
     * 浣跨敤鍙屽悜鏈�澶у尮閰嶇畻娉�
     * @param text 鏂囨湰
     * @return 鍒嗚瘝缁撴灉
     */
    public static List<Word> seg(String text){
        List<Word> words = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching).seg(text);
        return filterStopWords(words);
    }    
    /**
     * 绉婚櫎鍋滅敤璇�
     * @param words 璇嶅垪琛�
     * @return 鏃犲仠鐢ㄨ瘝鐨勮瘝鍒楄〃
     */
    public static List<Word> filterStopWords(List<Word> words){
        Iterator<Word> iter = words.iterator();
        while(iter.hasNext()){
            Word word = iter.next();
            if(StopWord.is(word.getText())){
                //鍘婚櫎鍋滅敤璇�
                LOGGER.debug("鍘婚櫎鍋滅敤璇嶏細"+word.getText());
                iter.remove();
            }
        }
        return words;
    }
    /**
     * 瀵规枃浠惰繘琛屽垎璇嶏紝淇濈暀鍋滅敤璇�
     * 鍙寚瀹氬叾浠栧垎璇嶇畻娉�
     * @param input 杈撳叆鏂囦欢
     * @param output 杈撳嚭鏂囦欢
     * @param segmentationAlgorithm 鍒嗚瘝绠楁硶
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        seg(input, output, false, segmentationAlgorithm);
    }
    /**
     * 瀵规枃浠惰繘琛屽垎璇嶏紝淇濈暀鍋滅敤璇�
     * 浣跨敤鍙屽悜鏈�澶у尮閰嶇畻娉�
     * @param input 杈撳叆鏂囦欢
     * @param output 杈撳嚭鏂囦欢
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output) throws Exception{
        seg(input, output, false, SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    /**
     * 瀵规枃浠惰繘琛屽垎璇嶏紝绉婚櫎鍋滅敤璇�
     * 鍙寚瀹氬叾浠栧垎璇嶇畻娉�
     * @param input 杈撳叆鏂囦欢
     * @param output 杈撳嚭鏂囦欢
     * @param segmentationAlgorithm 鍒嗚瘝绠楁硶
     * @throws Exception 
     */
    public static void seg(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        seg(input, output, true, segmentationAlgorithm);
    }
    /**
     * 瀵规枃浠惰繘琛屽垎璇嶏紝绉婚櫎鍋滅敤璇�
     * 浣跨敤鍙屽悜鏈�澶у尮閰嶇畻娉�
     * @param input 杈撳叆鏂囦欢
     * @param output 杈撳嚭鏂囦欢
     * @throws Exception 
     */
    public static void seg(File input, File output) throws Exception{
        seg(input, output, true, SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    /**
     * 
     * 瀵规枃浠惰繘琛屽垎璇�
     * @param input 杈撳叆鏂囦欢
     * @param output 杈撳嚭鏂囦欢
     * @param removeStopWords 鏄惁绉婚櫎鍋滅敤璇�
     * @param segmentationAlgorithm 鍒嗚瘝绠楁硶
     * @throws Exception 
     */
    private static void seg(File input, File output, boolean removeStopWords, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        LOGGER.info("寮�濮嬪鏂囦欢杩涜鍒嗚瘝锛�"+input.toString());
        float max=(float)Runtime.getRuntime().maxMemory()/1000000;
        float total=(float)Runtime.getRuntime().totalMemory()/1000000;
        float free=(float)Runtime.getRuntime().freeMemory()/1000000;
        String pre="鎵ц涔嬪墠鍓╀綑鍐呭瓨:"+max+"-"+total+"+"+free+"="+(max-total+free);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input),"utf-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"utf-8"))
            long size = Files.size(input.toPath());
            LOGGER.info("size:"+size);
            LOGGER.info("鏂囦欢澶у皬锛�"+(float)size/1024/1024+" MB");
            int textLength=0;
            int progress=0;
            long start = System.currentTimeMillis();
            String line = null;
            while((line = reader.readLine()) != null){
                if("".equals(line.trim())){
                    writer.write("\n");
                    continue;
                }
                textLength += line.length();
                List<Word> words = null;
                if(removeStopWords){
                    words = seg(line, segmentationAlgorithm);
                }else{
                    words = segWithStopWords(line, segmentationAlgorithm);
                }
                if(words == null){
                    continue;
                }
                for(Word word : words){
                    writer.write(word.getText()+" ");
                }
                writer.write("\n");
                progress += line.length();
                if( progress > 500000){
                    progress = 0;
                    LOGGER.info("鍒嗚瘝杩涘害锛�"+(int)((float)textLength*2/size*100)+"%");
                }
            }
            long cost = System.currentTimeMillis() - start;
            float rate=0;
            if(cost!=0)
            rate = textLength/cost;
           

        max=(float)Runtime.getRuntime().maxMemory()/1000000;
        total=(float)Runtime.getRuntime().totalMemory()/1000000;
        free=(float)Runtime.getRuntime().freeMemory()/1000000;
        String post=""+max+"-"+total+"+"+free+"="+(max-total+free);
        LOGGER.info(pre);
        LOGGER.info(post);
        
    }
    private static void demo(){
        long start = System.currentTimeMillis();
        List<String> sentences = new ArrayList<>();
        
        int i=1;
        for(String sentence : sentences){
            List<Word> words = segWithStopWords(sentence);
            
        }
        long cost = System.currentTimeMillis() - start;
       
    }
    public static void processCommand(String... args) {
        if(args == null || args.length < 1){
         
            return;
        }
        try{
            switch(args[0].trim().charAt(0)){
                case 'd':
                    demo();
                    break;
                case 't':
                    if(args.length < 2){
                        showUsage();
                    }else{
                        StringBuilder str = new StringBuilder();
                        for(int i=1; i<args.length; i++){
                            str.append(args[i]).append(" ");
                        }
                        List<Word> words = segWithStopWords(str.toString());
                        
                    }
                    break;
                case 'f':
                    if(args.length != 3){
                        showUsage();
                    }else{
                        segWithStopWords(new File(args[1]), new File(args[2]));
                    }
                    break;
                default:
                    StringBuilder str = new StringBuilder();
                    for(String a : args){
                        str.append(a).append(" ");
                    }
                    List<Word> words = segWithStopWords(str.toString());
                    
                    break;
            }
        }catch(Exception e){
            showUsage();
        }
    }
    private static void run(String encoding) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding))){
            String line = null;
            while((line = reader.readLine()) != null){
                if("exit".equals(line)){
                    System.exit(0);
                   
                    return;
                }
                if(line.trim().equals("")){
                    continue;
                }
                processCommand(line.split(" "));
                showUsage();
            }
        } catch (IOException ex) {
            LOGGER.error(" ", ex);
        }
    }
    private static void showUsage(){
        
    }
    public static void main(String[] args) {
        String encoding = "utf-8";
        if(args==null || args.length == 0){
            showUsage();
            run(encoding);
        }else if(Charset.isSupported(args[0])){
            showUsage();
            run(args[0]);
        }else{
            processCommand(args);
          
            System.exit(0);
        }
    }
}
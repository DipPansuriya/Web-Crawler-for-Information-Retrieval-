import java.io.File;

//import com.opencsv.*;
import java.io.*;
import java.util.*;
import java.net.*;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.*;
import edu.uci.ics.crawler4j.robotstxt.*;
public class Controller {
	public static void main(String[] args) throws Exception {
		
		String data=Controller.class.getName().replace(".", File.separator)+".class";
		
		URL fileurl=ClassLoader.getSystemClassLoader().getResource(data);
		String path=new File(fileurl.toURI()).getParent();
		
		
		String crawlStorageFolder = path+File.separator+"data"+File.separator+"crawl";
		 
		 int numberOfCrawlers = 1;
		 CrawlConfig config = new CrawlConfig();
		 config.setCrawlStorageFolder(crawlStorageFolder);
		 
		 int maxpagetofetch=10000;
		 int maxdepth=16;
		 
//		 config.setPolitenessDelay(500);
		 config.setMaxPagesToFetch(maxpagetofetch);
		 config.setMaxDepthOfCrawling(maxdepth);
		 config.setIncludeBinaryContentInCrawling(true);
//		 config.setMaxDownloadSize(10000000);
		 
		 PageFetcher pageFetcher = new PageFetcher(config);
		 RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		 RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		 CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		 controller.addSeed("http://www.bbc.com/news");
		 
		 
		 controller.start(MyCrawler.class, numberOfCrawlers);
		 int unique=(int)MyCrawler.linkset.stream().filter(p->p.getURL().toLowerCase().startsWith("http://www.bbc.com/news")).count();
		 System.out.println(MyCrawler.fetchmap);
		 System.out.println(MyCrawler.map);
		 System.out.println(MyCrawler.contentmap);
		 System.out.println(MyCrawler.sizemap);
//		 System.out.println("Total URLs extracted:"+MyCrawler.total_extractedURL);
		 System.out.println("Total URLs extracted (Count-Should Visit):"+MyCrawler.count);
		 System.out.println("Count from Visit :"+MyCrawler.total_extractedURL);
		 System.out.println("unique URLs extracted:"+MyCrawler.linkset.size());
		 System.out.println("unique URLs within a news website:"+unique);
		 System.out.println("unique URLs outside the news website:"+(MyCrawler.linkset.size()-unique));
		 
		 }
		}

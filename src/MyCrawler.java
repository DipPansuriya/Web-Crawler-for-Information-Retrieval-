import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.hssf.record.FilePassRecord;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;


public class MyCrawler extends  WebCrawler
{

	List<WebURL> listwebURL=new ArrayList<>();
	public static int count=0;
//	private static final Pattern filePatterns = Pattern.compile(".*(\\.(pdf|docx|html|htm|doc|jpg|png|gif))$");
	public static final String fetch="D:/IR-Final/fetch_BBCNews.csv";
	public static final String visit="D:/IR-Final/visit_BBCNews.csv";
	public static final String urls="D:/IR-Final/urls_BBCNews.csv";
	
	public static HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
	public static HashMap<String,Integer> contentmap=new HashMap<String,Integer>();
	public static HashMap<String,Integer> sizemap=new HashMap<String,Integer>();
	public static HashSet<WebURL> linkset=new HashSet<WebURL>();
	public static Set<WebURL> checkset=new HashSet<WebURL>();
	public static HashMap<String,Integer> fetchmap=new HashMap<String,Integer>();
	
	static int totalurls=0;
	int c=0;
	int contenttype=0;
	int less1=1;
	int onetoten=1;
	int tentohun=1;
	int hudtomb=1;
	int moremb=1;
	int a=1;
   int sucess;
   int abort;
   int failed;
   int fetch_count;
   public static int total_extractedURL=0;
	static int extractcount=0;
	
//	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|"
//			 + "|mp3|mp3|zip|gz))$");
			 
			@Override
	         public void onStart()
	         {
	        	 File fetchfile=new File(fetch);
	        	 File visitfile=new File(visit);
	        	 File urlsfile=new File(urls);
	        	 
	        	 FileWriter fetchfilewriter;
	        	 FileWriter visitfilewriter;
	        	 FileWriter urlfilewriter;
	        	 
	        	 
	        	 try
	        	 {
	        		 fetchfilewriter =new FileWriter(fetchfile);
	        		 fetchfilewriter.write("URL"+","+"Status Code"+"\n");
	        		 fetchfilewriter.close();
	        		 
	        		 visitfilewriter =new FileWriter(visitfile);
	        		 visitfilewriter.write("URL"+","+"File Size"+","+" No of Links Found"+","+"Content Type"+"\n");
	        		 visitfilewriter.close();
	        		 
	        		 urlfilewriter =new FileWriter(urlsfile);
	        		 urlfilewriter.write("URL"+","+"Type of URL"+"\n");
	        		 urlfilewriter.close();
	        		 
	        		 
	        	 }
	        	 catch(IOException e)
	        	 {
	        		e.printStackTrace(); 
	        	 }
	        	 
	         }
	        
//			 @Override
			 protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
				 File fetchfile=new File(fetch);
				 
				 try(PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fetchfile,true))))
				 {
					 String href = webUrl.getURL();
					 if(map.containsKey(statusCode))
					 {
						 c=map.get(statusCode);
						 map.put(statusCode, c+1);
					 }
					 else
					 {
						 map.put(statusCode, 1);
					 }
					 
					 if(statusCode>=200&&statusCode<=299)
					 {
						 if(fetchmap.containsKey("Sucess:"))
						 {
							sucess=fetchmap.get("Sucess:");
							fetchmap.put("Sucess:", sucess+1);
						 }
						 else
						 {
							 fetchmap.put("Sucess:", 1);
						 }
						 
					 }
					 else if(statusCode>=300&&statusCode<=399)
					 {
						 if(fetchmap.containsKey("Abort:"))
						 {
							abort=fetchmap.get("Abort:");
							fetchmap.put("Abort:", abort+1);
						 }
						 else
						 {
							 fetchmap.put("Abort:", 1);
						 }
						 
					 }
					 else if(statusCode>=400&&statusCode<=599)
					 {
						 if(fetchmap.containsKey("Failed:"))
						 {
							failed=fetchmap.get("Failed:");
							fetchmap.put("Failed:", failed+1);
						 }
						 else
						 {
							 fetchmap.put("Failed:", 1);
						 }

					 }
					 if(href.startsWith("http://www.bbc.com/news")||href.startsWith("https://www.bbc.com/news"))
					 {
						 fetch_count=fetch_count+1;
						 pw.println(href+","+statusCode);
						 if(fetchmap.containsKey("Total Fetch URLS:"))
						 {
							 fetch_count=fetchmap.get("Total Fetch URLS:");
							 fetchmap.put("Total Fetch URLS:", fetch_count+1);
						 }
						 else
						 {
							 fetchmap.put("Total Fetch URLS:", 1);
						 }
					 }
					 
					 
				 } catch (IOException e) {
					e.printStackTrace();
				}
				 
			  }

			 
			 @Override
			 public boolean shouldVisit(Page referringPage, WebURL url) {
			 
			 count=count+1;
			 String href = url.getURL();
			 String Checkdomain="";
			 
			 if(href.startsWith("http://www.bbc.com/news")||href.startsWith("https://www.bbc.com/news"))
			 {
				Checkdomain="OK"; 
			 }
			 else
			 {
				 Checkdomain="Not OK";
			 }
			 
			 File urlsfile=new File(urls);
			 try(PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(urlsfile,true))))
			 {
				 out.println(href+","+Checkdomain);
			 }
			 catch(IOException e)
			 {
				 e.printStackTrace();
			 }
			 
			 return href.startsWith("http://www.bbc.com/news");
				//	 !FILTERS.matcher(href).matches();
//			 && href.startsWith("http://www.bbc.com/news");
			 }
			
			 public void check(Page page)
			 {
				 if(page.getContentData().length<1024)
				  {
					  
					  if(sizemap.containsKey("<1:"))
					  {
			             less1=sizemap.get("<1:");
			             sizemap.put("<1:", less1+1);
					  }
					  else
					  {
						  sizemap.put("<1:", less1);
					  }
					  
				  }
				 else if(page.getContentData().length>=1024&&page.getContentData().length<10240)
				  {
					  if(sizemap.containsKey("1KB ~ <10KB:"))
					  {
						  onetoten=sizemap.get("1KB ~ <10KB:");
			             sizemap.put("1KB ~ <10KB:", onetoten+1);
					  }
					  else
					  {
						  sizemap.put("1KB ~ <10KB:", onetoten);
					  }
					  
				  }
				 else if(page.getContentData().length>=10240&&page.getContentData().length<102400)
				  {
					  if(sizemap.containsKey("10KB ~ <100KB:"))
					  {
						 tentohun=sizemap.get("10KB ~ <100KB:");
			             sizemap.put("10KB ~ <100KB:", tentohun+1);
					  }
					  else
					  {
						  sizemap.put("10KB ~ <100KB:", tentohun);
					  }
					  
				  }
				 else  if(page.getContentData().length>=102400&&page.getContentData().length<1048576)
				  {
					  if(sizemap.containsKey("100KB ~ <1MB:"))
					  {
						  hudtomb=sizemap.get("100KB ~ <1MB:");
			             sizemap.put("100KB ~ <1MB:", hudtomb+1);
					  }
					  else
					  {
						  sizemap.put("100KB ~ <1MB:", hudtomb);
					  }
					  
				  }
				 else
				  {
					  if(sizemap.containsKey(">= 1MB:"))
					  {
						  moremb=sizemap.get(">= 1MB:");
			             sizemap.put(">= 1MB:", moremb+1);
					  }
					  else
					  {
						  sizemap.put(">= 1MB:", moremb);
					  }
				  }
				  
			 }
			 
			 public  void checking(Set<WebURL> g)
			 {
				 checkset.addAll(g);
				 extractcount+=1;
			 }
			 
			  @Override
			  public void visit(Page page) 
			  {
			   String h=page.getContentType();
//			   if(h.contains("text/html")||h.contains("application/pdf")||h.contains("image/gif")||h.contains("image/png")||h.contains("image/jpg")||h.contains("image/tif")||h.contains("application/msword"))
//			   {
				  String url = page.getWebURL().getURL();
			  
				  if (page.getParseData() instanceof HtmlParseData) 
				  {
					  HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//					  String text = htmlParseData.getText();
//					  String html = htmlParseData.getHtml();
					  
					  Set<WebURL> links = htmlParseData.getOutgoingUrls();
					  linkset.addAll(links);
					  totalurls=links.size();
					  total_extractedURL+=totalurls;
					   
					   
					   
 					  File visitfFile=new File(visit);
					  try(PrintWriter pt=new PrintWriter(new BufferedWriter(new FileWriter(visitfFile,true))))
					  {
						 
						  check(page);
						  String p=page.getContentType();
						  if(p.contains(";"))
						  {
							  String[] arr=p.split(";");
							  pt.println("\""+url+"\","+page.getContentData().length+","+page.getParseData().getOutgoingUrls().size()+","+arr[0]);
							  if(contentmap.containsKey(arr[0]))
							  {
								  contenttype=contentmap.get(arr[0]);
								  contentmap.put(arr[0], contenttype+1);
							  }
							  else
							  {
								  contentmap.put(arr[0], 1);
							  }
						  }
						  else
						  {
							  pt.println("\""+url+"\","+page.getContentData().length+","+page.getParseData().getOutgoingUrls().size()+","+page.getContentType());
							  if(contentmap.containsKey(page.getContentType()))
							  {
								  contenttype=contentmap.get(page.getContentType());
								  contentmap.put(page.getContentType(), contenttype+1);
							  }
							  else
							  {
								  contentmap.put(page.getContentType(), 1);
							  }
						  }
					  }
					  catch(IOException e)
					  {
						  e.printStackTrace();
					  }
				  }
				  else if(h.contains("text/html")||h.contains("application/pdf")||h.contains("image/gif")||h.contains("image/png")||h.contains("image/jpg")||h.contains("image/tif")||h.contains("application/msword"))
				  {
					  File visitfFile=new File(visit);
					  try(PrintWriter pt=new PrintWriter(new BufferedWriter(new FileWriter(visitfFile,true))))
					  {
						 
						  check(page);
						  String p=page.getContentType();
						  if(p.contains(";"))
						  {
							  String[] arr=p.split(";");
							  pt.println("\""+url+"\","+page.getContentData().length+","+page.getParseData().getOutgoingUrls().size()+","+arr[0]);
							  if(contentmap.containsKey(arr[0]))
							  {
								  contenttype=contentmap.get(arr[0]);
								  contentmap.put(arr[0], contenttype+1);
							  }
							  else
							  {
								  contentmap.put(arr[0], 1);
							  }
						  }
						  else
						  {
							  pt.println("\""+url+"\","+page.getContentData().length+","+page.getParseData().getOutgoingUrls().size()+","+page.getContentType());
							  if(contentmap.containsKey(page.getContentType()))
							  {
								  contenttype=contentmap.get(page.getContentType());
								  contentmap.put(page.getContentType(), contenttype+1);
							  }
							  else
							  {
								  contentmap.put(page.getContentType(), 1);
							  }
						  }
					  }
					  catch(IOException e)
					  {
						  e.printStackTrace();
					  }
				  }
				 /* else if(filePatterns.matcher(url).matches())
				  {
					  File visitFile=new File(visit);
					  
					  try(PrintWriter pl=new PrintWriter(new BufferedWriter(new FileWriter(visitFile,true))))
					  {
						  check(page);
						  String p=page.getContentType();
						  if(p.contains(";"))
						  {
							  String[] arr=p.split(";");
							  pl.println("\""+url+"\","+page.getContentData().length+","+0+","+arr[0]);
							  if(contentmap.containsKey(arr[0]))
							  {
								  contenttype=contentmap.get(arr[0]);
								  contentmap.put(arr[0], contenttype+1);
							  }
							  else
							  {
								  contentmap.put(arr[0], 1);
							  }
						  }
						  else
						  {
							  pl.println("\""+url+"\","+page.getContentData().length+","+0+","+page.getContentType());
							  if(contentmap.containsKey(page.getContentType()))
							  {
								  contenttype=contentmap.get(page.getContentType());
								  contentmap.put(page.getContentType(), contenttype+1);
							  }
							  else
							  {
								  contentmap.put(page.getContentType(), 1);
							  }
						  }
						  
					  }
					  catch(IOException e)
					  {
						  e.printStackTrace();
						  
					  }
				  }*/
//				  if(contentmap.containsKey(page.getContentType()))
//				  {
//					  contenttype=contentmap.get(page.getContentType());
//					  contentmap.put(page.getContentType(), contenttype+1);
//				  }
//				  else
//				  {
//					  contentmap.put(page.getContentType(), 1);
//				  }
//			   }
			  }
	  
}

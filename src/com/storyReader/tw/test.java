package com.storyReader.tw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class test {
	public static void main(String[] args) throws IOException {
		StringBuilder dataString = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean isRight = false;
		int pagefrom, pageto;
		String outwrite = "";		
		String urllink, templink;
		String FileName, Name;
		
		/**
		 * http://ck101.com/thread-1667427-1-1.html
		 * 修真世界
		 **/
		
		System.out.print("請輸入網址: ");
		urllink = br.readLine();
		System.out.print("請輸入頁數: ");
		pagefrom = Integer.parseInt(br.readLine());
		System.out.print("請輸至第幾頁: ");
		pageto = Integer.parseInt(br.readLine());
		System.out.print("請輸入檔名: ");
		Name = br.readLine();
		FileName = Name + String.format("%03d", pagefrom) + ".txt";
		templink = urllink.substring(urllink.indexOf("-", urllink.indexOf("-") + 1), urllink.indexOf("-1.") + 3);
		urllink = urllink.replace(templink, "-" + pagefrom + "-1.");
//		urllink = urllink.replace("-1.", pagefrom + "-1.");
//		Util.print(urllink);
//		System.out.println(urllink);
		
		while(pagefrom <= pageto) {
			File saveFile = new File(FileName);
			try {
//				urllink = "http://ck101.com/thread-1309610-" + pagefrom + "-1.html";
				URL url = new URL(urllink);
				URLConnection conn = url.openConnection();
				Reader in = new InputStreamReader(conn.getInputStream(), "UTF-8");
				int data = in.read();
				FileWriter fwriter = new FileWriter(saveFile);
				//txt file head BOM
				fwriter.write("\uFEFF");
				while (data != -1) {
//					System.out.print((char)data);
					if (data == '\n') {
						//文章開始
						if((isRight == true) || (postStart(dataString.toString()) == true)) {
							isRight = true;
							outwrite = stringprocess(dataString.toString(), isRight);
						}
						//文章結束
						if((isRight == true) && (dataString.toString().indexOf("</div>") >= 0)) {
							outwrite = stringprocess(dataString.toString(), isRight);
							isRight = false;
						}

						fwriter.write(outwrite);
						outwrite = "";
						dataString.setLength(0);
					}
					else {
						dataString.append((char)data);
					}
					data = in.read();
				}
				fwriter.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(FileName + " Is Finish!!!");
			urllink = urllink.replace(pagefrom + "-1.", ++pagefrom + "-1.");
			FileName = Name + String.format("%03d", pagefrom) + ".txt";
		}
		System.out.println("All Finish!!!");
	}
	//文章字串處理
	public static String stringprocess(String test, boolean isRight) {
		String result = "";
		String temp1 = "";
		if(isRight == true) {
			if(postStart(test))
				test = "\n\n\n\n" + test;
			
			//處理去掉 "<...>" 字串
			while(test.indexOf("<") >= 0) {
				temp1 = test.substring(test.indexOf("<"), test.indexOf(">") + 1);
				test = test.replace(temp1, "");
			}
			
			//處理去掉 "本帖最後由 ......編輯" 字串
			while(test.indexOf("本帖最後由") >= 0) {
				temp1 = test.substring(test.indexOf("本帖最後由"), test.indexOf("編輯") + 2);
				test = test.replace(temp1, "");
			}
			test = test.replace("&nbsp;", "");
			test = test.replace(" ", "");
			test = test.replace("　", "");
			result = '\t' + test + '\n';
		}
		else if(isRight == false) {
			result = "";
		}
		return result;
	}
	
	public static boolean postStart(String dataString) {
		if(dataString.indexOf("<div id=\"postmessage") >= 0) {
			return true;
		}
		else if(dataString.indexOf("<strong><font color=") >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

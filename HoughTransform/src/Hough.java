import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Hough {

	static Color c255 = new Color(255, 255, 255);
    static Color c0 = new Color(0, 0, 0);
    static int rgb_white = c255.getRGB();
    static int rgb_black = c0.getRGB();
    
    
	public static int[][] GetImgArr(BufferedImage img){
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[][] imgArr = new int[width][height];
		
		Raster raster = img.getData();
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	            imgArr[w][h] = raster.getSample(w, h, 0);
	        }
	    }
		
		return  imgArr;
	}
	
	public static void FindPair(int[][] imgArr, int width, int height){
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		
//    	System.out.println(width +" "+height); //960 * 640
		int count = 0;
		float angle = 0;
		int p;
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	            while(angle < 180){
	            	p = (int)(w * Math.cos(Math.toRadians(angle)) + h * Math.sin(Math.toRadians(angle)));
	            	String paPair = Integer.toString(p) + "," + Float.toString(angle);
	            	hashMap.put(paPair, p);
	            	angle += 1;
//	            	
	            	count++;
	            	//System.out.println(count);
	            }
	            angle = 0;
	        }
//	        System.out.println(count);
	    }
//    	System.out.println(count);
		for(String Key: hashMap.keySet()){
		    System.out.println(Key+ ", " + hashMap.get(Key));
		}
		
		
	}
	
	public static void main(String[] args) {
		
		BufferedImage img = null;
		 
		try{
			img = ImageIO.read(new File("HW6.jpg"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[][] imgArr = GetImgArr(img);
		FindPair(imgArr, width, height);
		
		
		
		
		
		
		
		
		
	}

}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;


public class CreateQRCode {


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Qrcode qrcode = new Qrcode();
		String content = "二维码";
		String content1 ="BEGIN:VCARD\r\n" + 
					   "FN:姓名:王珍瑞\r\n"+
					   "ORG:学校:河北科师	院系:数信学院\r\n"+
					   "TITLE:学生\r\n" + 
					   "TEL;WORK;VOICE:45665432198\r\n"+
					   "TEL;HOME;VOICE:18045678911\r\n"+
					   "TEL;CELL;VOICE:18603369235\r\n"+
					   "ADR;WORK:河北科技师范学院\r\n"+
					   "ADR;HOME:河北 邯郸\r\n"+
					   "URL:http://www.wzrdoll.com\r\n"+
					   "EMAIL;HOME:252231254@qq.com\r\n" + 
					   "PHOTO;VALUE=uri:http://www.wzrdoll.com/images/logo.jpg\r\n" + 
					   "END:VCARD";

		//System.out.println("前version:"+qrcode.getQrcodeVersion());
		boolean[][] calQrcode = qrcode.calQrcode(content1.getBytes("utf-8"));
		int version = qrcode.getQrcodeVersion();
		//System.out.println("后version:"+version);
		int imgSize = 67+(version-1)*12;
		BufferedImage bufferedImage = new BufferedImage(imgSize,imgSize,BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, imgSize, imgSize);
		int pixoff = 2;
		String startRGB = "255,0,0";
		String endRBG = "0,0,255";
		int startR = 0;
		int startG = 0;
		int startB = 0;
		if(null != startRGB) {
			String[] split = startRGB.split(",");
			startR = Integer.valueOf(split[0]);
			startG = Integer.valueOf(split[1]);
			startB = Integer.valueOf(split[2]);
		}
		int endR = 0;
		int endG = 0;
		int endB = 0;
		if(null != endRBG) {
			String[] split = endRBG.split(",");
			endR = Integer.valueOf(split[0]);
			endG = Integer.valueOf(split[1]);
			endB = Integer.valueOf(split[2]);
		}
		for (int i = 0; i < calQrcode.length ;i++) {
			for (int j = 0; j < calQrcode[i].length; j++) {
				if(calQrcode[i][j]){
					int r = startR + (endR - startR)*(i+1)/calQrcode.length;				
					int g = startG + (endG - startG)*(i+1)/calQrcode.length;				
					int b = startB + (endB - startB)*(i+1)/calQrcode.length;
					if (r>225){
						r = 255;
					}
					if (r>255){
						r = 255;
					}
					if (r>255){
						r = 255;
					}
					Color color = new Color(r,g,b);
					gs.setColor(color);
					gs.fillRect(3*i+pixoff, 3*j+pixoff, 3, 3);
				}
			}
		}
		BufferedImage logo = ImageIO.read(new File("D:/logo.jpg"));
		int logoSize = imgSize/4;
		int o =(imgSize-logoSize)/2;
		gs.drawImage(logo, o, o, logoSize, logoSize, null);
		gs.dispose();
		bufferedImage.flush();
		ImageIO.write(bufferedImage, "png", new File("D:/QRcode.png"));
		System.out.println("完成了");
	}

}

package javacommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCode {

	// 验证码字符个数
	private static int codeQuantity = 4;

	// 验证码图片的宽度。
	private static int imgWidth = 60;
	// 验证码图片的高度。
	private static int imgHeight = 38;
	// 字体大小
	private static int fontSize = 20;
	// 边框颜色
	private static Color imgFrameColor = Color.WHITE;
	// 图片背景颜色
	private static Color imgBgColor = Color.white;
	// 线条颜色
	private static Color imgLineColor = Color.GREEN;
	// 干扰线条数
	private static int lineQuantity = 10;

	private static Logger log = LoggerFactory.getLogger(VerificationCode.class);

	static char[] codeSequence = { 'A', 'B', 'C', '7', 'E', '4', 'G', 'H', 'J',
			'K', '2', 'W', 'M', 'N', 'P', 'Q', 'R', '9', 'T', 'U', 'V', '6',
			'X', 'Y', 'Z', 'L', '3', 'F', '5', 'D', '8', 'S' };

	public static String getVerificationCode() {
		String code = null;
		try {
			// randomCode随机产生的验证码
			StringBuffer randomCode = new StringBuffer();
			for (int i = 0; i < codeQuantity; i++) {
				// 得到随机产生的验证码。
				String strRand = String.valueOf(codeSequence[new Random()
						.nextInt(25)]);
				// 将产生的四个随机数组合在一起。
				randomCode.append(strRand);
			}
			code = randomCode.toString();
		} catch (Exception e) {
			log.error("get code error", e);
		}
		return code;
	}

	public static void getBSCodes(HttpServletRequest request,
			HttpServletResponse response, String[] redisRandomCode) {
		try {

			// 定义图像buffer
			BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();
			// 创建一个随机数生成器类
			Random random = new Random();
			// 将图像填充为白色
			g.setColor(imgBgColor);
			g.fillRect(0, 0, imgWidth, imgHeight);
			// 创建字体
			Font font = new Font("宋体", Font.BOLD, fontSize);
			// 设置字体。
			g.setFont(font);
			// 画边框。
			g.setColor(imgFrameColor);
			g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);

			int red = 0, green = 0, blue = 0;

			for (int i = 0; i < lineQuantity; i++) {

				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 设置线条颜色随机
				g.setColor(new Color(red, green, blue));

				int x1 = random.nextInt(imgWidth);
				int y1 = random.nextInt(imgHeight);
				int x2 = random.nextInt(12);
				int y2 = random.nextInt(12);
				// 绘制线条
				g.drawLine(x1, y1, x1 + x2, y1 + y2);
			}

			// 如果redis中有直接返回
			if (null != redisRandomCode) {
				for (int i = 0; i < redisRandomCode.length; i++) {
					// 得到随机产生的验证码。
					String strRand = redisRandomCode[i];
					// 产生随机的颜色分量来构造颜色值，输出的每位数字的颜色值都将不同。
					red = random.nextInt(255);
					green = random.nextInt(255);
					blue = random.nextInt(255);
					// 用随机产生的颜色将验证码绘制到图像中。
					g.setColor(new Color(red, green, blue));
					g.drawString(strRand, (i + 1) * 11, 25);
				}
			}
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", -1);
			response.setContentType("image/jpeg");

			// 输出图像
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(buffImg, "jpeg", sos);
			sos.close();
			sos.flush();

		} catch (Exception e) {
			log.error("get imageCode error", e);
		}
	}

}

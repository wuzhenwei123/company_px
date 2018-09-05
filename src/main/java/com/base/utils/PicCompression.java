package com.base.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
public class PicCompression {
	/**
	 * 
	 * @param originalFile
	 *            原文件
	 * @param resizedFile
	 *            新文件
	 * @param newWidth
	 *            宽度
	 * @param quality
	 *            质量(0-1)
	 * @throws IOException
	 */
	public static void resize(File originalFile, File resizedFile, int newWidth) {

		try {
			BufferedImage src = ImageIO.read(originalFile); // 读入文件

			// 为等比压缩计算输出的宽高
			double rate = ((double) src.getWidth(null)) / (double) newWidth;

			int new_w = (int) (src.getWidth(null) / rate);
			int new_h = (int) (src.getHeight(null) / rate);
			String ext = FilenameUtils.getExtension(originalFile.getAbsolutePath());// 扩展名
			Image image = src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING);
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.SCALE_DEFAULT);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, ext, resizedFile);// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // Example usage

	public static void resize(File originalFile, File resizedFile) {

		try {
			BufferedImage src = ImageIO.read(originalFile); // 读入文件

			// 为等比压缩计算输出的宽高
			int new_w = src.getWidth(null);
			int new_h = src.getHeight(null);
			String ext = FilenameUtils.getExtension(originalFile.getAbsolutePath());// 扩展名
			Image image = src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING);
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, ext, resizedFile);// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // Example usage
	public static void main(String[] args) {
		resize(new File("D:\\6F4A2938.JPG"),new File("D:\\6F4A2938.JPG"));
	}
	/**
	 * 压缩图片方法
	 * 
	 * @param oldFile
	 *            要压缩的图片路径
	 * @param newFile
	 *            新文件
	 * @param width
	 *            压缩宽
	 * @param height
	 *            压缩高
	 * @param percentage
	 *            是否等比例压缩,true则宽高比自动调整
	 * @return
	 * @throws Exception
	 */
	public static void reduceImg(String oldFile, String newFilePath, int widthdist, int heightdist, boolean percentage) {
		try {
			File srcfile = new File(oldFile);
			if (!srcfile.exists()) {
				return;
			}
			File newFile = new File(newFilePath);
			if(!newFile.exists())
				newFile.createNewFile();
			Image src = javax.imageio.ImageIO.read(srcfile);
			if (percentage) {
				// 为等比压缩计算输出的宽高
				double rate1 = ((double) src.getWidth(null)) / (double) widthdist + 0.1;
				double rate2 = ((double) src.getHeight(null)) / (double) heightdist + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;

				int new_w = (int) (src.getWidth(null) / rate);
				int new_h = (int) (src.getHeight(null) / rate);
				// 设定宽高
				BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);

				// 设定文件扩展名
				String filePrex = FilenameUtils.getExtension(oldFile);
				// 生成图片
				// 两种方法,效果与质量都相同,效率差不多
				// tag.getGraphics().drawImage(src.getScaledInstance(widthdist,heightdist,
				// Image.SCALE_SMOOTH), 0, 0, null);
				tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null);
				ImageIO.write(tag, filePrex , newFile);
			} else {
				// 设定宽高
				BufferedImage tag = new BufferedImage(widthdist, heightdist, BufferedImage.TYPE_INT_RGB);
				// 设定文件扩展名
				String filePrex = FilenameUtils.getExtension(oldFile);
				// 生成图片
				// 两种方法,效果与质量都相同,第二种效率比第一种高,约一倍
				// tag.getGraphics().drawImage(src.getScaledInstance(widthdist,
				// heightdist, Image.SCALE_SMOOTH), 0, 0, null);
				tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);
				ImageIO.write(tag, filePrex , newFile);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 压缩图片方法 生成无后缀文件
	 * 
	 * @param oldFile
	 *            要压缩的图片路径
	 * @param newFile
	 *            新文件
	 * @param width
	 *            压缩后的宽
	 * @param height
	 *            压缩后的高
	 * @param percentage
	 *            是否等比例压缩,true则宽高比自动调整
	 * @return
	 * @throws Exception
	 */
	public static void reduceUserIcon(String oldFile, String newFilePath, int widthdist, int heightdist, boolean percentage) {
		try {
			File srcfile = new File(oldFile);
			if (!srcfile.exists()) {
				return;
			}
			File newFile = new File(newFilePath);
			if(!newFile.exists())
				newFile.createNewFile();
			Image src = javax.imageio.ImageIO.read(srcfile);
			if (percentage) {
				// 为等比压缩计算输出的宽高
				double rate1 = ((double) src.getWidth(null)) / (double) widthdist + 0.1;
				double rate2 = ((double) src.getHeight(null)) / (double) heightdist + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;

				int new_w = (int) (src.getWidth(null) / rate);
				int new_h = (int) (src.getHeight(null) / rate);
				// 设定宽高
				BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);

				// 设定文件扩展名
				String filePrex = FilenameUtils.getExtension(oldFile);
				tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null);
				ImageIO.write(tag, filePrex , newFile);
			} else {
				// 设定宽高
				BufferedImage tag = new BufferedImage(widthdist, heightdist, BufferedImage.TYPE_INT_RGB);
				// 设定文件扩展名
				String filePrex = FilenameUtils.getExtension(oldFile);
				tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);
				ImageIO.write(tag, filePrex , newFile);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
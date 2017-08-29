package com.test.zhao;

public class Test {
	/**
	 * 图片上传（等比例压缩）
	 * tempFile  上传的图片路径
	 * fileName  图片保存的文件名
	 * targetDir 保存目标目录
	 * newWidth  指定缩放宽度  0  默认按0.5比例缩放
	 * newHeight 指定缩放高度  0  默认按0.5比例缩放
	 * @return
	 */
	public static void main(String[] arg){

		String pic="test.gif";
		String tempFile1="/home/zhao/Downloads/pic/"+pic;
		String fileName1="test.gif";
		String targetDir="/tmp/img";
		boolean b= UploadUtil.uploadZip(tempFile1, fileName1, targetDir, 270, 80);
		System.out.println(b);
	}
}

package com.chickensoup.utils;

public class StringUtils {
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||str.trim().length()==0){
			return true;
		}
		return false;
	}
}

package com.tdh.common;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UtilAPI {
	
	
	public static boolean isNumeric(String str){  
		   for(int i=str.length();--i>=0;){  
		      int chr=str.charAt(i);  
		      if(chr<48 || chr>57)  
		         return false;  
		   }  
		   return true;  
		}   

	public static boolean retainAll(String[] arr_a, String[] arr_b) {
		boolean has = false;
		HashSet<String> set = new HashSet<>(Arrays.asList(arr_a));
		set.retainAll(Arrays.asList(arr_b));
		if (set.size() > 0) {
			has = true;
		}
		return has;
	}

	public static String arrToString(String exam_req_ids) {
		String exam_req_id = "";
		for (int i = 0; i < exam_req_ids.split(",").length; i++) {
			if (i == 0) {
				exam_req_id = "'" + exam_req_ids.split(",")[i] + "'";
			} else {
				exam_req_id = exam_req_id + ",'" + exam_req_ids.split(",")[i] + "'";
			}
		}
		return exam_req_id;
	}
	
	public static String arrToString2(String[] exam_req_ids) {
		String exam_req_id = "";
		for (int i = 0; i < exam_req_ids.length; i++) {
			if (i == 0) {
				exam_req_id = "'" + exam_req_ids[i] + "'";
			} else {
				exam_req_id = exam_req_id + ",'" + exam_req_ids[i] + "'";
			}
		}
		return exam_req_id;
	}
	
	public static String arrToString3(String[] exam_req_ids) {
		String exam_req_id = "";
		for (int i = 0; i < exam_req_ids.length; i++) {
			if (i == 0) {
				exam_req_id = "" + exam_req_ids[i] + "";
			} else {
				exam_req_id = exam_req_id + "," + exam_req_ids[i] + "";
			}
		}
		return exam_req_id;
	}

	public static String messageBeanToJson(MessageBean messageBean) {
		return JSONObject.fromObject(messageBean).toString();
	}

	/**
	 * 转换为int类型
	 * 
	 * @param obj
	 * @return int 空返回0
	 */
	public static int turnInt(Object obj) {
		int i = 0;
		if (UtilAPI.isNull(obj)) {
			return i;
		}
		try {
			if (obj instanceof Double) {
				Double d = (Double) obj;
				return d.intValue();
			}
			if (obj instanceof Float) {
				Float f = (Float) obj;
				return f.intValue();
			}
			i = Integer.valueOf(obj.toString());
		} catch (Exception e) {
			// 类型转换异常
			e.printStackTrace();
			return 0;
		}
		return i;
	}

	public static Integer turnGotoArrangeNo(String visit_seq) {
		String return_sql = "";
		if (visit_seq != null && visit_seq.length() > 0) {
			char num[] = visit_seq.toCharArray();
			for (int i = 0; i < num.length; i++) {
				if (Character.isDigit(num[i])) {
					return_sql = return_sql + num[i];
				}
			}
			return Integer.valueOf(return_sql);
		}
		return 0;
	}

	/**
	 * 判断是否为null
	 * 
	 * @param strData
	 * @return boolean
	 */
	public static boolean isNull(Object strData) {
		if (strData == null || String.valueOf(strData).trim().equals("")) {
			return true;
		}
		return false;
	}

	public static File saveFile(MultipartFile execlFile, String filePath) throws IOException {
		InputStream is = execlFile.getInputStream();
		File folderFile = new File(filePath);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}

		File fileFile = new File(filePath + System.currentTimeMillis() + ".xls");
		OutputStream os = new FileOutputStream(fileFile);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		is.close();
		return fileFile;
	}

	/**
	 * 判断List是否为null
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isListNull(List list) {
		if (list == null || list.isEmpty() || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Map转String
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String selectMapToString(Map<String, Object> map, String key) {
		if (map == null && key != null) {
			return null;
		} else {
			String upper = key.toUpperCase();
			if (map.get(upper) != null && map.get(upper).toString().length() > 0) {
				return map.get(upper).toString();
			}
			String lower = key.toLowerCase();
			if (map.get(lower) != null && map.get(lower).toString().length() > 0) {
				return map.get(lower).toString();
			}
			return null;
		}
	}

	/**
	 * Map转Integer
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Integer selectMapToInt(Map<String, Object> map, String key) {
		if (map == null && key != null) {
			return null;
		} else {
			String upper = key.toUpperCase();
			if (map.get(upper) != null && map.get(upper).toString().length() > 0) {
				return Integer.valueOf(map.get(upper).toString());
			}
			String lower = key.toLowerCase();
			if (map.get(lower) != null && map.get(lower).toString().length() > 0) {
				return Integer.valueOf(map.get(lower).toString());
			}
			return null;
		}
	}

	/**
	 * Map转Integer
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Double selectMapToDouble(Map<String, Object> map, String key) {
		if (map == null && key != null) {
			return null;
		} else {
			String upper = key.toUpperCase();
			if (map.get(upper) != null && map.get(upper).toString().length() > 0) {
				return Double.valueOf(map.get(upper).toString());
			}
			String lower = key.toLowerCase();
			if (map.get(lower) != null && map.get(lower).toString().length() > 0) {
				return Double.valueOf(map.get(lower).toString());
			}
			return null;
		}
	}

	public static String returnArrangeNo(String arrange_no) {
		System.err.println(arrange_no);
		if (arrange_no.indexOf(".") > -1) {
			// System.err.println(arrange_no.split("\\.")[0]+"."+(Integer.valueOf(arrange_no.split("\\.")[1])+(0.001)));
			String h = String.valueOf(Integer.valueOf(arrange_no.split("\\.")[1]) + 1);
			if (h.length() == 1) {
				return arrange_no.split("\\.")[0] + ".00" + h;
			} else if (h.length() == 2) {
				return arrange_no.split("\\.")[0] + ".0" + h;
			} else {
				return arrange_no.split("\\.")[0] + "." + h;
			}
		} else {
			// System.err.println( arrange_no+".001");
			return arrange_no + ".001";
		}
	}

}

package cn.lannis.codemaker.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

/**
 * <p>描述：表转换相关工具类</p>
 * <p>公司：Lannis©2021 All Rights Reserved</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2021-01-04 14:37</p>
 * <p>版权：Lannis-2021</p>
 */
@Log4j2
public class TableConvert {
	private TableConvert(){};
	public static String getNullAble(String nullable) {
		if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable)) || ("f".equals(nullable))) {
			return "Y";
		}
		if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable)) || ("t".equals(nullable))) {
			return "N";
		}
		return null;
	}

	public static String getNullString(String nullable) {
		if (StringUtils.isEmpty(nullable)) {
			return "";
		}
		return nullable;
	}

	public static String getV(String s) {
		return "'" + s + "'";
	}
}

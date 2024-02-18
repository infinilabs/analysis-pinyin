package org.nlpcn.commons.lang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjConver {

	public static final String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Double getDouble(String value) {
		return castToDouble(value);
	}

	public static Double getDoubleValue(String value) {
		if (StringUtil.isBlank(value)) {
			return 0D;
		}
		return castToDouble(value);
	}

	public static Float getFloat(String value) {
		if (StringUtil.isBlank(value)) {
			return null;
		}
		return castToFloat(value);
	}

	public static Float getFloatValue(String value) {
		return castToFloat(value).floatValue();
	}

	public static Integer getInteger(String value) {
		return castToInteger(value);
	}

	public static int getIntValue(String value) {
		if (StringUtil.isBlank(value)) {
			return 0;
		}
		return castToInteger(value);
	}

	public static Date getDate(String value) {
		if (StringUtil.isBlank(value)) {
			return null;
		}
		return castToDate(value);
	}

	public static Long getLong(String value) {
		return castToLong(value);
	}

	public static long getLongValue(String value) {
		if (StringUtil.isBlank(value)) {
			return 0L;
		}
		return castToLong(value);
	}

	public static Boolean getBoolean(String value) {
		return castToBoolean(value);
	}

	public static boolean getBooleanValue(String value) {
		if (StringUtil.isBlank(value)) {
			return false;
		}
		return castToBoolean(value);

	}

	public static final Float castToFloat(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}

		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0) {
				return null;
			}

			return Float.parseFloat(strVal);
		}

		throw new ClassCastException("can not cast to float, value : " + value);
	}

	public static final Double castToDouble(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		} else if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0) {
				return null;
			}
			return Double.parseDouble(strVal);
		}
		throw new RuntimeException("can not cast to double, value : " + value);
	}

	public static final Date castToDate(Object value) {
		if (value == null) {
			return null;
		}
		long longValue = -1;

		if(value instanceof Date){
			return (Date) value ;
		}else if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		} else if (value instanceof String) {
			String strVal = (String) value;

			if (strVal.indexOf('-') != -1) {
				String format = null;
				if (strVal.length() == DEFFAULT_DATE_FORMAT.length()) {
					format = DEFFAULT_DATE_FORMAT;
				} else if (strVal.length() == 10) {
					format = "yyyy-MM-dd";
				} else if (strVal.length() == "yyyy-MM-dd HH".length()) {
					format = "yyyy-MM-dd HH";
				} else if (strVal.length() == "yyyy-MM-dd HH:mm".length()) {
					format = "yyyy-MM-dd HH:mm";
				} else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
					format = "yyyy-MM-dd HH:mm:ss";
				} else if (strVal.length() == "yyyy-MM-dd HH:mm:SSS".length()) {
					format = "yyyy-MM-dd HH:mm:ss.SSS";
				} else {
					return null;
				}

				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				try {
					return dateFormat.parse(strVal);
				} catch (ParseException e) {
					throw new RuntimeException("can not cast to Date, value : " + strVal);
				}
			}

			if (strVal.length() == 0) {
				return null;
			}

			longValue = Long.parseLong(strVal);
		}

		if (longValue < 0) {
			throw new ClassCastException("can not cast to Date, value : " + value);
		}

		return new Date(longValue);
	}

	public static final Long castToLong(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Number) {
			return ((Number) value).longValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}

			try {
				return Long.parseLong(strVal);
			} catch (NumberFormatException ex) {
			}

			Date date = castToDate(strVal);

			if (date != null) {
				return date.getTime();
			}
		}

		throw new ClassCastException("can not cast to long, value : " + value);
	}

	public static final Integer castToInteger(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value instanceof Number) {
			return ((Number) value).intValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}

			return Integer.parseInt(strVal);
		}

		throw new ClassCastException("can not cast to int, value : " + value);
	}

	public static final Boolean castToBoolean(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		if (value instanceof Number) {
			return ((Number) value).intValue() == 1;
		}

		if (value instanceof String) {
			String str = (String) value;
			if (str.length() == 0) {
				return null;
			}

			if ("true".equalsIgnoreCase(str)) {
				return Boolean.TRUE;
			}
			if ("false".equalsIgnoreCase(str)) {
				return Boolean.FALSE;
			}

			if ("1".equalsIgnoreCase(str)) {
				return Boolean.TRUE;
			}
		}

		throw new ClassCastException("can not cast to int, value : " + value);
	}

	private static Character castToCharacter(Object value) {

		if (value instanceof Character) {
			return (Character) value;
		}

		if (value instanceof Number) {
			return (char) ((Number) value).intValue();
		}

		if (value != null) {
			return value.toString().trim().charAt(0);
		}

		return null;
	}

	/**
	 * 将一个对象转换为对应的类
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 *
	 * @param <T>
	 * @param value
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T conversion(Object value, Class<T> c) {
		if (String.class.equals(c)) {
			return (T) value;
		} else if (Character.class.equals(c)) {
			return (T) ObjConver.castToCharacter(value);
		} else if (Integer.class.equals(c)) {
			return (T) ObjConver.castToInteger(value);
		} else if (Double.class.equals(c)) {
			return (T) ObjConver.castToDouble(value);
		} else if (Float.class.equals(c)) {
			return (T) ObjConver.castToFloat(value);
		} else if (Long.class.equals(c)) {
			return (T) ObjConver.castToLong(value);
		} else if (Boolean.class.equals(c)) {
			return (T) ObjConver.castToBoolean(value);
		} else {
			throw new RuntimeException("not define this class by " + c);
		}
	}

}

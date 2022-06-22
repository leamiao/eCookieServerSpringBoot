package com.memd.ecookie.common;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

	//public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private static JsonHelper instance_ = new JsonHelper();

	public static JsonHelper getInstance() {
		return instance_;
	}

	private JsonObjectMapper objectMapper_;
	
	private JsonHelper () {
		objectMapper_ = new JsonObjectMapper();
		objectMapper_.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-06:00"));
		objectMapper_.setDateFormat(sdf);
	}

	public String convertToJson(Object object) {
		String json = null;
		try {
			json = objectMapper_.writeValueAsString(object);
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
	    }

		return json;
	}

	public <T> T convertFromJson(String jsonString, Class<T> clazz) {
		T t = null;
		try {
			if (!CommonUtil.isEmpty(jsonString)) {
				t =  objectMapper_.readValue(jsonString, clazz);
			}
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
	    }

		return t;
	}

	public <T> List<T> convertFromJsonToList(String jsonString, Class<T> clazz) {
		List<T> t = null;
		try {
			if (jsonString != null && !jsonString.isEmpty()) {
				t =  objectMapper_.readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
			}
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
	    }

		return t;
	}
	
	public <T> List<T> convertFromJsonToList(File file, Class<T> clazz) {
		List<T> t = null;
		try {
			if (file != null) {
				t =  objectMapper_.readValue(file, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException("Could not convert JSON to List.", e);
	    }

		return t;
	}

	public <T> List<T> convertFromJsonToList(InputStream is, Class<T> clazz) {
		List<T> t = null;
		try {
			if (is != null) {
				t =  objectMapper_.readValue(is, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException("Could not convert JSON to List.", e);
	    }

		return t;
	}

	public <K, V> Map<K, V> convertFromJsonToMap(String jsonString, Class<K> keyType, Class<V> valueType) {
		Map<K, V> map = null;
		try {
			if (!CommonUtil.isEmpty(jsonString)) {
				map =  objectMapper_.readValue(jsonString, TypeFactory.defaultInstance().constructMapLikeType(LinkedHashMap.class, keyType, valueType));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException("Could not convert JSON to List.", e);
	    }

		return map;
	}

	public <K, V> Map<K, V> convertFromJsonToMap(File file, Class<K> keyType, Class<V> valueType) {
		Map<K, V> map = null;
		try {
			if (file != null) {
				map =  objectMapper_.readValue(file, TypeFactory.defaultInstance().constructMapLikeType(LinkedHashMap.class, keyType, valueType));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException("Could not convert JSON to List.", e);
	    }

		return map;
	}

	public <K, V> Map<K, V> convertFromJsonToMap(InputStream is, Class<K> keyType, Class<V> valueType) {
		Map<K, V> map = null;
		try {
			if (is != null) {
				map =  objectMapper_.readValue(is, TypeFactory.defaultInstance().constructMapLikeType(LinkedHashMap.class, keyType, valueType));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException("Could not convert JSON to List.", e);
	    }

		return map;
	}

	public <T> T convertFromJson(File file, Class<T> clazz) {
		T t = null;
		try {
			if (file != null) {
				t =  objectMapper_.readValue(file, clazz);
			}
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
	    }

		return t;
	}	
	
	public <T> T convertFromJson(InputStream is, Class<T> clazz) {
		T t = null;
		try {
			if (is != null) {
				t =  objectMapper_.readValue(is, clazz);
			}
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
	    }

		return t;
	}
	
	public JsonNode convertFromJson(String content) {
		try {
			return this.objectMapper_.readTree(content);
		} catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public ObjectNode createObjectNode() {
		return this.objectMapper_.createObjectNode();
	}
}


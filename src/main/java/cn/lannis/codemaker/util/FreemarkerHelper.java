package cn.lannis.codemaker.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class FreemarkerHelper {
	@SuppressWarnings("deprecation")
	public static Configuration newFreeMarkerConfiguration(List<File> templateRootDirs, String defaultEncoding, String templateName) throws IOException {
		Configuration conf = new Configuration();

		FileTemplateLoader[] templateLoaders = new FileTemplateLoader[templateRootDirs.size()];
		for (int i = 0; i < templateRootDirs.size(); i++) {
			templateLoaders[i] = new FileTemplateLoader((File) templateRootDirs.get(i));
		}
		MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders);

		conf.setTemplateLoader(multiTemplateLoader);
		conf.setNumberFormat("###############");
		conf.setBooleanFormat("true,false");
		conf.setDefaultEncoding(defaultEncoding);

		return conf;
	}

	public static List<String> getParentPaths(String templateName, String suffix) {
		String[] array = tokenizeToStringArray(templateName, "\\/");
		List<String> list = new ArrayList<String>();
		list.add(suffix);
		list.add(File.separator + suffix);
		String path = "";
		for (int i = 0; i < array.length; i++) {
			path = path + File.separator + array[i];
			list.add(path + File.separator + suffix);
		}
		return list;
	}

	public static String[] tokenizeToStringArray(String str, String seperators) {
		if (str == null) {
			return new String[0];
		}
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List<Object> result = new ArrayList<>();
		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return result.toArray(new String[result.size()]);
	}

	public static String processTemplateString(String templateString, Map<?, ?> model, Configuration conf) {
		StringWriter out = new StringWriter();
		try {
			Template template = new Template("模板字符串", new StringReader(templateString), conf);
			template.process(model, out);
			return out.toString();
		} catch (Exception e) {
			throw new IllegalStateException("cannot process templateString:" + templateString + " cause:" + e, e);
		}
	}

	public static void processTemplate(Template template, Map<?, ?> model, File outputFile, String encoding) throws IOException, TemplateException {
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), encoding));
		template.process(model, out);
		out.close();
	}
}

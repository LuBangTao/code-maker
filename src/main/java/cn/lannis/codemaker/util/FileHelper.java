package cn.lannis.codemaker.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class FileHelper {

	public static List<File> searchAllNotIgnoreFile(File dir) {
		ArrayList<File> arrayList = new ArrayList<>();
		searchAllNotIgnoreFile(dir, arrayList);
		arrayList.sort(Comparator.comparing(File::getAbsolutePath));
		return arrayList;
	}

	public static void searchAllNotIgnoreFile(File dir, List<File> collector) {
		if ((!dir.isHidden()) && (dir.isDirectory()) && (!isIgnoreFile(dir))) {
			File[] subFiles = dir.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				searchAllNotIgnoreFile(subFiles[i], collector);
			}
		} else if ((!isIgnoreEndWithFile(dir)) && (!isIgnoreFile(dir))) {
			collector.add(dir);
		}
	}

	public static String getRelativePath(File baseDir, File file) {
		if (baseDir.equals(file)) {
			return "";
		}
		if (baseDir.getParentFile() == null) {
			return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		}
		return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
	}

	public static boolean isBinaryFile(File file) {
		if (file.isDirectory()) {
			return false;
		}
		return isBinaryFile(file.getName());
	}

	public static boolean isBinaryFile(String filename) {
		if (StringUtils.isEmpty(getExtension(filename))) {
			return false;
		}
		return true;
	}

	public static String getExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int index = filename.indexOf(".");
		if (index == -1) {
			return "";
		}
		return filename.substring(index + 1);
	}

	public static File parentMkdir(String file) {
		if (file == null) {
			throw new IllegalArgumentException("file must be not null");
		}
		File result = new File(file);
		parentMkdir(result);
		return result;
	}

	private static void parentMkdir(File outputFile) {
		if (outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
	}

	public static List<String> ignoreList = new ArrayList<>();
	public static List<String> ignoreEndWithList = new ArrayList<>();

	static {
		ignoreList.add(".svn");
		ignoreList.add("CVS");
		ignoreList.add(".cvsignore");
		ignoreList.add(".copyarea.db");
		ignoreList.add("SCCS");
		ignoreList.add("vssver.scc");
		ignoreList.add(".DS_Store");
		ignoreList.add(".git");
		ignoreList.add(".gitignore");
		ignoreEndWithList.add(".ftl");
	}

	private static boolean isIgnoreFile(File file) {
		for (String s : ignoreList) {
			if (file.getName().equals(s)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isIgnoreEndWithFile(File file) {
		for (String s : ignoreEndWithList) {
			if (file.getName().endsWith(s)) {
				return true;
			}
		}
		return false;
	}
}

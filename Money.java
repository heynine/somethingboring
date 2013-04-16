package com.heynine.money;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Money {

	private List<Map<String, String>> mOldDatas;

	private int[] countReds;

	private int[] countBlues;

	private String[] redNumbers = new String[] { "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
			"28", "29", "30", "31", "32", "33", "34", "35" };
	private String[] blueNumbers = new String[] { "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" };

	/**
	 * 初始化数据
	 * 
	 * @throws Exception
	 */
	private void initData() throws Exception {
		mOldDatas = new ArrayList<Map<String, String>>();
		File dataFile = new File("datas.txt");
		BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		String line = null;
		while ((line = reader.readLine()) != null) {
			Map<String, String> map = new HashMap<String, String>();
			// 得到时间
			String date = line.substring(0, 10);
			map.put("date", date);
			// 取得第几期
			String number = line.substring(11, 18);
			map.put("number", number);
			// 红色号码
			String red = line.substring(20, 37);
			map.put("red", red);
			// 蓝色号码
			String blue = line.substring(38, 40);
			map.put("blue", blue);
			mOldDatas.add(map);
		}
		reader.close();
	}

	/**
	 * 统计数据
	 */
	private void count() {
		for (int i = 0; i < mOldDatas.size(); i++) {
			Map<String, String> mapNow = mOldDatas.get(i);
			String[] redNow = mapNow.get("red").split(" ");
			String blueNow = mapNow.get("blue");
			// 蓝色出现的次数包括现在的次数，所以初始化为1
			int countBlue = 1;
			int[] countRed = new int[redNow.length];
			// //初始化红色号码统计次数为1
			for (int n = 0; n < countRed.length; n++) {
				countRed[n] = 1;
			}

			// 统计红色号码次数
			String strCountRed = "";
			for (int m = 0; m < redNow.length; m++) {
				for (int j = i + 1; j < mOldDatas.size(); j++) {
					Map<String, String> mapOld = mOldDatas.get(j);
					String redOld = mapOld.get("red");
					if (redOld.contains(redNow[m])) {
						countRed[m]++;
					}
				}
				// 对齐处理
				if (m == redNow.length - 1) {
					if (countRed[m] < 10) {
						strCountRed = strCountRed + countRed[m] + " ";
					} else {
						strCountRed = strCountRed + countRed[m];
					}
				} else {
					if (countRed[m] < 10) {
						strCountRed = strCountRed + countRed[m] + "  ";
					} else {
						strCountRed = strCountRed + countRed[m] + " ";
					}
				}

			}
			// 保存
			mapNow.put("countred", strCountRed);

			// 统计蓝色号码次数
			for (int j = i + 1; j < mOldDatas.size(); j++) {
				Map<String, String> mapOld = mOldDatas.get(j);
				String blueOld = mapOld.get("blue");
				if (blueNow.equals(blueOld)) {
					countBlue++;
				}
			}
			mapNow.put("countblue", countBlue + "");
		}
	}

	/**
	 * 数据数据
	 * 
	 * @throws Exception
	 */
	private void output() throws Exception {
		System.out.println("开始统计");
		FileOutputStream out = new FileOutputStream(new File("out.txt"));
		PrintStream p = new PrintStream(out);

		// 全局统计红球
		p.println("红球统计：");
		for (int i = 0; i < redNumbers.length; i++) {
			p.print(redNumbers[i] + "  ");
		}
		p.println();
		for (int i = 0; i < countReds.length; i++) {
			if (countReds[i] < 10) {
				p.print(countReds[i] + "   ");
			} else {
				p.print(countReds[i] + "  ");
			}

		}
		p.println();
		// 全局统计篮球
		p.println("篮球统计：");
		for (int i = 0; i < blueNumbers.length; i++) {
			p.print(blueNumbers[i] + "  ");
		}
		p.println();
		for (int i = 0; i < countBlues.length; i++) {
			if (countBlues[i] < 10) {
				p.print(countBlues[i] + "   ");
			} else {
				p.print(countBlues[i] + "  ");
			}

		}
		p.println();
		p.println();
		for (int i = 0; i < mOldDatas.size(); i++) {
			Map<String, String> map = mOldDatas.get(i);
			System.out.println("正在统计第 " + map.get("number") + "期 ...");
			p.println(map.get("date") + "   " + map.get("number") + "期");
			p.println("号码：" + map.get("red") + "  " + map.get("blue"));
			p.println("次数：" + map.get("countred") + "  " + map.get("countblue"));
			p.println();
		}
		p.close();
		out.close();
		System.out.println("统计完成！");
	}

	private void countNumber() {
		// 统计35个红色球出现的次数
		countReds = new int[redNumbers.length];
		for (int i = 0; i < redNumbers.length; i++) {
			countReds[i] = 0;
		}

		for (int i = 0; i < redNumbers.length; i++) {
			for (int j = 0; j < mOldDatas.size(); j++) {
				Map<String, String> map = mOldDatas.get(j);
				String red = map.get("red");
				if (red.contains(redNumbers[i])) {
					countReds[i]++;
				}
			}
		}

		// 统计16个蓝色球出现的次数
		countBlues = new int[blueNumbers.length];
		for (int i = 0; i < blueNumbers.length; i++) {
			for (int j = 0; j < mOldDatas.size(); j++) {
				Map<String, String> map = mOldDatas.get(j);
				String red = map.get("blue");
				if (red.contains(blueNumbers[i])) {
					countBlues[i]++;
				}
			}
		}

		// 输出
	}

	public static void main(String[] args) {
		Money money = new Money();
		try {
			money.initData();
			money.count();
			money.countNumber();
			money.output();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

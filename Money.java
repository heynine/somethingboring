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
	 * ��ʼ������
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
			// �õ�ʱ��
			String date = line.substring(0, 10);
			map.put("date", date);
			// ȡ�õڼ���
			String number = line.substring(11, 18);
			map.put("number", number);
			// ��ɫ����
			String red = line.substring(20, 37);
			map.put("red", red);
			// ��ɫ����
			String blue = line.substring(38, 40);
			map.put("blue", blue);
			mOldDatas.add(map);
		}
		reader.close();
	}

	/**
	 * ͳ������
	 */
	private void count() {
		for (int i = 0; i < mOldDatas.size(); i++) {
			Map<String, String> mapNow = mOldDatas.get(i);
			String[] redNow = mapNow.get("red").split(" ");
			String blueNow = mapNow.get("blue");
			// ��ɫ���ֵĴ����������ڵĴ��������Գ�ʼ��Ϊ1
			int countBlue = 1;
			int[] countRed = new int[redNow.length];
			// //��ʼ����ɫ����ͳ�ƴ���Ϊ1
			for (int n = 0; n < countRed.length; n++) {
				countRed[n] = 1;
			}

			// ͳ�ƺ�ɫ�������
			String strCountRed = "";
			for (int m = 0; m < redNow.length; m++) {
				for (int j = i + 1; j < mOldDatas.size(); j++) {
					Map<String, String> mapOld = mOldDatas.get(j);
					String redOld = mapOld.get("red");
					if (redOld.contains(redNow[m])) {
						countRed[m]++;
					}
				}
				// ���봦��
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
			// ����
			mapNow.put("countred", strCountRed);

			// ͳ����ɫ�������
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
	 * ��������
	 * 
	 * @throws Exception
	 */
	private void output() throws Exception {
		System.out.println("��ʼͳ��");
		FileOutputStream out = new FileOutputStream(new File("out.txt"));
		PrintStream p = new PrintStream(out);

		// ȫ��ͳ�ƺ���
		p.println("����ͳ�ƣ�");
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
		// ȫ��ͳ������
		p.println("����ͳ�ƣ�");
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
			System.out.println("����ͳ�Ƶ� " + map.get("number") + "�� ...");
			p.println(map.get("date") + "   " + map.get("number") + "��");
			p.println("���룺" + map.get("red") + "  " + map.get("blue"));
			p.println("������" + map.get("countred") + "  " + map.get("countblue"));
			p.println();
		}
		p.close();
		out.close();
		System.out.println("ͳ����ɣ�");
	}

	private void countNumber() {
		// ͳ��35����ɫ����ֵĴ���
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

		// ͳ��16����ɫ����ֵĴ���
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

		// ���
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

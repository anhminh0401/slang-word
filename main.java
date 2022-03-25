
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Util.Util;

public class main {
    static Scanner ip = new Scanner(System.in);
    static Map<String, String> slangWord = new HashMap<>();
    static Map<String, String> history = new HashMap<>();

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("slang.txt"))) {
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {

                String[] in = temp.split("`");
                slangWord.put(in[0], in[1]);

            }
            bufferedReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int choice = 1;
        while (choice != 0) {
            Util.cleanScreen();
            Util.menu();
            System.out.print("Moi lua chon chuc nang muon su dung: ");
            choice = ip.nextInt();
            System.out.println("--------------------------------------------------");
            ip.nextLine();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    searchSlangWord();
                    break;
                case 2:
                    searchDefinition();
                    break;
                case 3:
                    printHistory();
                    break;
                case 4:

                    break;
            }
        }
        ip.close();
    }

    public static void searchSlangWord() {
        System.out.print("Moi nhap slang word muon tim kiem: ");
        String key = ip.nextLine(); 
        String result = slangWord.get(key);
        if (result == null)
            System.out.printf("Khong ton tai slang word %s", key, " trong he thong!");
        else {
            System.out.printf("Ket qua: %s", result);
        }
        history.put(key,slangWord.get(key));
        ip.nextLine();
    }

    public static void searchDefinition() {
        System.out.print("Moi nhap definition muon tim kiem: ");
        String keyWord = ip.nextLine();

        Map<String, String> result = new HashMap<>();
        boolean check = false;
        for (String key : slangWord.keySet()) {
            String defintition = slangWord.get(key);
            if (defintition.indexOf(keyWord) != -1) {
                result.put(key, slangWord.get(key));
                history.put(key, slangWord.get(key));
                check = true;
            } 
        }
        if (check) {
            System.out.println("Ket qua la: ");
            for (String key : result.keySet()) {
                System.out.printf("%s", key);
                Util.insertSpace(key.length(), 15); // 15 la do dai khoang cach
                System.out.printf("%s\n",result.get(key));
            }
        } else {
            System.out.print("Khong ton tai definition muon tim kiem");
        }
        ip.nextLine();
    }

    public static void printHistory() {
        for (int i = 0; i < history.size(); i++) {
            System.out.printf("%s", history.get(i));
            if (i < history.size() - 1)
                System.out.print(", ");
        }
        ip.nextLine();
    }
}
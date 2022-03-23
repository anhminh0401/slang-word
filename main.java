
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Util.Util;

public class main {
    static Scanner ip = new Scanner(System.in);
    static Map<String, String> slangWord = new HashMap<>();

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

                    break;
                case 3:

                    break;
                case 4:

                    break;
            }
        }
        ip.close();
    }

    public static void searchSlangWord() {
        System.out.print("Moi lua slang word muon tim kiem: ");
        String key = ip.nextLine();
        String result = slangWord.get(key);
        if (result == null)
            System.out.printf("Khong ton tai slang word %s", key, " trong he thong!");
        else {
            System.out.printf("Ket qua: %s", result);
        }
        ip.nextLine();
    }
}
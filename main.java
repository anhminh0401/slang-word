
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Util.Util;

public class main {
    static Scanner ip = new Scanner(System.in);
    static Map<String, String> slangWord = new HashMap<>();
    static ArrayList<String> history = new ArrayList<>();

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

        try (BufferedReader fHistory = new BufferedReader(new FileReader("history.txt"))) {
            int n = Integer.parseInt(fHistory.readLine());
            // fHistory.readLine();
            for (int i = 0; i < n; i++)
                history.add(fHistory.readLine());
            fHistory.close();
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
                    saveFiles();
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
                    addSlangWord();
                    break;
                case 5:
                    editSlangWord();
                    break;
                case 6:
                    delSlangWord();
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
            System.out.printf("Khong ton tai slang word %s\n", key);
        else {
            System.out.printf("Ket qua: %s\n", result);
        }
        // Ghi lịch sử tìm kiếm
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
        String his = date + "     " + key + Util.insertSpace(key.length(), 15) + slangWord.get(key);
        history.add(his);

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
                // Ghi lịch sử tìm kiếm
                LocalDateTime dateTime = LocalDateTime.now();
                String date = dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
                String his = date + "     " + key + Util.insertSpace(key.length(), 15) + slangWord.get(key);
                history.add(his);

                check = true;
            }
        }
        if (check) {
            System.out.println("Ket qua la: ");
            for (String key : result.keySet()) {
                System.out.printf("%s", key);
                System.out.print(Util.insertSpace(key.length(), 15)); // 15 la do dai khoang cach
                System.out.printf("%s\n", result.get(key));
            }
        } else {
            System.out.println("Khong ton tai definition muon tim kiem");
        }
        ip.nextLine();
    }

    public static void printHistory() {
        System.out.println("Lich su tim kiem:");
        if (history.size() < 1) {
            System.out.println("Trong");
        } else {
            for (int i = history.size() - 1; i >= 0; i--) {
                System.out.printf("%s\n", history.get(i));
            }
        }
        ip.nextLine();
    }

    public static void saveFiles() {
        // Lưu file history
        try (BufferedWriter fHistory = new BufferedWriter(new FileWriter("history.txt"))) {
            fHistory.write(String.valueOf(history.size()));
            fHistory.write("\n");
            for (int i = 0; i < history.size(); i++) {
                fHistory.write(history.get(i));
                fHistory.write("\n");
            }
            fHistory.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addSlangWord() {
        System.out.print("Moi nhap Slang word moi: ");
        String key = ip.nextLine();
        if (slangWord.containsKey(key)) {
            System.out.printf("%s da ton tai\n", key);
            ip.nextLine();
        } else {
            System.out.printf("Moi nhap Definition cua %s: ", key);
            String definition = ip.nextLine();
            slangWord.put(key, definition);
            System.out.println("Da them thanh cong");
            ip.nextLine();
        }
    }

    public static void editSlangWord() {
        System.out.print("Moi nhap Slang word muon chinh sua: ");
        String key = ip.nextLine();
        if (!slangWord.containsKey(key)) {
            System.out.printf("%s khong ton tai\n", key);
            ip.nextLine();
        } else {
            System.out.printf("%s", key);
            System.out.print(Util.insertSpace(key.length(), 15)); // 15 la do dai khoang cach
            System.out.printf("%s\n", slangWord.get(key));

            System.out.println("Nhap thong tin muon thay doi, neu khong muon thay doi thi nhan ENTER");
            System.out.print("Slang word: ");
            String keyNew = ip.nextLine();
            while (slangWord.containsKey(keyNew) && !keyNew.equals(key)) {
                System.out.printf("Slang word %s da ton tai, moi nhap lai\n", keyNew);
                System.out.print("Slang word: ");
                keyNew = ip.nextLine();
            }
            System.out.print("Definition: ");
            String definitionNew = ip.nextLine();
            if (keyNew != "" && !keyNew.equals(key)) {
                if (definitionNew != "") {
                    slangWord.put(keyNew, definitionNew);
                } else {
                    slangWord.put(keyNew, slangWord.get(key));
                }
                slangWord.remove(key, slangWord.get(key));
            } else {
                if (definitionNew != "") {
                    slangWord.replace(key, definitionNew);
                }
            }
            System.out.println("Da chinh sua thanh cong");
            ip.nextLine();
        }
    }

    public static void delSlangWord() {
        System.out.print("Moi nhap Slang word muon xoa: ");
        String key = ip.nextLine();
        if (!slangWord.containsKey(key)) {
            System.out.printf("%s khong ton tai\n", key);
            ip.nextLine();
        } else {
            System.out.printf("%s", key);
            System.out.print(Util.insertSpace(key.length(), 15)); // 15 la do dai khoang cach
            System.out.printf("%s\n", slangWord.get(key));

            System.out.printf("Ban co chac chan muon xoa slang word %s khong? (Y/YES, N/NO) ", key);
            while (true) {
                String choice = ip.nextLine();
                choice = choice.toUpperCase();
                if (choice.equals("Y") || choice.equals("YES")) {
                    slangWord.remove(key, slangWord.get(key));
                    System.out.println("Da xoa thanh cong");
                    ip.nextLine();
                    return;
                } else {
                    if (choice.equals("N") || choice.equals("NO"))
                        return;
                }
                System.out.print("Ky tu ban nhap khong dung voi yeu cau, moi nhap lai! (Y/YES, N/NO) ");
            }
        }
    }
}

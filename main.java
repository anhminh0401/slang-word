
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import Util.Util;

public class main {
    static Scanner ip = new Scanner(System.in);
    static Map<String, String> slangWord = new HashMap<>();
    static ArrayList<String> history = new ArrayList<>();
    static Random rand = new Random();

    public static void main(String[] args) {
        // Kiểm tra file tồn tại hay chưa
        File f = new File("slang.txt");
        String dataFile;
        if (f.exists())
            dataFile = "slang.txt";
        else
            dataFile = "slang-origin.txt";

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(dataFile));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                String[] in = temp.split("`");
                slangWord.put(in[0], in[1]);
            }
            bufferedReader.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            BufferedReader fHistory = new BufferedReader(new FileReader("history.txt"));
            int n = Integer.parseInt(fHistory.readLine());
            // fHistory.readLine();
            for (int i = 0; i < n; i++)
                history.add(fHistory.readLine());
            fHistory.close();
        } catch (Exception e) {
            // TODO: handle exception
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
                case 7:
                    resetSlangWord();
                    break;
                case 8:
                    randomSlangWord();
                    break;
                case 9:
                    quizSlangWord();
                    break;
                case 10:
                    quizDefinition();
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
            for (int i = 0; i < history.size(); i++) {
                System.out.printf("%s\n", history.get(i));
            }
        }
        ip.nextLine();
    }

    public static void saveFiles() {
        // Lưu file history
        try {
            BufferedWriter fHistory = new BufferedWriter(new FileWriter("history.txt"));
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

        // Lưu file slangword
        try {
            BufferedWriter fSlang = new BufferedWriter(new FileWriter("slang.txt"));

            for (String key : slangWord.keySet()) {
                fSlang.write(key + "`" + slangWord.get(key));
                fSlang.write("\n");
            }
            fSlang.close();
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

    public static void resetSlangWord() {
        System.out.print("Ban co chac chan muon reset slang word khong? (Y/YES, N/NO) ");
        while (true) {
            String choice = ip.nextLine();
            choice = choice.toUpperCase();
            if (choice.equals("Y") || choice.equals("YES")) {
                slangWord.clear();

                try (BufferedReader bufferedReader = new BufferedReader(new FileReader("slang-origin.txt"))) {
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

                System.out.println("Da reset thanh cong");
                ip.nextLine();
                return;
            } else {
                if (choice.equals("N") || choice.equals("NO"))
                    return;
            }
            System.out.print("Ky tu ban nhap khong dung voi yeu cau, moi nhap lai! (Y/YES, N/NO) ");
        }
    }

    public static void randomSlangWord() {

        System.out.println("               Slang word ngau nhien             ");
        System.out.println("Quy tac: Y/YES de tiep tuc, cac phim khac de thoat");

        String choice = "Y";
        while (true) {
            System.out.println("               ------------");
            if (choice.equals("Y") || choice.equals("YES")) {
                String key = Util.randomSlang(slangWord);
                System.out.printf("%s", key);
                System.out.print(Util.insertSpace(key.length(), 15)); // 15 la do dai khoang cach
                System.out.printf("%s\n", slangWord.get(key));

                System.out.print("Tiep tuc? ");
                choice = ip.nextLine();
                choice = choice.toUpperCase();
            } else
                return;
        }
    }

    public static void quizSlangWord() {
        System.out.println("               Do vui Slang word             ");
        System.out.println("Quy tac: - Y/YES de tiep tuc, cac phim khac de thoat.");
        System.out.println("         - Nhap A, B, C, D de tra loi cau hoi voi dap an tuong ung.");

        String[] ansFormat = { "A", "B", "C", "D" };
        String choice = "Y";
        while (true) {
            System.out.println("               ------------");
            if (choice.equals("Y") || choice.equals("YES")) {
                String key = Util.randomSlang(slangWord);
                System.out.printf("Cau hoi: %s nghia la gi\n", key);

                Set<String> answer = new HashSet<>();
                answer.add(slangWord.get(key));
                while (answer.size() < 4) {
                    String definition = Util.randomDefinition(slangWord);
                    if (definition != null && !definition.equals(slangWord.get(key)))
                        answer.add(definition);
                }

                String ans = "";
                Iterator<String> it1 = answer.iterator();
                for (int i = 1; i <= 4; i++) {
                    String def = it1.next();
                    System.out.printf("%s. %s", ansFormat[i - 1], def);

                    if (def.equals(slangWord.get(key))) // lưu vị trí kết quả
                        ans = ansFormat[i - 1];
                    if (i % 2 == 1)
                        System.out.print(Util.insertSpace(def.length(), 60)); // 60 dựa vào đáp án gần như dài nhất
                    else
                        System.out.println();

                }
                System.out.print("Dap an cua ban: ");
                choice = ip.nextLine();
                choice = choice.toUpperCase();
                if (choice.equals(ans))
                    System.out.print("Chinh xac!!! Ban gioi qua!\n");
                else
                    System.out.printf("Tiec qua @@ Dap an chinh xac phai la %s. %s\n", ans, slangWord.get(key));
                System.out.print("Tiep tuc? ");
                choice = ip.nextLine();
                choice = choice.toUpperCase();
            } else
                return;
        }
    }

    public static void quizDefinition() {
        System.out.println("               Do vui Definition            ");
        System.out.println("Quy tac: - Y/YES de tiep tuc, cac phim khac de thoat.");
        System.out.println("         - Nhap A, B, C, D de tra loi cau hoi voi dap an tuong ung.");

        String[] ansFormat = { "A", "B", "C", "D" };
        String choice = "Y";
        while (true) {
            System.out.println("               ------------");
            if (choice.equals("Y") || choice.equals("YES")) {
                String key = Util.randomSlang(slangWord);
                System.out.printf("Cau hoi: %s la y nghia cua Slang word nao duoi day?\n", slangWord.get(key));

                Set<String> answer = new HashSet<>();
                answer.add(key);
                while (answer.size() < 4) {
                    String slang = Util.randomSlang(slangWord);
                    if (!slang.equals(key) && !slangWord.get(slang).equals(slangWord.get(key)))
                        answer.add(slang);
                }

                String ans = "";
                Iterator<String> it1 = answer.iterator();
                for (int i = 1; i <= 4; i++) {
                    String k = it1.next();
                    System.out.printf("%s. %s", ansFormat[i - 1], k);

                    if (k.equals(key)) // lưu vị trí kết quả
                        ans = ansFormat[i - 1];
                    if (i % 2 == 1)
                        System.out.print(Util.insertSpace(k.length(), 40)); // 60 dựa vào đáp án gần như dài nhất
                    else
                        System.out.println();

                }
                System.out.print("Dap an cua ban: ");
                choice = ip.nextLine();
                choice = choice.toUpperCase();
                if (choice.equals(ans))
                    System.out.print("Chinh xac!!! Ban gioi qua!\n");
                else
                    System.out.printf("Tiec qua @@ Dap an chinh xac phai la %s. %s\n", ans, key);
                System.out.print("Tiep tuc? ");
                choice = ip.nextLine();
                choice = choice.toUpperCase();
            } else
                return;
        }
    }
}

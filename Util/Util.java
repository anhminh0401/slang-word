package Util;

import java.io.IOException;

public class Util {
    public static void menu() {
        System.out.println("----------------------MENU-----------------------");
        System.out.println("1. Tim kiem theo slang word");
        System.out.println("2. Tim kiem theo definition");
        System.out.println("3. Hien thi lich su tim kiem");
        System.out.println("4. Them slang word moi");
        System.out.println("5. Chinh sua slang word");
        System.out.println("6. Xoa slang word");
        System.out.println("7. Reset slang word goc");
        System.out.println("8. Random slang word");
        System.out.println("9. Do vui voi slang word");
        System.out.println("10. Do vui voi definition");
        System.out.println("0. Thoat chuong trinh");
        System.out.println("--------------------------------------------------");
    }

    public static void cleanScreen() {
        // code xóa màn hình tham khảo tại:
        // https://daynhauhoc.com/t/clear-console-screen-trong-java/22290/6
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public static void insertSpace(int content, int maxLength) {
        for (int i = content; i < maxLength; i++)
            System.out.print(" ");
    }
}

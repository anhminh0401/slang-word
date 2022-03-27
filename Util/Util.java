package Util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Util {
    static Random rand = new Random();
    
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

    // hàm tạo khoảng cách để code nhìn đẹp hơn
    public static String insertSpace(int contentLength, int maxLength) {
        String result = "";
        for (int i = contentLength; i < maxLength; i++)
            result = result + " ";
        return result;
    }

    public static String randomSlang(Map<String,String> slangWord) {
        Set<String> keys = slangWord.keySet();
        int pos = rand.nextInt(keys.size());
        Iterator<String> it1 = keys.iterator();
        while (pos > 0) {
            it1.next();
            pos--;
        }
        return it1.next();
    }

    public static String randomDefinition(Map<String,String> slangWord) {
        Set<String> keys = slangWord.keySet();
        int pos = rand.nextInt(keys.size());
        Iterator<String> it1 = keys.iterator();
        while (pos > 0) {
            it1.next();
            pos--;
        }
        
        return slangWord.get(it1.next());
    }
}

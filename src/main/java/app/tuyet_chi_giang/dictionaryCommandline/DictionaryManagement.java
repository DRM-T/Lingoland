
package app.tuyet_chi_giang.dictionaryCommandline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;


    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public void replace(int i, Word word, String path) {
        dictionary.update(i, word);
        exportToFile(path);
    }

    public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        System.out.print("So luong tu ban muon nhap la: ");
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            System.out.println("Word[" + j + "]: ");
            System.out.print("___Target: ");
            String word_target = scan.next();
            System.out.print("___Explain: ");
            String word_explain = scan.nextLine();
            word_explain = scan.nextLine();
            Word newWord = new Word(word_target, word_explain);
            dictionary.addWord(newWord);
        }
        scan.close();
    }

    public void showAll() {
        dictionary.pr();
    }

    public Word getWord(int i) {
        return dictionary.iWord(i);
    }

    public void insertFromFile(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            String target = null;
            String explain = "";

            boolean first = true;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.startsWith("|")) {
                    //System.out.println(line + target);
                    if (target != null) {

                        if (!explain.isEmpty()) {
                            //System.out.println(line + "->" + target);
                            Word word = new Word(target, explain);
                            dictionary.addWord(word);
                            explain = "";
                        }
                    }
                    target = line.substring(1).trim();
                } else {
                    explain += line + "\n";
                }
            }
            if (target != null && !explain.isEmpty()) {
                Word word = new Word(target, explain);
                dictionary.addWord(word);
            }
            System.out.println("Succes reading file" + filePath);
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public void exportToFile(String path) {
        try {
            FileWriter file = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(file);
            for (Word word : dictionary.wordList) {
                bufferedWriter.write("|" + word.getWord_target() + "\n" + word.getWord_explain().trim() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error exportToFile: " + e);
        }
    }

    public void dictionaryLookup() {
        System.out.print("Nhap tu ban muon tra cuu:");
        Scanner scan = new Scanner(System.in);
        String target = scan.next();
        String res = dictionary.explain(target);
        if (res.equals("Tu nay chua co trong tu dien.")) {
            System.out.println(res);
        } else {
            System.out.println("-> " + target + ": " + res);
        }
        scan.close();
    }

    public void add() {
        insertFromCommandline();
    }

    public void add(Word word) {
        dictionary.addWord(word);
    }

    public ObservableList<String> lookupWord(String key) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            List<Integer> results = dictionary.search20(key);
            if (results != null) {

                int length = Math.min(results.size(), 20);
                for (int i = 0; i < length; i++)
                    list.add(dictionary.iWord(results.get(i)).getWord_target());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
        return list;
    }

    public int checkContains(Word word) {
        return dictionary.findIndexWord(word.getWord_target());
    }

    public void insert(int i, Word word) {
        dictionary.update2(i, word);
    }

    public void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public List<Integer> search(String find) {
        return dictionary.search20(find);
    }


    public void delete(int a, String path) {
        dictionary.remove(a);
        exportToFile(path);
    }

    public String mean(String target) {
        return dictionary.meaning(target);
    }

    public String randomTarget() {
        return dictionary.randomChooseTarget();
    }

    public List<Word> random5word() {
        return dictionary.list_5_word_random();
    }

    public List<Word> randomNword(int n) {
        return dictionary.list_n_word_random(n);
    }

    public static void main(String[] args) {
        DictionaryManagement a = new DictionaryManagement();
        a.insertFromFile("src/main/resources/utils/test.txt");
        List<Integer> res = a.search("h");
        for (int i = 0; i < res.size(); i++) {
            a.getWord(res.get(i)).print(i);
        }
    }
}
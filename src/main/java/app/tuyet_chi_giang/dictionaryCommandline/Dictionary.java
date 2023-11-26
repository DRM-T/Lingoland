//<<<<<<< HEAD
package app.tuyet_chi_giang.dictionaryCommandline;

import java.util.*;

public class Dictionary {
    protected List<Word> wordList;

    public Dictionary() {
        wordList = new ArrayList<>();
    }


    public void addWord(Word newWord) {
        if (wordList.isEmpty()) {
            wordList.add(0, newWord);
            return;
        }
        int i = findIndexInsert(newWord.getWord_target());
        wordList.add(i, newWord);
    }

    public int findIndexWord(String target) {
        int begin = 0;
        int end = wordList.size() - 1;
        int mid;

        while (begin <= end) {
            mid = (begin + end) / 2;

            if (wordList.get(mid).getWord_target().equals(target)) {
                return mid; // Trả về vị trí nếu từ đã có trong từ điển
            }

            int compareResult = wordList.get(mid).getWord_target().compareTo(target);
            if (compareResult < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return -1; // Từ không có trong từ điển
    }

    public String meaning(String target) {
        int i = findIndexWord(target);
        if (i == -1) {
            return "-.-";
        }
        return wordList.get(i).getWord_explain();
    }

    public int findIndexInsert(String target) {
        int begin = 0;
        int end = wordList.size() - 1;
        int mid;

        while (begin <= end) {
            mid = begin + (end - begin) / 2;
            int compareResult = wordList.get(mid).getWord_target().compareTo(target);

            if (compareResult == 0) {
                return mid; // Trả về vị trí nếu từ đã có trong danh sách
            } else if (compareResult < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        // Nếu không tìm thấy, trả về vị trí chèn tiềm năng
        return begin;
    }


    public String explain(String word) {
        int i = findIndexWord(word);
        if (i != -1) {
            return wordList.get(i).getWord_explain();
        }
        return "Tu nay chua co trong tu dien.";
    }

    public void pr() {
        if (wordList.isEmpty()) {
            System.out.println("This dict is empty!!!");
            return;
        }
        System.out.println("No        |       Endlish               |       Vietnamese");
        for (int i = 0; i < wordList.size(); i++) {
            Word word = new Word(wordList.get(i));
            word.print(i + 1);
        }
    }

    public boolean contain(String a, String b) { // true if a = b + ...;
        // System.out.println(a+ " " + b);
        int n = a.length();
        for (int i = 0; i < b.length(); i++) {
            if (i >= n) {
                return false;
            }
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Word> list_5_word_random() {
        List<Word> words = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        //System.out.println("5 từ ngẫu nhiên khác nhau từ danh sách là:");
        for (int i = 0; i < 5; i++) {
            words.add(wordList.get(indexes.get(i)));
        }

        return words;

    }

    public List<Word> list_n_word_random(int n) {
        List<Word> words = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        //System.out.println("5 từ ngẫu nhiên khác nhau từ danh sách là:");
        for (int i = 0; i < n; i++) {
            words.add(wordList.get(indexes.get(i)));
        }

        return words;

    }

    public List<Integer> search20(String fword) {
        List<Integer> resList = new ArrayList<>();
        int res = 0;
        int findex = findIndexInsert(fword);
        System.out.println("Da tim thay: " + fword + " tai vi tri " + findex);
        for (int i = findex; i < wordList.size(); i++) {
            if (contain(wordList.get(i).getWord_target(), fword)) {
                resList.add(i);
                res++;
            } else if (res > 0) {
                return resList;
            }
        }
        return resList;
    }

    public void update(int i, Word word) {
        wordList.set(i, word);
    }

    public void update2(int i, Word word) {
        String mean0 = wordList.get(i).getWord_explain();
        String mean1 = word.getWord_explain();
        wordList.get(i).setWord_explain(mean0 + mean1);
    }

    public Word iWord(int i) {
        if (i < 0 || i >= wordList.size()) {
            return null;
        }
        return wordList.get(i);
    }

    public void pr20(String a) {
        List<Integer> findA = search20(a);
        for (int i = 0; i < findA.size(); i++) {
            wordList.get(i).print(i);
        }
        System.out.println(findA.size());
    }

    public String randomChooseTarget() {
        Random random = new Random();
        int randomNumber = random.nextInt(wordList.size() - 1);
        return wordList.get(randomNumber).getWord_target();
    }

    public void remove(int a) {
        wordList.remove(a);
    }

    public static void main(String[] args) {
        Dictionary ya = new Dictionary();
        ya.pr20("h");
    }
}
package app.tuyet_chi_giang.dictionaryCommandline;

import java.util.Objects;

public class Word {
    private String word_target; // tu vung tieng Anh
    private String word_explain; // giai nghia tieng Viet

    public Word() {
        this.word_target = "";
        this.word_explain = "";
    }
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(Word other) {
        this.word_target = other.getWord_target();
        this.word_explain = other.getWord_explain();
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void print(int index) {
        String formattedString = String.format("%-10d%-10s%-20s%-10s%-20s", index, "|", word_target, "|", word_explain);
        System.out.println(formattedString);
    }

    public void print2(int i) {
        System.out.println(i + ". " + word_target);
        System.out.println(word_explain);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return Objects.equals(this.word_target, word.word_target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.word_target, this.word_explain);
    }

    @Override
    public String toString() {
        return "Word{" + "wordTarget='" + this.word_target + '\'' + ", wordExplain='" + this.word_explain + '\'' + '}';
    }
}

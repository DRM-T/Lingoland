
package app.tuyet_chi_giang.dictionaryCommandline;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public void showAllWords() {
        dictionaryManagement.showAll();
    }

//    public void dictionarySearcher() {
//        dictionaryManagement.searcher();
//    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        dictionaryManagement.showAll();
    }
}

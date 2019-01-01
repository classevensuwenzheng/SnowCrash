package cn.edu.hebtu.software.snowcarsh2.bean;

public class Search {
    private String newsTitle;
    private String newUrl;
    private String desc;

    public Search() {
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Search(String newsTitle, String newUrl, String desc) {
        this.newsTitle = newsTitle;
        this.newUrl = newUrl;
        this.desc = desc;
    }
}

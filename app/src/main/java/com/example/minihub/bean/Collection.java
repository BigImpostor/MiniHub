package com.example.minihub.bean;


import java.util.List;

public class Collection {

    private Data data;
    private int errorCode;
    private String errorMsg;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public class Data {

        private int curPage;
        private List<Datas> datas;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }
        public int getCurPage() {
            return curPage;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }
        public List<Datas> getDatas() {
            return datas;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
        public int getOffset() {
            return offset;
        }

        public void setOver(boolean over) {
            this.over = over;
        }
        public boolean getOver() {
            return over;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }
        public int getPageCount() {
            return pageCount;
        }

        public void setSize(int size) {
            this.size = size;
        }
        public int getSize() {
            return size;
        }

        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

    }


    public class Datas {

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;
        public void setAuthor(String author) {
            this.author = author;
        }
        public String getAuthor() {
            return author;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }
        public int getChapterId() {
            return chapterId;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }
        public String getChapterName() {
            return chapterName;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }
        public int getCourseId() {
            return courseId;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }
        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setLink(String link) {
            this.link = link;
        }
        public String getLink() {
            return link;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }
        public String getNiceDate() {
            return niceDate;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }
        public String getOrigin() {
            return origin;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }
        public int getOriginId() {
            return originId;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }
        public long getPublishTime() {
            return publishTime;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
        public int getUserId() {
            return userId;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }
        public int getVisible() {
            return visible;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
        public int getZan() {
            return zan;
        }

    }
}
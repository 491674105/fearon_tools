package utils.page;

public class PageUtil {

    private Integer pageNo;//当前页数
    private Integer pageSize;//每页条数
    private Integer totalNum;//总条数
    private Integer totalPage;//总页数

    private Integer startLoc;//（数据库查询）起始位置


    public PageUtil(Integer pageNo, Integer pageSize, Integer totalNum) {
        if (totalNum == null) {
            totalNum = 0;
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (totalNum + pageSize - 1) / pageSize;
        this.startLoc = (pageNo - 1) * pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totlPage) {
        this.totalPage = totalPage;
    }

    public Integer getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(Integer startLoc) {
        this.startLoc = startLoc;
    }

    @Override
    public String toString() {
        return "PageUtil [pageNo=" + pageNo + ", pageSize=" + pageSize
                + ", totalNum=" + totalNum + ", totlPage=" + totalPage
                + ", startLoc=" + startLoc + "]\n";
    }

}

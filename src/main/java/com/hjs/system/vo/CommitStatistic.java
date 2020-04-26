package com.hjs.system.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/25 15:09
 * @Modified By:
 */
public class CommitStatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    private String githubName;

    private String realName;

    private Integer totalCommit;

    private Integer totalCodeNum;

    private List<Integer> weekCodeNum;

    public String getGithubName() {
        return githubName;
    }

    public void setGithubName(String githubName) {
        this.githubName = githubName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getTotalCommit() {
        return totalCommit;
    }

    public void setTotalCommit(Integer totalCommit) {
        this.totalCommit = totalCommit;
    }

    public Integer getTotalCodeNum() {
        return totalCodeNum;
    }

    public void setTotalCodeNum(Integer totalCodeNum) {
        this.totalCodeNum = totalCodeNum;
    }

    public List<Integer> getWeekCodeNum() {
        return weekCodeNum;
    }

    public void setWeekCodeNum(List<Integer> weekCodeNum) {
        this.weekCodeNum = weekCodeNum;
    }

    @Override
    public String toString() {
        return "CommitStatistic{" +
                "githubName='" + githubName + '\'' +
                ", realName='" + realName + '\'' +
                ", totalCommit=" + totalCommit +
                ", totalCodeNum=" + totalCodeNum +
                ", weekCodeNum=" + weekCodeNum +
                '}';
    }
}

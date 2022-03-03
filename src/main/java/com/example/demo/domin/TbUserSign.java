package com.example.demo.domin;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_user_sign
 * @author 
 */
public class TbUserSign implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 签到表id
     */
    private Long signId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 开始签到日期
     */
    private Date startTime;

    /**
     * 最后签到日期
     */
    private Date endTime;

    /**
     * 总签到次数
     */
    private Integer totalSignNum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalSignNum() {
        return totalSignNum;
    }

    public void setTotalSignNum(Integer totalSignNum) {
        this.totalSignNum = totalSignNum;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TbUserSign other = (TbUserSign) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSignId() == null ? other.getSignId() == null : this.getSignId().equals(other.getSignId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getTotalSignNum() == null ? other.getTotalSignNum() == null : this.getTotalSignNum().equals(other.getTotalSignNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSignId() == null) ? 0 : getSignId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getTotalSignNum() == null) ? 0 : getTotalSignNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", signId=").append(signId);
        sb.append(", userId=").append(userId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", totalSignNum=").append(totalSignNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
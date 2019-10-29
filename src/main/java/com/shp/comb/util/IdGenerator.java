package com.shp.comb.util;


import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.exception.ServerBizException;

/**
 * Created by shp on 17/9/20.
 */
public class IdGenerator {
    private final long pypepoch = 23300000L;
    private volatile long lastts = -1L;
    //序列号所占位数
    private final int sequenceBits = 8;
    //序列号
    private volatile long sequence = 0L;
    //最大序列号
    private final long maxSequence = -1L ^ (-1L << sequenceBits);


    private static class UserIdGeneratorHolder {
        //单例变量
        private static final IdGenerator userIdGenerator = new IdGenerator();
    }

    private IdGenerator(){}

    /**
     * 生成id
     * @return
     */
    public synchronized long generateId() throws ServerBizException {
        long timestamp = currentTimeMintue();

        //当前时间并未调整到下一个,则时间戳++
        if (lastts == timestamp) {
            sequence = (sequence + 1) % maxSequence;
            //序列号已经达到最大,等待下一个时刻继续生产id
            if (sequence == 0) {
                //等待下一秒
                timestamp = tilNextSeconds(lastts);
            }
        }else if(lastts > timestamp){
            //当前时间戳小于已经记录的最大数,说明时间往回调整
            throw new ServerBizException("generate userId exception for "+ (lastts - timestamp) +" milliseconds.", ErrorCodeEnum.SERVER_SYSERR);
        }else {
            //当前时间已经过渡到下一时刻,那么序列号归0
            sequence = 0;
        }
        //记录当前时间戳
        lastts = timestamp;

        return combine(timestamp,sequence);
    }

    /**
     * 合并时间戳和序列号
     * @param timestamp
     * @param sequence
     * @return
     */
    private long combine(long timestamp,long sequence){
        long id = ((timestamp - pypepoch) << (sequenceBits)) | sequence;
        return id;
    }

    /**
     * 循环等待下一秒
     * @param lastts
     * @return
     */
    private long tilNextSeconds(long lastts){
        long timestamp = currentTimeMintue();
        //循环等待,知道进入下一秒
        while (timestamp <= lastts) {
            timestamp = currentTimeMintue();
        }
        return timestamp;
    }

    /**
     * 当前的分钟
     * @return
     */
    private long currentTimeMintue(){
        return System.currentTimeMillis()/1000;
    }

    public static IdGenerator getInstance(){
        return UserIdGeneratorHolder.userIdGenerator;
    }

    public static void main(String[] args) {
        try {
                System.out.println(IdGenerator.getInstance().generateId());
        } catch (ServerBizException e) {
            e.printStackTrace();
        }
    }
}


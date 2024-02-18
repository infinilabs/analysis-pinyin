package org.nlpcn.commons.lang.dat;

import java.io.Serializable;

/**
 * 如果你的dat需要.有参数需要继承并且重写这个类的构造方法
 * 
 * @author ansj
 * 
 */
public abstract class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected byte   status;
    protected int    base = 65536;
    protected int    index;
    protected int    check;

    /**
     * 从词典中加载如果又特殊需求可重写此构造方法
     * 
     * @param split split
     */
    public abstract void init(String[] split);

    /**
     * 从生成的词典中加载。应该和toText方法对应
     * 
     * @param split split
     */
    public abstract void initValue(String[] split);

    /**
     * @return 以文本格式序列化的显示
     */
    public abstract String toText();

    @Override
    public String toString() {
        return this.toText();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

}

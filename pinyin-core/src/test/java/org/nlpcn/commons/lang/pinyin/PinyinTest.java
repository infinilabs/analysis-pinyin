package org.nlpcn.commons.lang.pinyin;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PinyinTest {

	String str = "正品行货 正品行货 码完代码，他起身关上电脑，用滚烫的开水为自己泡制一碗腾着热气的老坛酸菜面。中国的程序员更偏爱拉上窗帘，在黑暗中享受这独特的美食。这是现代工业给一天辛苦劳作的人最好的馈赠。南方一带生长的程序员虽然在京城多年，但仍口味清淡，他们往往不加料包，由脸颊自然淌下的热泪补充恰当的盐分。他们相信，用这种方式，能够抹平思考着现在是不是过去想要的未来而带来的大部分忧伤…小李的父亲在年轻的时候也是从爷爷手里接收了祖传的代码，不过令人惊讶的是，到了小李这一代，很多东西都遗失了，但是程序员苦逼的味道保存的是如此的完整。 就在24小时之前，最新的需求从PM处传来，为了得到这份自然的馈赠，码农们开机、写码、调试、重构，四季轮回的等待换来这难得的丰收时刻。码农知道，需求的保鲜期只有短短的两天，码农们要以最快的速度对代码进行精致的加工，任何一个需求都可能在24小时之后失去原本的活力，变成一文不值的垃圾创意。";

	// String str = "點下面繁體字按鈕進行在線轉換" ;
	

	/**
	 * 動態加入拼音
	 */
    @Test
    public void testInsertPinyin(){
        List<String> result1 = (Pinyin.tonePinyin(str));
        System.out.println("result1:"+result1);
        Pinyin.insertPinyin("行货", new String[]{"hang2","huo4"});
        List<String> result2 = (Pinyin.tonePinyin(str));
        System.out.println("result2:"+result2);
        Assert.assertNotSame(result1.get(2), result2.get(2));
        
    }
    
    /**
     * list 转换为String
     */
    @Test
    public void testList2String(){
        List<String> list = Pinyin.unicodePinyin(str);
        
        System.out.println(list);
        
        System.out.println(Pinyin.list2String(list));
        
        System.out.println(Pinyin.list2StringSkipNull(list));
    }


	@Test
	public void testStr2Pinyin() {
		List<String> parseStr = Pinyin.unicodePinyin(str);
		System.out.println(parseStr);
		Assert.assertEquals(parseStr.size(), str.length());
	}

	/**
	 * 拼音返回
	 * 
	 * @param str
	 * @return ['zhong3','guo4']
	 */
	@Test
	public void testPinyinStr() {
		List<String> result = Pinyin.pinyin(str);
		System.out.println(result);
		Assert.assertEquals(result.size(), str.length());

	}

	/**
	 * 取得每个字的拼音,不要声调
	 * 
	 * @return
	 */
	@Test
	public void testPinyinWithoutTone() {
		List<String> result = Pinyin.pinyin(str);
		System.out.println(result);
		Assert.assertEquals(result.size(), str.length());
	}

	/**
	 * 取得每个字的首字符
	 * 
	 * @param str
	 * @return
	 */
	@Test
	public void testStr2FirstCharArr() {
	    List<String> result = Pinyin.firstChar(str);
		System.out.println(result);
		Assert.assertEquals(result.size(), str.length());
	}
	
}

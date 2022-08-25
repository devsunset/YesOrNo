/*
 * @(#)DataVo.java
 * Date : 2019. 3. 31.
 * Copyright: (C) 2019 by devsunset All right reserved.
 */
package devsunset.app.yesorno.modules.httpservice;

/**
 * <PRE>
 * YesOrNo DataVo
 * </PRE>
 *
 * @author devsunset
 * @version 1.0
 * @since SimpleRandomChat 1.0
 */
public class DataVo {

    private final String answer;
    private final String forced;
    private final String image;

    /**
     * DataVo
     *
     * @param answer
     * @param forced
     * @param image
     */
    public DataVo(String answer, String forced, String image) {
        this.answer = answer;
        this.forced = forced;
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public String getForced() {
        return forced;
    }

    public String getImage() {
        return image;
    }
}



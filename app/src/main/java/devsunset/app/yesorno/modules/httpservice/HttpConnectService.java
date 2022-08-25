/*
 * @(#)HttpConnectService.java
 * Date : 2019. 3. 31.
 * Copyright: (C) 2019 by devsunset All right reserved.
 */
package devsunset.app.yesorno.modules.httpservice;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * <PRE>
 * SimpleRandomChat HttpConnectService
 * </PRE>
 *
 * @author devsunset
 * @version 1.0
 * @since SimpleRandomChat 1.0
 */
public interface HttpConnectService {

    String URL = "https://yesno.wtf/";

    @GET("api")
    Call<devsunset.app.yesorno.modules.httpservice.DataVo> api();
}
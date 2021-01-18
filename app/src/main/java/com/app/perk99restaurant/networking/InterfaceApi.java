package com.app.perk99restaurant.networking;



import java.util.HashMap;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface InterfaceApi
{

    // http://192.168.1.21:7000/
    // String BASE_URL = "http://192.168.1.21:7000/mobile/api/";

    // String BASE_URL = "http://165.22.215.99:7000/mobile/api/";

    String BASE_URL = "http://178.128.116.149/food_app/public/api/";


    @FormUrlEncoded
    @POST("merchant_register")
    Observable<ResponseBody> register(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("merchant_login")
    Observable<ResponseBody> login(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("faqs")
    Observable<ResponseBody> FAQ(@FieldMap HashMap<String, String> param);


    /*provider
    *
old_password
* new_password
*/


    @FormUrlEncoded
    @POST("change_password")
    Observable<ResponseBody> changepassword(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("merchant_forgot_password")
    Observable<ResponseBody> forgetPassword(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("merchant_verifyOtp")
    Observable<ResponseBody> verifyOTP(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("merchant_update_password")
    Observable<ResponseBody> resetPassword(@FieldMap HashMap<String, String> param);

    @GET("rest_orders")
    Observable<ResponseBody> getOrders(@Query("provider") String provider);

    @GET("get_rest_rating")
    Observable<ResponseBody> getReviews(@Query("provider") String provider);

    @GET("accepted_orders")
    Observable<ResponseBody> getAcceptedOrders(@Query("provider") String provider);

    @GET("get_merchant")
    Observable<ResponseBody> getMerchantProfile(@Query("provider") String provider);

    @GET("get_menus")
    Observable<ResponseBody> getMenu(@Query("provider") String provider);

    @GET("get_dicount")
    Observable<ResponseBody> getPerks(@Query("provider") String provider);

    @GET("get_nearby_drivers")
    Observable<ResponseBody> getNearByDrivers(@Query("provider") String provider);


    @FormUrlEncoded
    @POST("accept_order")
    Observable<ResponseBody> accept_order(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("reject_order")
    Observable<ResponseBody> reject_order(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("add_rest_review")
    Observable<ResponseBody> add_review(@FieldMap HashMap<String, String> param,@Query("provider") String provider);

    @FormUrlEncoded
    @POST("store_withdraw_request")
    Observable<ResponseBody> sendEarning(@FieldMap HashMap<String, String> param,@Query("provider") String provider);



    @FormUrlEncoded
    @POST("assign_job_to_driver")
    Observable<ResponseBody> assign_driver(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_merchant_earning")
    Observable<ResponseBody> getEarning(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("filter_order")
    Observable<ResponseBody> getActualEarning(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("set_dicount")
    Observable<ResponseBody> savePerks(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("ready_order")
    Observable<ResponseBody> orderReady(@FieldMap HashMap<String, String> param);
    //order_id

    //order_id
    @FormUrlEncoded
    @POST("picked_up_order")
    Observable<ResponseBody> orderPickedUpbyCustomer(@FieldMap HashMap<String, String> param);




}

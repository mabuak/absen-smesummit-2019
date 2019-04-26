package id.smesummit.smeabsen.Services;

import id.smesummit.smeabsen.Responses.AttendanceResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AttendaceService {
    @FormUrlEncoded
    @POST("attendance.php")
    Call<AttendanceResponse> attendance(@Field("ticket_code") String barcode);
}
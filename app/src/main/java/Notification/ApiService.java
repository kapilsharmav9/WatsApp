package Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers(
           {

            "Content-type:application/json",
            "Authorization:key=AAAAM0e2COw:APA91bHymzFz1OL9G2qBX0H9g2ltuOBDMFJOPJfpAZrb5rWhSs_NxFg9ufXjGip1Yd-FmlKqb3PzdKi809m8qPFHBbSvjmgMw9CO6hSHz-CCDcb-_VM2qeEiULS4exAmddE7GOpv1imX"
}

    )
    @POST("*fcm/send")
    Call<MyResponse> sendNotificatio(@Body Sender body);
}

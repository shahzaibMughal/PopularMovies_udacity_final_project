package network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static network.URLs.MOVIES_BASE_URL;


public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient()
    {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MOVIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

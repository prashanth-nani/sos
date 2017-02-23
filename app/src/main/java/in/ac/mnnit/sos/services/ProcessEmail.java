package in.ac.mnnit.sos.services;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashanth on 21/2/17.
 */

public class ProcessEmail extends StringRequest {

    private Map<String, String> params;
    private String email;

    public ProcessEmail(String email, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.email = email;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("email", email);
        return params;
    }
}

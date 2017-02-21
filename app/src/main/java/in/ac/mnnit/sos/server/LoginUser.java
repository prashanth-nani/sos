package in.ac.mnnit.sos.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import in.ac.mnnit.sos.models.Credential;

/**
 * Created by prashanth on 21/2/17.
 */

public class LoginUser extends StringRequest {

    Map<String, String> params;
    Credential cred;

    public LoginUser(Credential cred, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.cred = cred;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("email", cred.getUsername());
        params.put("password", cred.getPassword());
        return params;
    }
}

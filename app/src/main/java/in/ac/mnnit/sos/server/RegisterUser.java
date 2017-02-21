package in.ac.mnnit.sos.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import in.ac.mnnit.sos.models.User;

/**
 * Created by prashanth on 21/2/17.
 */

public class RegisterUser extends StringRequest {

    Map<String, String> params;
    User user;

    public RegisterUser(User user, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.user = user;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("name", user.getName());
        params.put("phone", user.getPhone());
        params.put("gender", user.getGender());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        return params;
    }
}

package org.smartregister.ssl;

import android.content.Context;
import android.util.Log;

import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.smartregister.DristhiConfiguration;
import org.smartregister.R;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLException;

/**
 * Created by onaio on 01/09/2017.
 */

public class OpensrpSSLHelper {
    private Context context;
    private DristhiConfiguration configuration;
    private String TAG = OpensrpSSLHelper.class.getCanonicalName();

    public OpensrpSSLHelper(Context context_, DristhiConfiguration configuration_) {
        this.context = context_;
        this.configuration = configuration_;
    }

    public SocketFactory getSslSocketFactoryWithOpenSrpCertificate() {
        try {

            KeyStore trustedKeystore = KeyStore.getInstance("BKS");
            InputStream inputStream = context.getResources().openRawResource(R.raw.opensrp_zeir_truststore);
            try {
                trustedKeystore.load(inputStream, "phone red pen".toCharArray());
            } finally {
                inputStream.close();
            }

            SSLSocketFactory socketFactory = new SSLSocketFactory(trustedKeystore);
            final X509HostnameVerifier oldVerifier = socketFactory.getHostnameVerifier();
            socketFactory.setHostnameVerifier(new AbstractVerifier() {
                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws
                        SSLException {
                    for (String cn : cns) {
                        if (!configuration.shouldVerifyCertificate() || host.equals(cn)) {
                            return;
                        }
                    }
                    oldVerifier.verify(host, cns, subjectAlts);
                }
            });
            return socketFactory;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() != null ? e.getMessage() : "");
            throw new AssertionError(e);
        }
    }

}
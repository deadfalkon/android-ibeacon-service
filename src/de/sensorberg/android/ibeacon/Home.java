package de.sensorberg.android.ibeacon;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.MonitorNotifier;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import java.util.Collection;

public class Home extends Activity implements IBeaconConsumer {

    protected static final String TAG = "RangingActivity";
    private TextView logTextView;
    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logTextView = (TextView) findViewById(R.id.logTextView);

        iBeaconManager.bind(this);
        appendLogText("Welcome");
        appendLogText("hello");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iBeaconManager.unBind(this);
    }

    @Override
    public void onIBeaconServiceConnect() {
        iBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                if (iBeacons.size() > 0){
                    IBeacon next = iBeacons.iterator().next();
                    appendLogText("found i Beacon:" + region.getProximityUuid() + " major:" + next.getMajor() + "minor:" + next.getMinor());
                }
            }
        });
        iBeaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                appendLogText("I just saw an iBeacon for the first time! " + region.getProximityUuid() + " major:" + region.getMajor() + "minor:" + region.getMinor());
            }

            @Override
            public void didExitRegion(Region region) {
                appendLogText("I no longer see an iBeacon " + region.getProximityUuid() + " major:" + region.getMajor() + "minor:" + region.getMinor());
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                appendLogText("I have just switched from seeing/not seeing iBeacons: " + state);
            }
        });
        try {
            iBeaconManager.startRangingBeaconsInRegion(new Region("region1", "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, null));
            iBeaconManager.startRangingBeaconsInRegion(new Region("region2", "5A4BCFCE-174E-4BAC-A814-092E77F6B7E5", null, null));
            iBeaconManager.startRangingBeaconsInRegion(new Region("region3", "74278BDA-B644-4520-8F0C-720EAF059935", null, null));
            iBeaconManager.startRangingBeaconsInRegion(new Region("region4", "D57092AC-DFAA-446C-8EF3-C81AA22815B5", null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void appendLogText(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, string);
                logTextView.setVerticalScrollbarPosition(0);
                logTextView.setText(string+"\n"+logTextView.getText());
            }
        });
    }
}

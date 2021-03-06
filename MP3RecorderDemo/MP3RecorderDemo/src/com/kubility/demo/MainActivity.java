
package com.kubility.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    private MP3Recorder recorder = new MP3Recorder(Environment.getExternalStorageDirectory()
            + "/Recorder.mp3", 8000);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView statusTextView = (TextView)findViewById(R.id.StatusTextView);

        recorder.setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MP3Recorder.MSG_REC_STARTED:
                    case MP3Recorder.MSG_REC_RESTORE:
                        statusTextView.setText("开始录音");
                        break;
                    case MP3Recorder.MSG_REC_STOPPED:
                        statusTextView.setText("结束录音");
                        break;
                    case MP3Recorder.MSG_REC_PAUSE:
                        statusTextView.setText("暂停录音");
                        break;
                }
            }
        });

        Button startButton = (Button)findViewById(R.id.StartButton);
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.start();
            }
        });
        Button stopButton = (Button)findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stop();
            }
        });

        Button pauseButton = (Button)findViewById(R.id.PauseButton);
        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    if (recorder.isPaus()) {
                        recorder.restore();
                    } else {
                        recorder.pause();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.stop();
    }
}

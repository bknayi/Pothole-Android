package com.potholedetection;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.potholedetection.eventnotification.EventNotifier;
import com.potholedetection.eventnotification.EventStates;
import com.potholedetection.eventnotification.EventTypes;
import com.potholedetection.eventnotification.IEventListener;
import com.potholedetection.eventnotification.ListenerPriority;
import com.potholedetection.eventnotification.NotifierFactory;
import com.potholedetection.locator.LocationMonitor;
import com.potholedetection.sensor.AccelerationMonitor;


/**
 * This class includes the business logic of application.
 */
public class Core implements IEventListener {
	private static Core _instance;

	private long _lastComplaintSentTimestamp;

	private final long POTHOLE_DETECTION_TIMEOUT = 2 * 1000;

	Activity con;

	/**
	 * Private constructor.
	 */
	private Core() {
	}

	/**
	 * Method to get the initialized instance of {@link Core}.
	 * 
	 * @return the initialized instance of {@link Core}.
	 */
	public static Core getInstance() {
		if (_instance == null) {
			_instance = new Core();
		}
		return _instance;
	}

	public void init(Activity c) {
		con = c;
		registerListener();
		LocationMonitor.getInstance().start();
		AccelerationMonitor.getInstance().start();
		_lastComplaintSentTimestamp = System.currentTimeMillis();
	}

	public void stop() {
		LocationMonitor.getInstance().stop();
		AccelerationMonitor.getInstance().stop();
	}

	@Override
	public void registerListener() {
		EventNotifier notifier = NotifierFactory.getInstance().getNotifier(NotifierFactory.ACCELEROMETER_EVENT_NOTIFIER);
		notifier.registerListener(Core.this, ListenerPriority.HIGH);
	}

	@Override
	public void unregisterListener() {
		EventNotifier notifier = NotifierFactory.getInstance().getNotifier(NotifierFactory.ACCELEROMETER_EVENT_NOTIFIER);
		notifier.unregisterListener(Core.this);
	}

	@Override
	public int eventNotify(int eventType, Object eventObject) {
		switch (eventType) {
		case EventTypes.ACCELEROMETR_VALUES_CHANGED:
			long currentTimestamp = System.currentTimeMillis();
			if (currentTimestamp - _lastComplaintSentTimestamp > POTHOLE_DETECTION_TIMEOUT) {
				Log.d(MainApplication.LOG_TAG, "Core: eventNotify: Sending complaint");
				_lastComplaintSentTimestamp = currentTimestamp;
				Location location = LocationMonitor.getInstance().getCurrentLocation();
				if (location != null) {
					double latitude = location.getLatitude();
					double longitude = location.getLongitude();
//					Complaint complaint = new Complaint("Pothole", "Pothole detected by sensor", "This is automatically detected pothole", latitude, longitude);
//					JurkComplaint makeComplaint = new JurkComplaint(complaint);
//					makeComplaint.send();
					Log.d("ssssseeeennnsssor",latitude+"");
					AutoComplain a = new AutoComplain();
					a.makeRequest(latitude,longitude,con);
				}else{
					Log.d("ssssseeeennnsssor","no location");
				}
			}
			break;
		}
		return EventStates.PROCESSED;
	}
}

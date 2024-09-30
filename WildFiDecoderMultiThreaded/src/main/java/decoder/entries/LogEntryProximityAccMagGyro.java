package decoder.entries;

import Logging.Log;
import decoder.LogEntryManager;
import decoder.imu.IMUDecoder;
import decoder.imu.IMUEntry;
import decoder.imu.IMUSettings;
import decoder.prox.ProxEntry;

import java.util.ArrayList;

import static java.lang.Math.toIntExact;

public class LogEntryProximityAccMagGyro extends LogEntry {
    public long proxLen, fifoLen;
    public ArrayList<ProxEntry> proxEntries = new ArrayList<>();
    public ArrayList<IMUEntry> imuEntries = new ArrayList<>();
		String name;

    public int minLength = INDEX(11);
    public LogEntryProximityAccMagGyro() {
        prefix = "123465";
    }

    public void decode(String name, String dataset, boolean debug, IMUSettings imuSettings, int imuFrequency) {
        if(dataset.length() < minLength) {
            if (debug) Log.d("decoder-plausibility", "length not plausible " + dataset.length());
            plausibilityCheckOkay = false;
            return;
        }

				this.name = name;
        this.imuFrequency = imuFrequency;

        utcTimestamp = Long.parseLong(dataset.substring(INDEX(3), INDEX(7)), 16);
        proxLen = Long.parseLong(dataset.substring(INDEX(7), INDEX(9)), 16);
        fifoLen = Long.parseLong(dataset.substring(INDEX(9), INDEX(11)), 16);

        if (!LogEntryManager.timestampPlausible(utcTimestamp, debug)) plausibilityCheckOkay = false;
        if (!LogEntryManager.proxLenPlausible(proxLen, debug)) plausibilityCheckOkay = false;
        if (!LogEntryManager.fifoLenPlausible(fifoLen, debug)) plausibilityCheckOkay = false;
        if (!LogEntryManager.utcProximityTimePlausible(utcTimestamp, debug)) plausibilityCheckOkay = false;

        // PROX DATA
        String proxDataExtracted = "";
        try {
            proxDataExtracted = dataset.substring(minLength, minLength + INDEX(toIntExact(proxLen)));
        }
        catch(Exception e) {
            plausibilityCheckOkay = false;
            if (debug) Log.d("decoder-plausibility", "prox length does not match string length (partially transmitted fifo, maybe last received message)");
        }
        if(plausibilityCheckOkay) {
            ProxEntry.createProxData(proxDataExtracted, utcTimestamp, proxEntries);
        }
        if (!LogEntryManager.proximityRSSIsMakeSense(proxEntries, debug)) plausibilityCheckOkay = false;

        // FIFO DATA
        String fifoDataExtracted = "";
        try {
            fifoDataExtracted = dataset.substring(minLength + INDEX(toIntExact(proxLen)), minLength + INDEX(toIntExact(proxLen)) + INDEX(toIntExact(fifoLen)));
        }
        catch(Exception e) {
            plausibilityCheckOkay = false;
            if (debug) Log.d("decoder-plausibility", "fifo length does not match string length (partially transmitted fifo, maybe last received message)");
        }

        if(plausibilityCheckOkay) {
            IMUDecoder.createIMUData(true, true, true, fifoDataExtracted, imuEntries, imuSettings.accConversionFactor, imuSettings.gyroConversionFactor, imuSettings.magConversionFactor);
        }

        entryLengthInBytes = toIntExact((minLength / 2) + fifoLen + proxLen);
    }

    public String headlineHeader() {
        return "id," +
                "prefixDataType," +
								"tagId," +
                "utcTimestamp," +
                "utcDate," +
                "proxLen," +
                "fifoLen";
    }

    public String serializeHeader() {
        return dataMessageCustomPrefix + ","
                + prefix + ","
								+ name + ","
                + utcTimestamp + ","
                + LogEntryManager.utcTimestampToStringWithoutWeekday(utcTimestamp) + ","
                + proxLen + ","
                + fifoLen;
    }

    public int getVarDataLength() {
        return proxEntries.size() + imuEntries.size();
    }

	public String headlineHeaderAndVarData(boolean useBurstForm) {
		if (useBurstForm)
			return headlineHeader() + "," + ProxEntry.serializeHeadline(true) + "," + IMUEntry.serializeHeadline(true, true, true, true);
		else
			return headlineHeader() + "," + ProxEntry.serializeHeadline(false) + "\n" + IMUEntry.serializeHeadline(true, true, true, false);
	}

	public String serializeHeaderAndVarData(boolean useBurstForm) {
		String returnVal = "";
		if (useBurstForm) {
			returnVal += serializeHeader() + ",";
			if (proxEntries.size() > 0) {
				for (int i = 0; i < proxEntries.size(); i++)
					returnVal += proxEntries.get(i).serializeId() + (i == proxEntries.size() - 1 ? "," : " ");
				for (int i = 0; i < proxEntries.size(); i++)
					returnVal += proxEntries.get(i).serializeRssi() + (i == proxEntries.size() - 1 ? "," : " ");
			} else
				returnVal += ProxEntry.serializeNobodySeen() + ",";
			if(imuEntries.size() > 0) {
                for (int i = 0; i < imuEntries.size(); i++)
                    returnVal += imuEntries.get(i).serializeConsecutiveNumber() + (i == imuEntries.size() - 1 ? "," : " ");
                for (int i = 0; i < imuEntries.size(); i++)
                    returnVal += imuEntries.get(i).serializeAccelerometerData() + (i == imuEntries.size() - 1 ? "," : " ");
                for (int i = 0; i < imuEntries.size(); i++)
                    returnVal += imuEntries.get(i).serializeMagnetometerData() + (i == imuEntries.size() - 1 ? "," : " ");
                for (int i = 0; i < imuEntries.size(); i++)
                    returnVal += imuEntries.get(i).serializeHallSensorData() + (i == imuEntries.size() - 1 ? "," : " ");
                for (int i = 0; i < imuEntries.size(); i++)
                    returnVal += imuEntries.get(i).serializeGyroscopeData() + (i == imuEntries.size() - 1 ? "," : " ");
                returnVal += imuEntries.get(0).serializeConversionFactor() + ",";
            }
            else {
                returnVal += ",,,";
            }
            returnVal += imuFrequency + ",XYZ,";
		} else {
			for (IMUEntry a : imuEntries)
				returnVal += a.serialize() + "\n";
			if (proxEntries.size() == 0)
				returnVal += serializeHeader() + "," + ProxEntry.serializeNobodySeen() + "\n";
			for (ProxEntry p : proxEntries)
				returnVal += serializeHeader() + "," + p.serialize() + "\n";
		}
		if (returnVal.length() > 0) returnVal = returnVal.substring(0, returnVal.length() - 1);
		return returnVal;
	}

    public LogEntry copyMe(String dataMessageCustomPrefixIn) {
        LogEntryProximityAccMagGyro e = new LogEntryProximityAccMagGyro();
        e.dataMessageCustomPrefix = dataMessageCustomPrefixIn;
        return e;
    }
}

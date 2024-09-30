package decoder;

import decoder.imu.IMUSettings;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class DecodingManager
{
    public  EspNowDecoderMultiThreading espNowDecoderMultiThreading = new EspNowDecoderMultiThreading();
    public ArrayList<EspNowMessage> espNowMessagesAll = new ArrayList<>();

    public boolean ConvertBytesToMessages(byte[] fileData, ArrayList<String> macs, boolean debug, IMUSettings imuSettings)
    {
        return espNowDecoderMultiThreading.stepOneBytesToEspNowMessages(fileData, macs, espNowMessagesAll, debug, imuSettings);
    }

    public void DecodeDataInSeperateThread(final String selectedTagName, final String filename, final IMUSettings imuSettings, boolean burstForm, boolean isMoveData, int imuFreq, CountDownLatch countDownLatch)
     {
        ArrayList<EspNowMessage> espNowMessagesSingleTag = espNowDecoderMultiThreading.stepTwoReduceDataToOneTag(selectedTagName, espNowMessagesAll);

        EspNowDecoderMultiThreading decoder = new EspNowDecoderMultiThreading(espNowMessagesSingleTag, selectedTagName,filename, imuSettings, burstForm, isMoveData, imuFreq, countDownLatch);
        decoder.start();
    }
}
package com.wjs.util;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;

public class MusicUtil {

    public static String  getMp3TrackLength(File mp3File) {
        try {
            MP3File f = (MP3File) AudioFileIO.read(mp3File);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
            int seconds = audioHeader.getTrackLength();
            int min=seconds/60;
            int sec=seconds%60;
            String time=min+":"+sec;
            return time;
        } catch(Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}

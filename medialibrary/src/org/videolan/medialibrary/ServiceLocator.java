package org.videolan.medialibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;

import org.videolan.libvlc.Media;
import org.videolan.medialibrary.interfaces.media.AMediaWrapper;
import org.videolan.medialibrary.media.MediaWrapper;
import org.videolan.medialibrary.stubs.StubMediaWrapper;

public class ServiceLocator {

    private static ServiceLocator mServiceLocator;
    private static LocatorMode mMode = LocatorMode.VLC_ANDROID;

    public static void setLocatorMode(LocatorMode mode) {
        ServiceLocator.mMode = mode;
    }

    enum LocatorMode {
        VLC_ANDROID,
        TESTS,
    }

    public static ServiceLocator getInstance() {
        return mServiceLocator;
    }

    // AMediaWrapper
    public static AMediaWrapper getAMediaWrapper(long id, String mrl, long time, long length,
                                                 int type, String title, String filename,
                                                 String artist, String genre, String album,
                                                 String albumArtist, int width, int height,
                                                 String artworkURL, int audio, int spu,
                                                 int trackNumber, int discNumber, long lastModified,
                                                 long seen, boolean isThumbnailGenerated) {
        if (mMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapper(id, mrl, time, length, type, title,
                    filename, artist, genre, album, albumArtist, width, height, artworkURL,
                    audio, spu, trackNumber, discNumber, lastModified, seen, isThumbnailGenerated);
        } else {
            return new StubMediaWrapper(id, mrl, time, length, type, title,
                    filename, artist, genre, album, albumArtist, width, height, artworkURL,
                    audio, spu, trackNumber, discNumber, lastModified, seen, isThumbnailGenerated);
        }
    }

    public static AMediaWrapper getAMediaWrapper(Uri uri, long time, long length, int type,
                                                 Bitmap picture, String title, String artist,
                                                 String genre, String album, String albumArtist,
                                                 int width, int height, String artworkURL,
                                                 int audio, int spu, int trackNumber,
                                                 int discNumber, long lastModified, long seen) {
        if (mMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapper(uri, time, length, type, picture, title, artist, genre,
                    album, albumArtist, width, height, artworkURL, audio, spu, trackNumber,
                    discNumber, lastModified, seen);
        } else {
            return new StubMediaWrapper(uri, time, length, type, picture, title, artist, genre,
                    album, albumArtist, width, height, artworkURL, audio, spu, trackNumber,
                    discNumber, lastModified, seen);
        }
    }

    public static AMediaWrapper getAMediaWrapper(Uri uri) {
        if (mMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapper(uri);
        } else {
            return new StubMediaWrapper(uri);
        }
    }

    public static AMediaWrapper getAMediaWrapper(Media media) {
        if (mMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapper(media);
        } else {
            return new StubMediaWrapper(media);
        }
    }

    public static AMediaWrapper getAMediaWrapper(Parcel in) {
        if (mMode == LocatorMode.VLC_ANDROID) {
            return new MediaWrapper(in);
        } else {
            return new StubMediaWrapper(in);
        }
    }
}
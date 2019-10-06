/*
 * Copyright 2019 Kaushik N. Sanji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kaushiknsanji.rhythm.data.local.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable Model class for storing the details of a Song Track
 *
 * @author Kaushik N Sanji
 */
public class SongTrack implements Parcelable {
    /**
     * Implementation of {@link android.os.Parcelable.Creator} interface
     * to generate instances of this Parcelable class {@link SongTrack} from a {@link Parcel}
     */
    public static final Creator<SongTrack> CREATOR = new Creator<SongTrack>() {
        /**
         * Creates an instance of this Parcelable class {@link SongTrack} from
         * a given Parcel whose data had been previously written by #writeToParcel() method
         *
         * @param in The Parcel to read the object's data from.
         * @return Returns a new instance of this Parcelable class {@link SongTrack}
         */
        @Override
        public SongTrack createFromParcel(Parcel in) {
            return new SongTrack(in);
        }

        /**
         * Creates a new array of this Parcelable class {@link SongTrack}
         *
         * @param size Size of the array
         * @return Returns an array of this Parcelable class {@link SongTrack}, with every
         * entry initialized to null
         */
        @Override
        public SongTrack[] newArray(int size) {
            return new SongTrack[size];
        }
    };
    //The Track Title of the Song
    private final String mSongTitle;
    //The Album Title of the Song
    private final String mAlbumTitle;
    //Names of Artists of the Song
    private final String mArtists;

    /**
     * Private Constructor of {@link SongTrack}
     *
     * @param songTitle  The Track Title of the Song
     * @param albumTitle The Album Title of the Song
     * @param artists    Names of Artists of the Song
     */
    private SongTrack(String songTitle, String albumTitle, String artists) {
        mSongTitle = songTitle;
        mAlbumTitle = albumTitle;
        mArtists = artists;
    }

    /**
     * Parcelable constructor that de-serializes the data from a Parcel {@code in} passed
     *
     * @param in The Instance of the Parcel class containing the serialized data
     */
    protected SongTrack(Parcel in) {
        mSongTitle = in.readString();
        mAlbumTitle = in.readString();
        mArtists = in.readString();
    }

    /**
     * Flattens/Serializes the object of {@link SongTrack} into a Parcel
     *
     * @param dest  The Parcel in which the object should be written
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSongTitle);
        dest.writeString(mAlbumTitle);
        dest.writeString(mArtists);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Getter Method for the Track Title of the Song
     *
     * @return The Track Title of the Song
     */
    public String getSongTitle() {
        return mSongTitle;
    }

    /**
     * Getter Method for the Album Title of the Song
     *
     * @return The Album Title of the Song
     */
    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    /**
     * Getter Method for the Names of Artists of the Song
     *
     * @return Names of Artists of the Song
     */
    public String getArtists() {
        return mArtists;
    }

    /**
     * Static Builder class that constructs {@link SongTrack}
     */
    public static class Builder {

        private String mSongTitle;
        private String mAlbumTitle;
        private String mArtists;

        /**
         * Setter for the Track Title of the Song
         *
         * @param songTitle The Track Title of the Song
         * @return Instance of {@link Builder} for chaining method calls.
         */
        public Builder setSongTitle(String songTitle) {
            mSongTitle = songTitle;
            return this;
        }

        /**
         * Setter for the Album Title of the Song
         *
         * @param albumTitle The Album Title of the Song
         * @return Instance of {@link Builder} for chaining method calls.
         */
        public Builder setAlbumTitle(String albumTitle) {
            mAlbumTitle = albumTitle;
            return this;
        }

        /**
         * Setter for the Names of Artists of the Song
         *
         * @param artists The Names of Artists of the Song
         * @return Instance of {@link Builder} for chaining method calls.
         */
        public Builder setArtists(String artists) {
            mArtists = artists;
            return this;
        }

        /**
         * Terminal Method that constructs the {@link SongTrack}
         *
         * @return Instance of {@link SongTrack} built
         */
        public SongTrack createSongTrack() {
            return new SongTrack(mSongTitle, mAlbumTitle, mArtists);
        }
    }
}

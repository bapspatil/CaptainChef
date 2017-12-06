package bapspatil.captainchef.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {
    private int stepId;
    private String shortInfo, info, videoUrl, thumbnailUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepId);
        dest.writeString(this.shortInfo);
        dest.writeString(this.info);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }

    public RecipeStep() {
    }

    protected RecipeStep(Parcel in) {
        this.stepId = in.readInt();
        this.shortInfo = in.readString();
        this.info = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };
}

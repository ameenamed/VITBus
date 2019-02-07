package com.example.android.vitbus1;

public class CloudData {

        public Double latitude;
        public Double longitude;

        public CloudData()
        {

        }

        public CloudData(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }


}

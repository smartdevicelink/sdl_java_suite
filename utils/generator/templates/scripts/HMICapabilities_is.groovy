
     public boolean isNavigationAvailable(){
         Object available = getValue(KEY_NAVIGATION);
         if(available == null){
             return false;
         }
         return (Boolean)available;
     }

     public boolean isPhoneCallAvailable(){
         Object available = getValue(KEY_PHONE_CALL);
         if(available == null){
             return false;
         }
         return (Boolean)available;
     }

    public boolean isVideoStreamingAvailable(){
        Object available = getValue(KEY_VIDEO_STREAMING);
        if(available == null){
            return false;
        }
        return (Boolean)available;
    }

    public boolean isRemoteControlAvailable(){
        Object available = getValue(KEY_REMOTE_CONTROL);
        if(available == null){
            return false;
        }
        return (Boolean)available;
    }

    public boolean isAppServicesAvailable(){
        Object available = getValue(KEY_APP_SERVICES);
        if(available == null){
            return false;
        }
        return (Boolean)available;
    }

    public boolean isDisplaysCapabilityAvailable(){
        Object available = getValue(KEY_DISPLAYS);
        if(available == null){
            return false;
        }
        return (Boolean)available;
    }

    public boolean isSeatLocationAvailable(){
        Object available = getValue(KEY_SEAT_LOCATION);
        if(available == null){
            return false;
        }
        return (Boolean)available;
    }

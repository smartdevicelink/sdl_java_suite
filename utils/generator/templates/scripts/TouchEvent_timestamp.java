
    @SuppressWarnings("unchecked")
    public List<Long> getTimestamps() {
    	if(getValue(KEY_TS) instanceof List<?>){
    		List<?> list = (List<?>) getValue(KEY_TS);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){ //Backwards case
        			int size = list.size();
        			List<Integer> listOfInt = (List<Integer>) list;
        			List<Long> listofLongs = new ArrayList<Long>(size);
        			for(int i = 0; i<size;i++){
        				listofLongs.add(listOfInt.get(i).longValue());
        			}
        			return listofLongs;
        		}else if(obj instanceof Long){
        			return (List<Long>) list;
        		}
        	}
    	}
        return null;
    }

    public void setTimestamps(@NonNull List<Long> ts){
        setValue(KEY_TS, ts);
    }

    @SuppressWarnings("unchecked")
    public List<TouchCoord> getTouchCoordinates() {
        return (List<TouchCoord>) getObject(TouchCoord.class, KEY_C);
    }

    public void setTouchCoordinates(@NonNull List<TouchCoord> c ) {
        setValue(KEY_C, c);
    }
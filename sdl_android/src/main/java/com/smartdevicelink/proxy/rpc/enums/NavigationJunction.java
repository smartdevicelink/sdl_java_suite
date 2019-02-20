package com.smartdevicelink.proxy.rpc.enums;

public enum NavigationJunction {

	/**
	 * A junction that represents a standard intersection with a single road crossing another.
	 */
	REGULAR,

	/**
	 * A junction where the road splits off into two paths; a fork in the road.
	 */
	BIFURCATION,

	/**
	 * A junction that has multiple intersections and paths.
	 */
	MULTI_CARRIAGEWAY,

	/**
	 * A junction where traffic moves in a single direction around a central, non-traversable point
	 * to reach one of the connecting roads.
	 */
	ROUNDABOUT,

	/**
	 * Similar to a roundabout, however the center of the roundabout is fully traversable. Also
	 * known as a mini-roundabout.
	 */
	TRAVESABLE_ROUNDABOUT,

	/**
	 * A junction where lefts diverge to the right, then curve to the left, converting a left turn
	 * to a crossing maneuver.
	 */
	JUGHANDLE,

	/**
	 * Multiple way intersection that allows traffic to flow based on priority; most commonly right
	 * of way and first in, first out.
	 */
	ALL_WAY_YIELD,

	/**
	 * A junction designated for traffic turn arounds.
	 */
	TURN_AROUND,

	;

	public static NavigationJunction valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}

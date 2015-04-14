package com.zifei.corebeau.common;

public abstract class AsyncCallBacks {

	/*
	 * For callback with one success parameter
	 */
	public static abstract class OneZero<S1> {

		public void onSuccess(S1 result) {
		}

		public void onError() {
		}
	}

	/*
	 * For callback with one error parameter
	 */
	public static abstract class ZeroOne<E1> {

		public void onSuccess() {
		}

		public void onError(E1 result) {
		}
	}
	
	/*
	 * For callback with one error parameter
	 */
	public static abstract class ZeroTwo<E1, E2> {

		public void onSuccess() {
		}

		public void onError(E1 result, E2 errorMsg) {
		}
	}

	/*
	 * For callback with one success parameter and one error parameter
	 */
	public static abstract class OneOne<S1, E1> {

		public void onSuccess(S1 result) {
		}

		public void onError(E1 result) {
		}
	}
	
	/*
	 * For callback with one success parameter and one error parameter
	 */
	public static abstract class OneTwo<S1, E1, E2> {

		public void onSuccess(S1 result) {
		}

		public void onError(E1 result, E2 errorMsg) {
		}
	}

	/*
	 * For callback with two success parameters and one error parameter
	 */
	public static abstract class TwoOne<S1, S2, E1> {

		public void onSuccess(S1 resultOne, S2 resultTwo) {
		}

		public void onError(E1 result) {
		}
	}

	/*
	 * For callback with two success parameters and two error parameters
	 */
	public static abstract class TwoTwo<S1, S2, E1, E2> {

		public void onSuccess(S1 resultOne, S2 resultTwo) {
		}

		public void onError(E1 resultOne, E2 resultTwo) {
		}
	}
	
	public static abstract class ThreeTwo<S1, S2, S3, E1, E2> {

		public void onSuccess(S1 resultOne, S2 resultTwo, S3 resultThree) {
		}

		public void onError(E1 resultOne, E2 resultTwo) {
		}
	}
}


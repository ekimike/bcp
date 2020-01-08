package com.bcp.challenge.controllerdto.response;

import java.math.BigDecimal;

import com.bcp.challenge.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseControllerResponse<T> {

	private ErrorCode errorCode;
	private T data;
	
	public static BaseControllerResponse<?> successNoData() {
		return BaseControllerResponse.builder().build();
	}
	
	public static<T> BaseControllerResponse<T> successWithData(T data) {
		return BaseControllerResponse.<T>builder()
				.errorCode(ErrorCode.SUCCESS)
				.data(data)
				.build();
	}
	
	public static BaseControllerResponse<?> error(ErrorCode errorCode) {
		return BaseControllerResponse.builder()
				.errorCode(errorCode)
				.build();
	}
	
	public static BaseControllerResponse<?> successUpdate() {
		BaseControllerResponse
				.error(ErrorCode.SUCCESS);
		return BaseControllerResponse
				.builder()
				.build();
	}

	
}

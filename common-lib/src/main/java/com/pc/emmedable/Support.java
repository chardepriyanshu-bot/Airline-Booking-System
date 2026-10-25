package com.pc.emmedable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Support {
	private String email;
	private String phoneNumber;
	private String hour;
}

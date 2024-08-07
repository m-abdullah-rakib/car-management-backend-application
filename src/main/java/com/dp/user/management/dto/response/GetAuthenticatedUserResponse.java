package com.dp.user.management.dto.response;

import com.dp.user.management.dto.GetAuthenticatedUserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthenticatedUserResponse {
    private GetAuthenticatedUserData data;
}

package murraco.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import murraco.model.AppUserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDataDTO {

  @ApiModelProperty(position = 0)
  @NotBlank
  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  private String username;

  @ApiModelProperty(position = 1)
  @NotBlank
  @Email
  private String email;

  @ApiModelProperty(position = 2)
  @NotBlank
  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @ApiModelProperty(position = 3)
  private List<AppUserRole> appUserRoles;

}

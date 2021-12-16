package keboom.streamd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author keboom
 * @date 2021/12/2
 */
@NoArgsConstructor
@Data
public class Pojo {


  @JsonProperty("code")
  private Integer code;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private DataDTO data;
  @JsonProperty("serverTime")
  private Long serverTime;

  @NoArgsConstructor
  @Data
  public static class DataDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("isDelete")
    private Integer isDelete;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("templetName")
    private String templetName;
    @JsonProperty("templetId")
    private Integer templetId;
    @JsonProperty("resumeStyle")
    private Integer resumeStyle;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private String address;
  }
}

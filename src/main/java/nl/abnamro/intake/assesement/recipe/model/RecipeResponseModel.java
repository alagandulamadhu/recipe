package nl.abnamro.intake.assesement.recipe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("RecipeResponseModel information")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RecipeResponseModel {

    @ApiModelProperty(value = "Recipe REST API response status")
    private Integer status;

    @ApiModelProperty(value = "Recipe REST API response data")
    private List<Recipe> data;

    @ApiModelProperty(value = "Recipe REST API response time")
    private Date timestamp;

    public RecipeResponseModel(Integer status, List<Recipe> data, Date timestamp) {
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public RecipeResponseModel() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Recipe> getData() {
        return data;
    }

    public void setData(List<Recipe> data) {
        this.data = data;
    }
}

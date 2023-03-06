package nl.abnamro.intake.assesement.recipe.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RecipeResponseModel {
    private Integer status;
    private List<Recipe> data;
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

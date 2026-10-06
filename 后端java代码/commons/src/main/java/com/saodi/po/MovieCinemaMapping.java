package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@TableName("movie_cinema_mapping")
public class MovieCinemaMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 电影id
     */
    private Integer movieId;

    /**
     * 影院id
     */
    private Integer cinemaId;

    public MovieCinemaMapping(Integer movieId, Integer cinemaId) {
        this.movieId = movieId;
        this.cinemaId = cinemaId;
    }

    public MovieCinemaMapping() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    @Override
    public String toString() {
        return "MovieCinemaMapping{" +
            "id=" + id +
            ", movieId=" + movieId +
            ", cinemaId=" + cinemaId +
        "}";
    }
}

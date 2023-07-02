package com.spongehah.hahhome.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @JsonSerialize (using = ToStringSerializer.class)
    @TableId
    private Long id;

    /**
     * 名称
     */
    private String name;


    private  Integer count;
}

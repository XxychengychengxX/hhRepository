/**
 * @author Valar Morghulis
 * @Date 2023/8/14
 */
package com.project.hhrepository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleAuthDto {

    private List<Integer> authIds;

    private Integer roleId;
}

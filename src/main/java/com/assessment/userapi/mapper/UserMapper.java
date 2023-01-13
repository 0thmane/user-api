package com.assessment.userapi.mapper;

import com.assessment.userapi.domain.User;
import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * This is a mapper, used to map from entity class to dto class
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    public UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
           // @Mapping(target = "createdDate", ignore = true),
           // @Mapping(target = "lastModifiedDate", ignore = true),
    })
    public User userCreationDTOToUser(UserCreationDTO userCreationDTO);

    public UserDTO userToUserDTO(User user);
}

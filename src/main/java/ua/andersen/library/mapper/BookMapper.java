package ua.andersen.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ua.andersen.library.dto.BookUpdateDto;
import ua.andersen.library.entity.Book;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    void updateBookFromDto(BookUpdateDto dto, @MappingTarget Book book);
}

package ru.chibisov.app.dto.mapper;

/**
 * @param <E> type of Entity to map
 */
public interface MapperDTO<E, D> {
    D mapToDto(E entity);
    E mapFromDto(D dto);
}

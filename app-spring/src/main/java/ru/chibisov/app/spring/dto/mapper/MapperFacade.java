package ru.chibisov.app.spring.dto.mapper;

import java.util.List;

/**
 * Provide methods to convert one Entity class to others
 */
public interface MapperFacade {

    /**
     * Convert source to destination object
     * @param source input parametr which should to convert
     * @param destinationClass Classname type destination paramentr
     * @param <S> type source
     * @param <D> type destination
     * @return destination class after convert with filled fields
     */
    <S, D> D map(S source, Class<D> destinationClass);

    /**
     * Convert List source to destination List
     * @param source input list which should to convert
     * @param destinationClass Classname type destination object in list
     * @param <S> type objetc source of list
     * @param <D> type object destination of list
     * @return destination list with filled fields of object
     */
    <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass);
}

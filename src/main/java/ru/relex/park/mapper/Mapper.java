package ru.relex.park.mapper;

public interface Mapper<E, D> {

    E toEntity(D from);

    D toDto(E from);
}

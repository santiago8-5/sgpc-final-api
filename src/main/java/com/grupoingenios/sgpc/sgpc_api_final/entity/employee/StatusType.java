package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

/**
 * El enum `StatusType` define los posibles estados de un puesto de trabajo.
 * Los estados posibles son:
 * - `ACTIVO`: El puesto de trabajo está activo y ocupado.
 * - `INACTIVO`: El puesto de trabajo está inactivo, generalmente cuando no está en uso o no está asignado.
 * - `VACANTE`: El puesto de trabajo está disponible para ser ocupado.
 */
public enum StatusType {
    ACTIVO,
    INACTIVO,
    VACANTE
}

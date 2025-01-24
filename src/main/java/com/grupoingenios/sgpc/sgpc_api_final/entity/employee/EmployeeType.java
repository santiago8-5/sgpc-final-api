package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

/**
 * Enum que representa los diferentes tipos de empleados en la organización.
 * El tipo de empleado puede ser de dos categorías:
 * - **PLANTA:** Empleados permanentes que forman parte del equipo fijo.
 * - **OBRA:** Empleados contratados específicamente para proyectos o trabajos temporales.
 */
public enum EmployeeType {
    /**
     * Tipo de empleado que está contratado de manera permanente.
     */
    PLANTA,

    /**
     * Tipo de empleado que es contratado para proyectos o trabajos temporales.
     */
    OBRA
}

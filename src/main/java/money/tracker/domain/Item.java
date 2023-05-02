package money.tracker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** Домен записи (элемента) в отчёте */
@Data
@Entity
public class Item {
    /** Порядковый номер записи */
    @Id
    @GeneratedValue
    private Long id;
    /** Тип записи */
    private String type;
    /** Указанная в записи стоимость */
    private double cost;
    /** Дата записи */
    private String date;
    /** Назначение записи */
    private String dest;
}
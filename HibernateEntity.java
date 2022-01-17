package entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.util.*;

import static java.time.ZoneOffset.UTC;
import static javax.persistence.EnumType.STRING;

/**

very useful:

<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>hibernate-types-52</artifactId>
</dependency>

also use for json types

**/


@Slf4j
@FieldNameConstants
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@With
@Table(name = "my_table")
@ToString(exclude = {"createdAt", "updatedAt", "version"})
@EqualsAndHashCode(exclude = "my_other_table")
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    ),
    @TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
    )
})
public class HibernateEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, columnDefinition = "uuid", nullable = false)
    private UUID uuid;

    @Version
    @Column(name = "version")
    @NotNull
    private Long version;

    @Column(name = "timestamp")
    private LocalDateTime timestamp; // save as UTC local

    @Column(
        name = "updated_at",
        insertable = false,
        updatable = false)
    private LocalDateTime updatedAt;

    @Column(
        name = "created_at",
        insertable = false,
        updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "some_enum_state")
    @Enumerated(STRING)
    private SomeEnumState someEnumState;

    @ManyToOne
    private MyOtherTable myOtherTable;  // there is only one of this one, many of HibernateEntity

    @Type(type = "string-array")
    @Column(name = "somemorefield_list", columnDefinition = "varchar[]")
    private String[] somemorefieldList;

    @Transient
    private String mystring = new String(); // not to use in database, just an additional field

    public MyResponseResource toMyResponseResource() {

        // add mapper with builder code here
    }
}

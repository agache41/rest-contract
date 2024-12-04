
/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.rest.contract.entities;

import io.github.agache41.rest.contract.dataAccessBase.PrimaryKey;
import io.github.agache41.rest.contract.update.SelfTransferObject;
import io.github.agache41.rest.contract.update.Update;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
public class SubModell extends BaseEntity implements PrimaryKey<Long>, SelfTransferObject<SubModell> {

    private static final long serialVersionUID = 4145235006835414021L;

    @Id
    @EqualsAndHashCode.Exclude
    @SequenceGenerator(name = "idSequence", sequenceName = "idSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    @Update.excluded
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

//    @JsonIgnore
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToOne(fetch = FetchType.EAGER, mappedBy = "subModell")
//    private Modell modell;
}
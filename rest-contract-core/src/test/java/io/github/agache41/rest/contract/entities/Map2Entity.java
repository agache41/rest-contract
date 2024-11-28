
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

import io.github.agache41.rest.contract.dataAccess.PrimaryKey;
import io.github.agache41.rest.contract.update.SelfTransferObject;
import io.github.agache41.rest.contract.update.Update;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Map2Entity implements PrimaryKey<Long>, SelfTransferObject<Map2Entity> {
    private static final long serialVersionUID = 1273984705640286858L;
    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Update
    private String subName;

    @Update(dynamic = false)
    private String subStreet;

    @Update(dynamic = false)
    private Integer subNumber;

    @EqualsAndHashCode.Exclude
    private long subAge;
}
